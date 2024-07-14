package com.github.webicitybrowser.threadyweb.graphical.layout.flow.util;

import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flow.LineHeightDirective;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowRootContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.imp.FloatContextImp;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.imp.FloatTrackerImp;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class FlowUtils {
	
	private FlowUtils() {}

	public static float getLineHeight(LayoutRenderContext context) {
		SizeCalculationContext sizeContext = LayoutSizeUtils.createSizeCalculationContext(context, context.componentUI());
		SizeCalculation lineHeightSizeCalculation = context.componentUI()
			.getStyleReference(LineHeightDirective.class).get()
			.getLineHeightCalculation();
		return lineHeightSizeCalculation.calculate(sizeContext, true);
	}

	public static FlowRenderContext createFlowRenderContext(LayoutRenderContext layoutManagerContext) {
		LocalRenderContext localRenderContext = layoutManagerContext.localRenderContext();
		FlowRootContextSwitch flowRootContextSwitch = getFlowRootContextSwitch(localRenderContext);

		return new FlowRenderContext(layoutManagerContext, flowRootContextSwitch);
	}

	private static FlowRootContextSwitch getFlowRootContextSwitch(LocalRenderContext localRenderContext) {
		for (ContextSwitch contextSwitch: localRenderContext.contextSwitches()) {
			if (contextSwitch instanceof FlowRootContextSwitch flowRootContextSwitch) {
				return flowRootContextSwitch;
			}
		}

		FloatContextImp floatContext = new FloatContextImp(new FloatTrackerImp());
		return new FlowRootContextSwitch(floatContext);
	}

}
