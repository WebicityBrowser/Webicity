package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.threadyweb.graphical.directive.WhiteSpaceCollapseDirective.WhiteSpaceCollapse;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.ConsolidatedCollapsibleTextView;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.ConsolidatedTextCollapser;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.TextConsolidation;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextBox;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextUnit;

public final class FlowFluidTextRenderer {
	
	private FlowFluidTextRenderer() {}

	public static void preadjustTextBoxes(FlowFluidRendererState state, ChildrenBox box) {
		collectPreadjustTextBoxes(state, box);
		ConsolidatedCollapsibleTextView textView = state.getTextConsolidation().getTextView();
		ConsolidatedTextCollapser.collapse(textView, WhiteSpaceCollapse.COLLAPSE);
	}

	public static void addTextBoxToLine(FlowFluidRendererState state, TextBox textBox) {
		TextConsolidation textConsolidation = state.getTextConsolidation();
		String adjustedText = textConsolidation.readNextText(textBox);
		Font2D font = textBox.getFont(state.getGlobalRenderContext());
		
		TextSplitter splitter = new TextSplitter(adjustedText, font);
		while (!splitter.completed()) {
			boolean forceFit = state.currentLine().isEmpty();
			float parentWidth = state.getLocalRenderContext().getPreferredSize().width();
			float remainingWidth = parentWidth == RelativeDimension.UNBOUNDED ?
				parentWidth :
				parentWidth - state.currentLine().getSize().width();
			String text = splitter.getFittingText(forceFit, remainingWidth);
			if (text == null) {
				state.startNewLine();
				continue;
			}

			AbsoluteSize preferredSize = new AbsoluteSize(
				splitter.getLastTextWidth(),
				font.getMetrics().getCapHeight()
			);
			
			state.currentLine().add(new TextUnit(preferredSize, textBox, text, font));
		}
	}

	private static void collectPreadjustTextBoxes(FlowFluidRendererState state, ChildrenBox box) {
		for (Box childBox: box.getChildrenTracker().getChildren()) {
			if (childBox instanceof TextBox textBox) {
				state.getTextConsolidation().addText(textBox, textBox.text());
			} else if (childBox instanceof ChildrenBox && !childBox.managesSelf()) {
				collectPreadjustTextBoxes(state, (ChildrenBox) childBox);
			}
		}
	}

}
