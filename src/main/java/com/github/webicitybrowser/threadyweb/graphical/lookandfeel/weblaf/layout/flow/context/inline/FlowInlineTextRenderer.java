package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline.LineBox.LineEntry;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline.LineBox.LineMarkerEntry;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline.contexts.LineContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline.marker.UnitEnterMarker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.ConsolidatedCollapsibleTextView;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.ConsolidatedTextCollapser;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.TextConsolidation;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextBox;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextUnit;
import com.github.webicitybrowser.threadyweb.graphical.value.WhiteSpaceCollapse;

public final class FlowInlineTextRenderer {
	
	private FlowInlineTextRenderer() {}

	public static void preadjustTextBoxes(FlowInlineRendererState state, ChildrenBox box) {
		collectPreadjustTextBoxes(state, box);
		ConsolidatedCollapsibleTextView textView = state.getTextConsolidation().getTextView();
		ConsolidatedTextCollapser.collapse(textView, WhiteSpaceCollapse.COLLAPSE);
	}

	public static void addTextBoxToLine(FlowInlineRendererState state, TextBox textBox) {
		TextConsolidation textConsolidation = state.getTextConsolidation();
		String adjustedText = textConsolidation.readNextText(textBox);
		Font2D font = textBox.getFont(state.getGlobalRenderContext(), createLocalRenderContext(state));	
		TextSplitter splitter = new TextSplitter(adjustedText, font);
		while (!splitter.completed()) {
			String text = getNextSplit(state, splitter);
			addTextToCurrentLine(state, text, textBox, font);
		}
	}

	private static String getNextSplit(FlowInlineRendererState state, TextSplitter splitter) {
		LineContext lineContext = state.lineContext();
		boolean forceFit = lineContext.currentLine().isEmpty();
		float remainingWidth = calculateRemainingLineWidth(state);
		String text = splitter.getFittingText(forceFit, remainingWidth);
		if (text == null) {
			lineContext.startNewLine();
			return "";
		}

		return trimTextIfLineStart(state, text);
	}

	private static void addTextToCurrentLine(
		FlowInlineRendererState state, String text, TextBox textBox, Font2D font
	) {
		if (text.isEmpty()) return;

		AbsoluteSize fitSize = new AbsoluteSize(
			font.getMetrics().getStringWidth(text),
			font.getMetrics().getCapHeight()
		);
		
		state
			.lineContext()
			.currentLine()
			.add(new TextUnit(fitSize, textBox, text, font));
	}

	private static String trimTextIfLineStart(FlowInlineRendererState state, String text) {
		if (!text.startsWith(" ") || !isLineStart(state)) {
			return text;
		}

		return text.substring(1);
	}

	private static boolean isLineStart(FlowInlineRendererState state) {
		LineContext lineContext = state.lineContext();
		if (lineContext.currentLine().isEmpty()) {
			return true;
		}

		// Backwards iterate line. It is empty if every prior entry is a unit enter marker.
		List<LineEntry> lineItems = state.lineContext().currentLine().getLineItems();
		for (int i = lineItems.size() - 1; i >= 0; i--) {
			LineEntry lineItem = lineItems.get(i);
			if (!(lineItem instanceof LineMarkerEntry)) {
				return false;
			}
			if (!(((LineMarkerEntry) lineItem).marker() instanceof UnitEnterMarker)) {
				return false;
			}
		}

		return true;
	}

	private static void collectPreadjustTextBoxes(FlowInlineRendererState state, ChildrenBox box) {
		for (Box childBox: box.getChildrenTracker().getChildren()) {
			if (childBox instanceof TextBox textBox) {
				state.getTextConsolidation().addText(textBox, textBox.text());
			} else if (childBox instanceof ChildrenBox && !childBox.managesSelf()) {
				collectPreadjustTextBoxes(state, (ChildrenBox) childBox);
			}
		}
	}

	private static float calculateRemainingLineWidth(FlowInlineRendererState state) {
		float parentWidth = state.getLocalRenderContext().getPreferredSize().width();
		float remainingWidth = parentWidth == RelativeDimension.UNBOUNDED ?
			parentWidth :
			parentWidth - state.lineContext().currentLine().getSize().width();

		return remainingWidth;
	}

	private static LocalRenderContext createLocalRenderContext(FlowInlineRendererState state) {
		AbsoluteSize preferredSize = state.getLocalRenderContext().getPreferredSize();
		Font2D font = state.getFontStack().peek();
		return LocalRenderContext.create(preferredSize, font.getMetrics(), new ContextSwitch[0]);
	}

}