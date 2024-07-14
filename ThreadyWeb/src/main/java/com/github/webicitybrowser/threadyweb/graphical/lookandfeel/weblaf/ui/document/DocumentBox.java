package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.document;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.SolidBoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebDefaults;

public class DocumentBox implements ChildrenBox {
	
	private final Context displayContext;
	private final BoxChildrenTracker childrenTracker;

	public DocumentBox(Context displayContext) {
		this.displayContext = displayContext;
		this.childrenTracker = new SolidBoxChildrenTracker(this, WebDefaults.INLINE_DISPLAY);
	}
	
	@Override
	public Context displayContext() {
		return this.displayContext;
	}

	@Override
	public BoxChildrenTracker getChildrenTracker() {
		return this.childrenTracker;
	}

}
