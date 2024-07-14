package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element;

import com.github.webicitybrowser.thready.gui.graphical.layout.core.SolidLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.SolidBoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.CloneBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.threadyweb.graphical.directive.InnerDisplayDirective;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.box.InlineBoxChildrenTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebDefaults;
import com.github.webicitybrowser.threadyweb.graphical.value.InnerDisplay;

public class ElementInlineBox implements ElementBox, CloneBox {

	private final BoxChildrenTracker childrenTracker;
	
	private final Context displayContext;
	private final SolidLayoutManager layout;
	private final boolean managesSelf;

	public ElementInlineBox(Context displayContext, SolidLayoutManager layout) {
		this.displayContext = displayContext;
		this.layout = layout;
		this.managesSelf = computeSelfManages();
		this.childrenTracker = managesSelf ?
			new SolidBoxChildrenTracker(this, WebDefaults.INLINE_DISPLAY) :
			new InlineBoxChildrenTracker(this);
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
	
	@Override
	public boolean isFluid() {
		return true;
	}

	@Override
	public boolean managesSelf() {
		return this.managesSelf;
	}

	@Override
	public CloneBox cloneEmpty() {
		return new ElementInlineBox(displayContext, layout);
	}

	private boolean computeSelfManages() {
		InnerDisplay innerDisplay = displayContext.componentUI()
			.getStyleReference(InnerDisplayDirective.class).get()
			.getInnerDisplay();
		return innerDisplay == InnerDisplay.FLOW_ROOT || innerDisplay == InnerDisplay.FLEX;
	}

}
