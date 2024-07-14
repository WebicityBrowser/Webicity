package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element;

import com.github.webicitybrowser.thready.gui.graphical.layout.core.SolidLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.SolidBoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebDefaults;

public class ElementBlockBox implements ElementBox {
	
	private final Context displayContext;
	private final BoxChildrenTracker childrenTracker;
	private final SolidLayoutManager layout;

	public ElementBlockBox(Context displayContext, SolidLayoutManager layout) {
		this.displayContext = displayContext;
		this.childrenTracker = new SolidBoxChildrenTracker(this, WebDefaults.INLINE_DISPLAY);
		this.layout = layout;
	}

	@Override
	public Context displayContext() {
		return this.displayContext;
	}

	@Override
	public BoxChildrenTracker getChildrenTracker() {
		return this.childrenTracker;
	}
	
	@Override
	public SolidLayoutManager layout() {
		return this.layout;
	}

}
