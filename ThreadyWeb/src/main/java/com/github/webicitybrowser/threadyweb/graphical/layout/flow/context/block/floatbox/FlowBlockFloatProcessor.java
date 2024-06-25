package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block.floatbox;

import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowRootContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block.FlowBlockRendererState;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.FloatContext;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.FloatContext.FloatEntry;

public final class FlowBlockFloatProcessor {
	
	private FlowBlockFloatProcessor() {}

	public static int renderInitialFloats(FlowBlockRendererState state, List<Box> children) {
		int floats = 0;
		for (int i = 0; i < children.size(); i++) {
			Box childBox = children.get(i);
			if (!FlowBlockFloatRenderer.isFloatBox(childBox)) break;
			float blockPosition = state.positionTracker().getPosition().y();
			RenderedUnit childUnit = FlowBlockFloatRenderer.renderFloatBoxUnit(state, childBox);
			FlowBlockFloatRenderer.addFloatBoxToLine(state, childUnit, childBox.styleDirectives(), blockPosition);
			floats++;
		}

		return floats;
	}

	public static void collectPostFloats(FlowBlockRendererState state, List<Box> children, int index) {
		FlowRenderContext flowContext = state.flowContext();
		FlowRootContextSwitch flowRootContextSwitch = flowContext.flowRootContextSwitch();
		FloatContext floatContext = flowRootContextSwitch.floatContext();
		for (int i = index; i < children.size(); i++) {
			Box childBox = children.get(i);
			if (!FlowBlockFloatRenderer.isFloatBox(childBox)) break;
			RenderedUnit childUnit = FlowBlockFloatRenderer.renderFloatBoxUnit(state, childBox);
			Box currentBox = flowContext.layoutManagerContext().parentBox();
			floatContext.addEndFloat(childBox, childUnit, currentBox);
		}
	}

	public static void addRemainingFloats(FlowBlockRendererState state) {
		FlowRenderContext flowContext = state.flowContext();
		FlowRootContextSwitch flowRootContextSwitch = flowContext.flowRootContextSwitch();
		FloatContext floatContext = flowRootContextSwitch.floatContext();

		while (!floatContext.getEndFloats().isEmpty()) {
			FloatEntry floatEntry = floatContext.getEndFloats().peek();
			Box currentBox = flowContext.layoutManagerContext().parentBox();
			if (floatEntry.orginatingBox() != currentBox) break;
			floatContext.getEndFloats().poll();

			float blockPosition = state.positionTracker().getPosition().y();
			FlowBlockFloatRenderer.addFloatBoxToLine(
				state, floatEntry.floatUnit(),
				floatEntry.floatBox().styleDirectives(), blockPosition);
		}
	}

}
