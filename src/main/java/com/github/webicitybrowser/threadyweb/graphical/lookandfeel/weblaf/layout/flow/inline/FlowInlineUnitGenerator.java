package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.inline;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.CursorTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.HorizontalLineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineCursorTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineDimensionConverter;

public class FlowInlineUnitGenerator implements RenderedUnitGenerator<InnerDisplayUnit> {

	private final GlobalRenderContext globalRenderContext;
	private final LocalRenderContext localRenderContext;
	private final UIDisplay<?, ?, ?> display;
	private final List<Box> children;
	private final LineDimensionConverter dimensionConverter;
	
	private List<ChildLayoutResult> renderedChildren;
	private RenderedUnitGenerator<?> currentSubGenerator;
	private int nextCursor = 0;
	
	private CursorTracker cursorTracker;
	private InnerDisplayUnit lastGeneratedUnit;

	public FlowInlineUnitGenerator(ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		this.globalRenderContext = globalRenderContext;
		this.localRenderContext = localRenderContext;
		this.display = box.display();
		this.children = box.getChildrenTracker().getChildren();
		this.dimensionConverter = new HorizontalLineDimensionConverter();
		startNextUnit();
		findNextSubGenerator();
	}
	
	@Override
	public GenerationResult generateNextUnit(AbsoluteSize preferredBounds, boolean forceFit) {
		if (completed()) {
			throw new IllegalStateException("Unit generator already completed!");
		}

		startNextUnit();

		GenerationResult result = GenerationResult.NORMAL;
		
		while (!completed()) {
			AbsoluteSize remainingAvailableUnitSize = getRemainingAvailableUnitSize(preferredBounds);
			result = currentSubGenerator.generateNextUnit(remainingAvailableUnitSize, forceFit);
			if (result != GenerationResult.NORMAL) {
				break;
			}
			RenderedUnit nextUnit = currentSubGenerator.getLastGeneratedUnit();
			addUnit(nextUnit);
			findNextSubGenerator();
		}

		if (renderedChildren.isEmpty()) {
			return GenerationResult.NO_FIT;
		}

		lastGeneratedUnit = new InnerDisplayUnit(
			display,
			cursorTracker.getSizeCovered(),
			renderedChildren.toArray(ChildLayoutResult[]::new));

		return result;
	}

	@Override
	public InnerDisplayUnit getLastGeneratedUnit() {
		return lastGeneratedUnit;
	}

	private void addUnit(RenderedUnit nextUnit) {
		AbsolutePosition unitPosition = cursorTracker.getNextPosition();
		renderedChildren.add(createRenderResult(nextUnit, unitPosition));
		cursorTracker.add(nextUnit.preferredSize());
	}

	private AbsoluteSize getRemainingAvailableUnitSize(AbsoluteSize preferredBounds) {
		if (completed()) {
			return AbsoluteSize.ZERO_SIZE;
		}

		AbsoluteSize filledSpace = cursorTracker.getSizeCovered();

		float remainingWidth = preferredBounds.width() - filledSpace.width();
		float remainingHeight = preferredBounds.height() - filledSpace.height();
		remainingWidth = preferredBounds.width() == RelativeDimension.UNBOUNDED ? RelativeDimension.UNBOUNDED : Math.max(0, remainingWidth);
		remainingHeight = preferredBounds.height() == RelativeDimension.UNBOUNDED ? RelativeDimension.UNBOUNDED : Math.max(0, remainingHeight);

		return new AbsoluteSize(remainingWidth, remainingHeight);
	}

	@Override
	public boolean completed() {
		return nextCursor >= children.size() && currentSubGenerator == null;
	}
	
	private void startNextUnit() {
		this.renderedChildren = new ArrayList<>();
		this.cursorTracker = new LineCursorTracker(dimensionConverter);
		this.lastGeneratedUnit = null;
	}
	
	private void findNextSubGenerator() {
		exitCurrentSubGeneratorIfCompleted();
		while (nextCursor < children.size() && currentSubGenerator == null) {
			startSubGeneratorAtCursor();
			exitCurrentSubGeneratorIfCompleted();
			nextCursor++;
		}
	}

	private void startSubGeneratorAtCursor() {
		// TODO: Call to the context
		// TODO: Figure out what I meant by the above TODO statement
		Box box = children.get(nextCursor);
		currentSubGenerator = UIPipeline.render(box, globalRenderContext, localRenderContext);
	}

	private void exitCurrentSubGeneratorIfCompleted() {
		if (currentSubGenerator != null && currentSubGenerator.completed()) {
			exitCurrentSubGenerator();
		}
	}

	private void exitCurrentSubGenerator() {
		currentSubGenerator = null;
	}
	
	private ChildLayoutResult createRenderResult(RenderedUnit unit, AbsolutePosition unitPosition) {
		Rectangle relativeRect = new Rectangle(unitPosition, unit.preferredSize());
		return new ChildLayoutResult(relativeRect, unit);
	}
	
}
