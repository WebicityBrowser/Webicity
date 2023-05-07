package com.github.webicitybrowser.thready.gui.graphical.view.textfield;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.thready.gui.graphical.animation.InvalidationScheduler;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.PaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;
import com.github.webicitybrowser.thready.gui.graphical.viewmodel.textfield.TextFieldViewModel;

public class TextFieldPainter implements Painter {
	
	private static final int CURSOR_BLINK_CYCLE_MILLIS = 1500;

	private final Rectangle documentRect;
	private final ComponentUI componentUI;
	private final TextFieldViewModel textFieldViewModel;
	private final Font2D font;
	
	private CursorState lastCursorState = CursorState.NEW;

	public TextFieldPainter(Rectangle documentRect, ComponentUI componentUI, TextFieldViewModel textFieldViewModel, Font2D font) {
		this.documentRect = documentRect;
		this.componentUI = componentUI;
		this.textFieldViewModel = textFieldViewModel;
		this.font = font;
	}
	
	@Override
	public void paint(PaintContext context, Canvas2D canvas, Rectangle viewport) {
		String text = textFieldViewModel.getText();
		
		drawText(text, canvas);
		CursorState currentCursorState = getCurrentCursorState();
		if (textFieldViewModel.isFocused() && currentCursorState == CursorState.SHOWN) {
			drawCursor(text, canvas);
		}
		if (lastCursorState != currentCursorState) {
			scheduleNextInvalidation(context.getInvalidationScheduler());
			lastCursorState = currentCursorState;
		}
	}

	private void drawText(String text, Canvas2D canvas) {
		float docX = documentRect.position().x();
		float docY = documentRect.position().y();
		
		canvas.drawText(docX, docY, text);
	}
	
	private void drawCursor(String text, Canvas2D canvas) {
		float docX = documentRect.position().x();
		float docY = documentRect.position().y();
		float docH = documentRect.size().height();
		
		int cursorPos = textFieldViewModel.getCursorPos();
		int cursorOffset = getCursorOffset(text, cursorPos);
		
		canvas.drawLine(docX + cursorOffset, docY + docH / 4, 0, docH / 2);
	}
	
	private CursorState getCurrentCursorState() {
		boolean cursorShown = System.currentTimeMillis() % CURSOR_BLINK_CYCLE_MILLIS * 2 < CURSOR_BLINK_CYCLE_MILLIS;
		return cursorShown ? CursorState.SHOWN : CursorState.NOT_SHOWN;
	}
	
	private void scheduleNextInvalidation(InvalidationScheduler scheduler) {
		long nextInvalidationMillis = CURSOR_BLINK_CYCLE_MILLIS - System.currentTimeMillis() % CURSOR_BLINK_CYCLE_MILLIS;
		scheduler.scheduleInvalidationInMillis(nextInvalidationMillis, componentUI, InvalidationLevel.PAINT);
	}

	private int getCursorOffset(String text, int cursorPos) {
		int cursorOffset = 0;
		FontMetrics metrics = font.getMetrics();
		for (int i = 0; i < cursorPos; i++) {
			cursorOffset += metrics.getCharacterWidth(text.codePointAt(i));
		}
		
		return cursorOffset;
	}
	
	private enum CursorState {
		NEW, NOT_SHOWN, SHOWN
	}

}
