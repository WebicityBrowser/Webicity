package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.inline;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.SingleUnitGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.FluidRenderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.Renderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.PartialUnitPreview;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.UnitGenerator;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.cursor.CursorTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.cursor.HorizontalCursorTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.inline.FlowRecursiveContextSwitch.BoxEnterContext;

public class FlowInlineUnitGenerator implements UnitGenerator {

	private final Box[] children;
	private final RenderContext renderContext;
	private final ContextSwitch[] contextSwitches;
	private final FlowRecursiveContextSwitch recursiveSwitch;
	
	private List<RenderedInlineUnit> renderedChildren;
	private UnitGenerator currentSubGenerator;
	private int nextCursor = 0;
	
	private CursorTracker cursorTracker = new HorizontalCursorTracker();

	public FlowInlineUnitGenerator(Box[] children, RenderContext renderContext, ContextSwitch[] contextSwitches) {
		this.children = children;
		this.renderContext = renderContext;
		this.contextSwitches = contextSwitches;
		this.recursiveSwitch = findRecursiveSwitch();
		startNextUnit();
		findNextSubGenerator();
	}

	@Override
	public PartialUnitPreview previewNextUnit() {
		if (completed()) {
			throw new IllegalStateException("Unit generator already completed!");
		}
		
		PartialUnitPreview unitPreview = currentSubGenerator.previewNextUnit();
		
		return new PartialUnitPreview() {
			@Override
			public AbsoluteSize sizeAfterAppend() {
				return cursorTracker.sizeCoveredAfterAdd(unitPreview.sizeAfterAppend());
			}
			
			@Override
			public void append() {
				unitPreview.append();
				findNextSubGenerator();
			}
		};
	}

	@Override
	public Unit getMergedUnits() {
		if (currentSubGenerator != null) {
			mergeCurrentSubGeneratorProgress();
		}
		
		Unit fullUnit = new FlowInlineUnit(
			renderedChildren.toArray(RenderedInlineUnit[]::new),
			cursorTracker.getSizeCovered());
		startNextUnit();
		
		return fullUnit;
	}

	@Override
	public boolean completed() {
		return nextCursor >= children.length && currentSubGenerator == null;
	}
	
	private void startNextUnit() {
		this.renderedChildren = new ArrayList<>();
		this.cursorTracker = new HorizontalCursorTracker();
	}
	
	private void findNextSubGenerator() {
		exitCurrentSubGeneratorIfCompleted();
		while (nextCursor < children.length && currentSubGenerator == null) {
			startSubGeneratorAtCursor();
			exitCurrentSubGeneratorIfCompleted();
			nextCursor++;
		}
	}

	private void startSubGeneratorAtCursor() {
		// TODO: Call to the context
		Renderer childRenderer = children[nextCursor].createRenderer();
		if (childRenderer instanceof FluidRenderer fluidRenderer) {
			currentSubGenerator = fluidRenderer.renderFluid(renderContext, contextSwitches);
		} else {
			startOrSkipNonFluidSubGenerator(children[nextCursor], childRenderer);
		}
	}
	
	private void startOrSkipNonFluidSubGenerator(Box box, Renderer childRenderer) {
		startNonFluidSubGenerator(childRenderer);
		if (recursiveSwitch == null) {
			return;
		}
		recursiveSwitch.onBoxEnter(new BoxEnterContext() {
			@Override
			public void skipBox() {
				currentSubGenerator = null;
			}
			
			@Override
			public Box getBox() {
				return box;
			}
		});
		
	}
	
	private void startNonFluidSubGenerator(Renderer childRenderer) {
		AbsoluteSize childSize = new AbsoluteSize(-1, -1);
		Unit unit = childRenderer.render(renderContext, childSize);
		currentSubGenerator = new SingleUnitGenerator(unit);
	}

	private void exitCurrentSubGeneratorIfCompleted() {
		if (currentSubGenerator != null && currentSubGenerator.completed()) {
			exitCurrentSubGenerator();
		}
	}

	private void exitCurrentSubGenerator() {
		mergeCurrentSubGeneratorProgress();
		currentSubGenerator = null;
	}


	private void mergeCurrentSubGeneratorProgress() {
		Unit mergedUnit = currentSubGenerator.getMergedUnits();
		AbsolutePosition unitPosition = cursorTracker.getNextPosition(); 
		renderedChildren.add(createRenderData(mergedUnit, unitPosition));
		cursorTracker.add(mergedUnit.getMinimumSize());
	}
	
	private FlowRecursiveContextSwitch findRecursiveSwitch() {
		for (ContextSwitch contextSwitch: this.contextSwitches) {
			if (contextSwitch instanceof FlowRecursiveContextSwitch recursiveSwitch) {
				return recursiveSwitch;
			}
		}
		
		return null;
	}
	
	private RenderedInlineUnit createRenderData(Unit unit, AbsolutePosition unitPosition) {
		return new RenderedInlineUnit(unit, unitPosition, unit.getMinimumSize());
	}
	
}
