package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.SolidBoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;

public class ContainerBox implements ChildrenBox {

	private final Context containerContext;
	
	private final BoxChildrenTracker childTracker;
	
	public ContainerBox(Context containerContext, UIDisplay<?, ChildrenBox, ?> anonDisplay) {
		this.containerContext = containerContext;
		this.childTracker = new SolidBoxChildrenTracker(this, anonDisplay);
	}

	@Override
	public Context displayContext() {
		return containerContext;
	}

	@Override
	public BoxChildrenTracker getChildrenTracker() {
		return childTracker;
	}

}
