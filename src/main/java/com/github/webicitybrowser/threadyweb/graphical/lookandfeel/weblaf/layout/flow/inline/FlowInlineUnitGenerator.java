package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.inline;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundRenderedUnit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundRenderedUnitGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.CursorTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.HorizontalLineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineCursorTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.inline.FlowRecursiveContextSwitch.BoxEnterContext;

public class FlowInlineUnitGenerator implements RenderedUnitGenerator<InnerDisplayUnit> {

	private final GlobalRenderContext globalRenderContext;
	private final LocalRenderContext localRenderContext;
	private final List<BoundBox<?, ?>> children;
	private final FlowRecursiveContextSwitch recursiveSwitch;
	private final LineDimensionConverter dimensionConverter;
	
	private List<ChildLayoutResult> renderedChildren;
	private BoundRenderedUnitGenerator<?> currentSubGenerator;
	private int nextCursor = 0;
	
	private CursorTracker cursorTracker;
	private InnerDisplayUnit lastGeneratedUnit;

	public FlowInlineUnitGenerator(ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		this.globalRenderContext = globalRenderContext;
		this.localRenderContext = localRenderContext;
		this.children = box.getChildrenTracker().getChildren();
		this.recursiveSwitch = findRecursiveSwitch();
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
			result = currentSubGenerator.getRaw().generateNextUnit(remainingAvailableUnitSize, forceFit);
			if (result != GenerationResult.NORMAL) {
				break;
			}
			BoundRenderedUnit<?> nextUnit = currentSubGenerator.getLastGeneratedUnit();
			addUnit(nextUnit);
			findNextSubGenerator();
		}

		if (renderedChildren.isEmpty()) {
			return GenerationResult.NO_FIT;
		}

		lastGeneratedUnit = new InnerDisplayUnit(
			cursorTracker.getSizeCovered(),
			renderedChildren.toArray(ChildLayoutResult[]::new));

		return result;
	}

	@Override
	public InnerDisplayUnit getLastGeneratedUnit() {
		if (lastGeneratedUnit == null) {
			throw new IllegalStateException("No unit generated yet!");
		}

		return lastGeneratedUnit;
	}

	private void addUnit(BoundRenderedUnit<?> nextUnit) {
		AbsolutePosition unitPosition = cursorTracker.getNextPosition();
		renderedChildren.add(createRenderResult(nextUnit, unitPosition));
		cursorTracker.add(nextUnit.getRaw().preferredSize());
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
		BoundBox<?, ?> box = children.get(nextCursor);
		currentSubGenerator = box.render(globalRenderContext, localRenderContext);
		startOrSkipSubGenerator(box);
	}
	
	private void startOrSkipSubGenerator(BoundBox<?, ?> box) {
		if (recursiveSwitch == null) {
			return;
		}
		recursiveSwitch.onBoxEnter(new BoxEnterContext() {
			@Override
			public void skipBox() {
				currentSubGenerator = null;
			}
			
			@Override
			public BoundBox<?, ?> getBox() {
				return box;
			}
		});
		
	}

	private void exitCurrentSubGeneratorIfCompleted() {
		if (currentSubGenerator != null && currentSubGenerator.getRaw().completed()) {
			exitCurrentSubGenerator();
		}
	}

	private void exitCurrentSubGenerator() {
		currentSubGenerator = null;
	}
	
	private FlowRecursiveContextSwitch findRecursiveSwitch() {
		for (ContextSwitch contextSwitch: localRenderContext.getContextSwitches()) {
			if (contextSwitch instanceof FlowRecursiveContextSwitch recursiveSwitch) {
				return recursiveSwitch;
			}
		}
		
		return null;
	}
	
	private ChildLayoutResult createRenderResult(BoundRenderedUnit<?> unit, AbsolutePosition unitPosition) {
		Rectangle relativeRect = new Rectangle(unitPosition, unit.getRaw().preferredSize());
		return new ChildLayoutResult(relativeRect, unit);
	}
	
}
