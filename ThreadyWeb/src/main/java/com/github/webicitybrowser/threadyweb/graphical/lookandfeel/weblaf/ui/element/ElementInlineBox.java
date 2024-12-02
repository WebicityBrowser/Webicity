package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.SolidLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.SolidBoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.CloneBox;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.box.InlineBoxChildrenTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebDefaults;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.directive.WebDirectiveUtil;
import com.github.webicitybrowser.threadyweb.graphical.value.InnerDisplay;

public class ElementInlineBox implements ElementBox, CloneBox {

	private final BoxChildrenTracker childrenTracker;
	
	private final UIDisplay<?, ?, ?> display;
	private final ComponentUI componentUI;
	private final DirectivePool styleDirectives;
	private final SolidLayoutManager layout;
	private final boolean managesSelf;

	public ElementInlineBox(
		UIDisplay<?, ?, ?> display, ComponentUI componentUI, DirectivePool styleDirectives, SolidLayoutManager layout
	) {
		this.display = display;
		this.componentUI = componentUI;
		this.styleDirectives = styleDirectives;
		this.layout = layout;
		this.managesSelf = computeSelfManages();
		this.childrenTracker = managesSelf ?
			new SolidBoxChildrenTracker(this, WebDefaults.INLINE_DISPLAY) :
			new InlineBoxChildrenTracker(this);
	}

	@Override
	public UIDisplay<?, ?, ?> display() {
		return this.display;
	}
	
	@Override
	public ComponentUI componentUI() {
		return this.componentUI;
	}

	@Override
	public DirectivePool styleDirectives() {
		return this.styleDirectives;
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
		return new ElementInlineBox(display, componentUI, styleDirectives, layout);
	}

	private boolean computeSelfManages() {
		InnerDisplay innerDisplay = WebDirectiveUtil.getInnerDisplay(styleDirectives);
		return innerDisplay == InnerDisplay.FLOW_ROOT || innerDisplay == InnerDisplay.FLEX;
	}

}
