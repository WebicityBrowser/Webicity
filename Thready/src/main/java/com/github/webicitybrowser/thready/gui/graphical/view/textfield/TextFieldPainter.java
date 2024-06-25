package com.github.webicitybrowser.thready.gui.graphical.view.textfield;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.thready.gui.graphical.animation.InvalidationScheduler;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.viewmodel.textfield.TextFieldViewModel;

public class TextFieldPainter {
	
	private TextFieldPainter() {}
	
	private static final int CURSOR_BLINK_CYCLE_MILLIS = 1500;

	public static void paint(TextFieldContext textFieldContext, GlobalPaintContext globalContext, LocalPaintContext localContext) {
		String text = textFieldContext.getViewModel().getText();
		
		drawText(localContext, text);
		updateCursor(globalContext, localContext, text, textFieldContext);
	}

	private static void updateCursor(GlobalPaintContext globalContext, LocalPaintContext localContext, String text, TextFieldContext textFieldContext) {
		TextFieldCursorState currentTextFieldCursorState = getCurrentTextFieldCursorState();
		TextFieldViewModel textFieldViewModel = textFieldContext.getViewModel();
		if (textFieldViewModel.isFocused()) {
			if (currentTextFieldCursorState == TextFieldCursorState.SHOWN) {
				drawCursor(localContext, text, textFieldViewModel);
			}
			scheduleNextRedraw(globalContext, textFieldContext);
		} else {
			
		}
	}

	private static void scheduleNextRedraw(GlobalPaintContext globalContext, TextFieldContext textFieldContext) {
		TextFieldCursorState currentTextFieldCursorState = getCurrentTextFieldCursorState();
		if (textFieldContext.getLastCursorState() != currentTextFieldCursorState) {
			scheduleNextInvalidation(globalContext.invalidationScheduler(), textFieldContext.getOwningComponentUI());
			textFieldContext.setLastCursorState(currentTextFieldCursorState);
		}
	}

	private static void drawText(LocalPaintContext localContext, String text) {
		Canvas2D canvas = localContext.canvas();
		Rectangle documentRect = localContext.documentRect();
		FontMetrics metrics = canvas.getPaint().getFont().getMetrics();
		
		float docX = documentRect.position().x();
		float docY = documentRect.position().y();
		float docH = documentRect.size().height();

		float yOffset = docH / 2 - metrics.getCapHeight() / 2;
		
		canvas.drawText(docX, docY + yOffset, text);
	}
	
	private static void drawCursor(LocalPaintContext localContext, String text, TextFieldViewModel textFieldViewModel) {
		Rectangle documentRect = localContext.documentRect();
		Canvas2D canvas = localContext.canvas();
		
		
		float docX = documentRect.position().x();
		float docY = documentRect.position().y();
		float docH = documentRect.size().height();
		
		int cursorPos = textFieldViewModel.getCursorPos();
		int cursorOffset = getCursorOffset(canvas, text, cursorPos);
		
		canvas.drawLine(docX + cursorOffset, docY + docH / 4, 0, docH / 2);
	}
	
	private static TextFieldCursorState getCurrentTextFieldCursorState() {
		boolean cursorShown = System.currentTimeMillis() % CURSOR_BLINK_CYCLE_MILLIS * 2 < CURSOR_BLINK_CYCLE_MILLIS;
		return cursorShown ? TextFieldCursorState.SHOWN : TextFieldCursorState.NOT_SHOWN;
	}
	
	private static void scheduleNextInvalidation(InvalidationScheduler scheduler, ComponentUI invalidatedComponentUI) {
		long nextInvalidationMillis = CURSOR_BLINK_CYCLE_MILLIS - System.currentTimeMillis() % CURSOR_BLINK_CYCLE_MILLIS;
		scheduler.scheduleInvalidationInMillis(nextInvalidationMillis, invalidatedComponentUI, InvalidationLevel.PAINT);
	}

	private static int getCursorOffset(Canvas2D canvas, String text, int cursorPos) {
		int cursorOffset = 0;
		Font2D font = canvas.getPaint().getFont();
		FontMetrics metrics = font.getMetrics();
		for (int i = 0; i < cursorPos; i++) {
			cursorOffset += metrics.getCharacterWidth(text.codePointAt(i));
		}
		
		return cursorOffset;
	}

}
