package everyos.desktop.thready.laf.simple.component.ui.container.fluid;

import java.util.ArrayList;
import java.util.List;

import everyos.desktop.thready.core.gui.stage.box.FluidBox;
import everyos.desktop.thready.core.gui.stage.render.FluidRenderer;
import everyos.desktop.thready.core.gui.stage.render.RenderContext;
import everyos.desktop.thready.core.gui.stage.render.unit.ContextSwitch;
import everyos.desktop.thready.core.gui.stage.render.unit.NextUnitInfo;
import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.gui.stage.render.unit.UnitGenerator;
import everyos.desktop.thready.core.positioning.AbsolutePosition;
import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.core.positioning.imp.AbsolutePositionImp;
import everyos.desktop.thready.core.positioning.imp.AbsoluteSizeImp;

public class HorizontalFluidChildrenRendererSession {

	private final RenderContext renderContext;
	private final AbsoluteSize fitSize;
	private final FluidBox[] children;
	private final ContextSwitch[] switches;
	
	private List<FluidChildrenResult> rendered = new ArrayList<>();
	private float posX = 0;
	private float posY = 0;
	private float totalWidth = 0;
	private float curLineHeight = 0;
	
	// TODO: Alignment within line

	public HorizontalFluidChildrenRendererSession(RenderContext renderContext, AbsoluteSize fitSize, FluidBox[] children) {
		this.renderContext = renderContext;
		this.fitSize = fitSize;
		this.children = children;
		this.switches = new ContextSwitch[] {};
	}
	
	public Unit render() {
		renderChildren();
		
		AbsoluteSize computedSize = computeFinalSize();
		return new HorizontalFluidChildrenUnit(computedSize, rendered.toArray(new FluidChildrenResult[0]));
	}

	private void renderChildren() {
		for (FluidBox child: children) {
			renderChild(child);
		}
	}

	private void renderChild(FluidBox child) {
		FluidRenderer childRenderer = child.createRenderer();
		UnitGenerator unitGenerator = childRenderer.render(renderContext);
		
		while (!unitGenerator.completed()) {
			NextUnitInfo nextUnitInfo = unitGenerator.getNextUnitInfo(switches);
			if (shouldStartNewLine(nextUnitInfo)) {
				appendUnit(unitGenerator.getMergedUnits());
				goToNextLine();
			}
			nextUnitInfo.append();
		}
		appendUnit(unitGenerator.getMergedUnits());
	}

	private boolean shouldStartNewLine(NextUnitInfo nextUnitInfo) {
		if (fitSize.getWidth() == -1) {
			return false;
		}
		
		float unitEndX = nextUnitInfo.sizeAfterAppend().getWidth() + posX;
		return unitEndX > fitSize.getWidth();
	}

	private void appendUnit(Unit unit) {
		AbsoluteSize size = unit.getMinimumSize();
		curLineHeight = Math.max(size.getHeight(), curLineHeight);
		
		AbsolutePosition startPosition = new AbsolutePositionImp(posX, posY);
		rendered.add(new FluidChildrenResult(startPosition, unit));
		
		posX += unit.getMinimumSize().getWidth();
	}
	
	private void goToNextLine() {
		posX = 0;
		posY += curLineHeight;
		curLineHeight = 0;
	}
	
	private AbsoluteSize computeFinalSize() {
		return new AbsoluteSizeImp(totalWidth, posY + curLineHeight);
	}
	
}
