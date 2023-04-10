package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.stage.render.solid;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.layout.base.flowing.FlowingLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.SolidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;

public final class ContainerSolidRenderer {
	
	private ContainerSolidRenderer() {}

	public static Unit render(RenderContext renderContext, AbsoluteSize precomputedInnerSize, Box[] children) {
		SolidBox[] solidChildren = toSolidBoxArray(children);
		return FlowingLayoutManager.create()
			.render(renderContext, precomputedInnerSize, solidChildren);
	}

	private static SolidBox[] toSolidBoxArray(Box[] array) {
		SolidBox[] children = new SolidBox[array.length];
		System.arraycopy(array, 0, children, 0, array.length);
		
		return children;
	}
	
}
