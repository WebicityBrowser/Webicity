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

public class FlowInlineUnitGenerator implements UnitGenerator {

	private final Box[] children;
	private final RenderContext renderContext;
	
	private List<RenderedInlineUnit> renderedChildren;
	private UnitGenerator currentSubGenerator;
	private int cursor = 0;
	int linesToForce = 0;
	
	private CursorTracker cursorTracker = new HorizontalCursorTracker();

	public FlowInlineUnitGenerator(Box[] children, RenderContext renderContext) {
		this.children = children;
		this.renderContext = renderContext;
		updateCurrentSubGenerator();
		startNextUnit();
	}

	@Override
	public PartialUnitPreview previewNextUnit(ContextSwitch[] contextSwitches) {
		if (completed()) {
			throw new IllegalStateException("Unit generator already completed!");
		}
		
		PartialUnitPreview unitPreview = currentSubGenerator.previewNextUnit(contextSwitches);
		return new PartialUnitPreview() {
			@Override
			public AbsoluteSize sizeAfterAppend() {
				return cursorTracker.sizeCoveredAfterAdd(unitPreview.sizeAfterAppend());
			}
			
			@Override
			public boolean shouldForceNewLine() {
				return linesToForce > 0 || unitPreview.shouldForceNewLine();
			}
			
			@Override
			public void append() {
				if (linesToForce > 0) {
					linesToForce--;
				}
				unitPreview.append();
				updateCursorWhileCompleted();
			}
		};
	}

	@Override
	public Unit getMergedUnits() {
		if (currentSubGenerator != null) {
			mergeSubGeneratorUnit();
		}
		
		Unit fullUnit = new FlowInlineUnit(
			renderedChildren.toArray(RenderedInlineUnit[]::new),
			cursorTracker.getSizeCovered());
		startNextUnit();
		
		return fullUnit;
	}

	@Override
	public boolean completed() {
		return cursor >= children.length && currentSubGenerator == null;
	}
	
	private void updateCursorWhileCompleted() {
		if (currentSubGenerator == null) {
			return;
		}
		while (!lastUnitCompleted()) {
			if (!currentSubGenerator.completed()) {
				return;
			}
			mergeSubGeneratorUnit();
			updateCurrentSubGenerator();
		}
		mergeSubGeneratorUnit();
		currentSubGenerator = null;
	}
	
	private boolean lastUnitCompleted() {
		if (cursor < children.length) {
			return false;
		}
		return currentSubGenerator.completed();
	}

	private void mergeSubGeneratorUnit() {
		Unit mergedUnit = currentSubGenerator.getMergedUnits();
		AbsolutePosition unitPosition = cursorTracker.getNextPosition(); 
		renderedChildren.add(createRenderData(mergedUnit, unitPosition));
		cursorTracker.add(mergedUnit.getMinimumSize());
	}
	
	private RenderedInlineUnit createRenderData(Unit unit, AbsolutePosition unitPosition) {
		return new RenderedInlineUnit(unit, unitPosition, unit.getMinimumSize());
	}

	private void startNextUnit() {
		this.renderedChildren = new ArrayList<>();
		this.cursorTracker = new HorizontalCursorTracker();
	}

	private void updateCurrentSubGenerator() {
		if (completed()) {
			return;
		}
		
		Renderer childRenderer = children[cursor].createRenderer();
		cursor++;
		if (childRenderer instanceof FluidRenderer fluidRenderer) {
			currentSubGenerator = fluidRenderer.renderFluid(renderContext);
		} else {
			// TODO: Make sure this gets it's own line
			AbsoluteSize childSize = new AbsoluteSize(-1, -1);
			Unit unit = childRenderer.render(renderContext, childSize);
			currentSubGenerator = new SingleUnitGenerator(unit);
			linesToForce = 2;
		}
	}
	
}
