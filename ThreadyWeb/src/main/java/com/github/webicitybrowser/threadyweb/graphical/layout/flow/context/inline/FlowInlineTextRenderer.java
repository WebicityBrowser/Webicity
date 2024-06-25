package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.LineBreakDirective.LineBreak;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline.LineBox.LineEntry;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline.LineBox.LineMarkerEntry;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline.contexts.LineContext;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline.marker.UnitEnterMarker;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.ConsolidatedCollapsibleTextView;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.ConsolidatedTextCollapser;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.TextConsolidation;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextBox;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.directive.WebTextDirectiveUtil;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;
import com.github.webicitybrowser.threadyweb.graphical.value.WhiteSpaceCollapse;

public final class FlowInlineTextRenderer {
	
	private FlowInlineTextRenderer() {}

	public static void preadjustTextBoxes(FlowInlineRendererState state, List<Box> children) {
		collectPreadjustTextBoxes(state, children);
		ConsolidatedCollapsibleTextView textView = state.getTextConsolidation().getTextView();
		ConsolidatedTextCollapser.collapse(textView, WhiteSpaceCollapse.COLLAPSE);
	}

	public static void addTextBoxToLine(FlowInlineRendererState state, TextBox textBox) {
		TextConsolidation textConsolidation = state.getTextConsolidation();
		String adjustedText = textConsolidation.readNextText(textBox);
		Font2D font = textBox.getFont(state.getGlobalRenderContext(), createLocalRenderContext(state));	
		SizeCalculationContext context = LayoutSizeUtils.createSizeCalculationContext(
			state.flowContext().layoutManagerContext(), textBox.styleDirectives(), true);
		float letterSpacing = WebTextDirectiveUtil.getLetterSpacing(textBox.styleDirectives(), context);
		LineBreak lineBreak = WebTextDirectiveUtil.getLineBreak(textBox.styleDirectives());
		TextSplitter splitter = new TextSplitter(adjustedText, font, lineBreak, letterSpacing);
		while (!splitter.completed()) {
			// TODO: If no text fits between floats, then jump to after the floats.
			String text = getNextSplit(state, splitter);
			addTextToCurrentLine(state, text, textBox, font, letterSpacing);
		}
	}

	private static String getNextSplit(FlowInlineRendererState state, TextSplitter splitter) {
		// TODO: Don't force fit if floats are present.
		LineContext lineContext = state.lineContext();
		boolean forceFit = lineContext.currentLine().isEmpty();
		LineBox currentLine = lineContext.currentLine();
		float maxRun = currentLine.getMaxLineSize().run();
		float remainingRun = maxRun == RelativeDimension.UNBOUNDED ? maxRun : maxRun - currentLine.getSize().width();
		String text = splitter.getFittingText(forceFit, remainingRun);
		if (text == null) {
			FlowInlineRendererUtil.startNewLine(state);
			return "";
		}

		return trimTextIfLineStart(state, text);
	}

	private static void addTextToCurrentLine(
		FlowInlineRendererState state, String text, TextBox textBox, Font2D font, float letterSpacing
	) {
		if (text.isEmpty()) return;

		float textWidth = font.getMetrics().getStringWidth(text) + letterSpacing * (text.length() - 1);
		AbsoluteSize fitSize = new AbsoluteSize(
			textWidth,
			font.getMetrics().getCapHeight() + font.getMetrics().getDescent()
		);

		LineBox currentLine = state.lineContext().currentLine();
		
		currentLine.add(new TextUnit(fitSize, textBox, text, font, letterSpacing), fitSize);
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

	private static void collectPreadjustTextBoxes(FlowInlineRendererState state, List<Box> children) {
		for (Box childBox: children) {
			if (childBox instanceof TextBox textBox) {
				state.getTextConsolidation().addText(textBox, textBox.text());
			} else if (childBox instanceof ChildrenBox && !childBox.managesSelf()) {
				List<Box> childBoxes = ((ChildrenBox) childBox).getChildrenTracker().getChildren();
				collectPreadjustTextBoxes(state, childBoxes);
			}
		}
	}

	private static LocalRenderContext createLocalRenderContext(FlowInlineRendererState state) {
		AbsoluteSize preferredSize = state.getLocalRenderContext().preferredSize();
		return LocalRenderContext.create(preferredSize, new ContextSwitch[0]);
	}

}
