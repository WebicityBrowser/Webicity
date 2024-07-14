package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.util.AbsoluteDimensionsMath;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowConfig;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.BoxOffsetDimensions;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.BuildableRenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.StyledUnitContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextUnit;

public class LineRenderer {

	private final FlowConfig flowConfig;
	private final LayoutRenderContext layoutRenderContext;
	private final LineBox lineBox;

	private final List<LineEntry> remainingEntries = new LinkedList<>();
	private final Deque<LineSectionBuilder> sectionBuilders = new ArrayDeque<>();
	private final Deque<Box> boxStack = new ArrayDeque<>();
	
	public LineRenderer(FlowConfig flowConfig, LayoutRenderContext layoutRenderContext, LineBox lineBox) {
		this.flowConfig = flowConfig;
		this.layoutRenderContext = layoutRenderContext;
		this.lineBox = lineBox;
		remainingEntries.addAll(lineBox.entries());
		startSection();
		renderAll();
	}

	public LayoutResult getLayoutResult(AbsolutePosition positionOffset) {
		assert sectionBuilders.size() == 1;

		ChildLayoutResult[] childLayoutResults = sectionBuilders.peek().render();
		ChildLayoutResult[] adjustedResults = new ChildLayoutResult[childLayoutResults.length];
		
		for (int i = 0; i < childLayoutResults.length; i++) {
			ChildLayoutResult childLayoutResult = childLayoutResults[i];
			AbsolutePosition adjustedPosition = AbsoluteDimensionsMath.sum(positionOffset, childLayoutResult.relativeRect().position(), AbsolutePosition::new);
			adjustedResults[i] = new ChildLayoutResult(childLayoutResult.unit(),
				new Rectangle(adjustedPosition, childLayoutResult.relativeRect().size()));
		}

		return LayoutResult.create(adjustedResults, lineBox.positioningInfo().size());
	}

	public boolean isDone() {
		return remainingEntries.isEmpty();
	}

	private void renderAll() {
		while (!remainingEntries.isEmpty()) {
			renderNext();
		}
		assert sectionBuilders.size() == 1;
		assert boxStack.isEmpty();
	}

	private void renderNext() {
		LineEntry lineEntry = remainingEntries.remove(0);
		if (lineEntry instanceof LineEntry.Unit unitEntry) {
			RenderedUnit renderedUnit = unitEntry.unit();
			addUnit(renderedUnit);
		} else if (lineEntry instanceof LineEntry.Text textEntry) {
			TextUnit textUnit = textEntry.textUnit();
			if (textUnit.text().isEmpty()) return;
			addUnit(textUnit);
		} else if (lineEntry instanceof LineEntry.UnitEnter unitEnter) {
			enterSection(unitEnter);
		} else if (lineEntry instanceof LineEntry.UnitExit) {
			exitSection();
		}
	}

	private void addUnit(RenderedUnit unit) {
		sectionBuilders.peek().addUnit(unit);
	}

	private void enterSection(LineEntry.UnitEnter unitEnter) {
		startSection();
		boxStack.push(unitEnter.box());
	}
	
	private void exitSection() {
		LineSectionBuilder sectionBuilder = sectionBuilders.pop();
		ChildLayoutResult[] childLayoutResults = sectionBuilder.render();
		Box box = boxStack.pop();
		BuildableRenderedUnit buildableUnit = flowConfig.buildableUnitGenerator().apply(box);

		float maxWidth = 0;
		float maxHeight = 0;
		for (ChildLayoutResult childLayoutResult : childLayoutResults) {
			Rectangle relativeRect = childLayoutResult.relativeRect();
			buildableUnit.addChildUnit(childLayoutResult.unit(), relativeRect);
			maxWidth = Math.max(maxWidth, relativeRect.position().x() + relativeRect.size().width());
			maxHeight = Math.max(maxHeight, relativeRect.position().y() + relativeRect.size().height());
		}
		buildableUnit.setFitSize(new AbsoluteSize(maxWidth, maxHeight));
		
		BoxOffsetDimensions boxOffsetDimensions = BoxOffsetDimensions.create(layoutRenderContext, box.componentUI());
		AbsoluteSize outerSize = addPaddingAndBorder(buildableUnit.fitSize(), boxOffsetDimensions);
		RenderedUnit styledUnit = flowConfig.styledUnitGenerator().generateStyledUnit(
			new StyledUnitContext(buildableUnit, outerSize, boxOffsetDimensions));
		addUnit(styledUnit);
	}

	private void startSection() {
		sectionBuilders.push(new LineSectionBuilder(lineBox.direction()));
	}

	private AbsoluteSize addPaddingAndBorder(AbsoluteSize fitSize, BoxOffsetDimensions boxOffsetDimensions) {
		AbsoluteSize withPadding = LayoutSizeUtils.addPadding(fitSize, boxOffsetDimensions.padding());
		AbsoluteSize withBorder = LayoutSizeUtils.addPadding(withPadding, boxOffsetDimensions.borders());

		return withBorder;
	}

}
