package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.scroll;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public class ScrollUnit implements RenderedUnit {

	private final ScrollBox box;
	private final AbsoluteSize fitSize;
	private final RenderedUnit innerUnit;
	private final AbsoluteSize innerUnitSize;

	private final ScrollState verticalScrollState;
	private final ScrollState horizontalScrollState;

	public ScrollUnit(ScrollBox box, AbsoluteSize fitSize, RenderedUnit innerUnit, AbsoluteSize innerUnitSize) {
		this.box = box;
		this.fitSize = fitSize;
		this.innerUnit = innerUnit;
		this.innerUnitSize = innerUnitSize;

		ScrollContext scrollContext = box.scrollContext();
		this.verticalScrollState = new ScrollState(scrollContext);
		this.horizontalScrollState = new ScrollState(scrollContext);
	}

	@Override
	public UIDisplay<?, ?, ?> display() {
		return box.display();
	}

	@Override
	public AbsoluteSize fitSize() {
		return fitSize;
	}

	@Override
	public DirectivePool styleDirectives() {
		return box.styleDirectives();
	}

	public ScrollBox box() {
		return box;
	}

	public RenderedUnit innerUnit() {
		return innerUnit;
	}

	public AbsoluteSize innerUnitSize() {
		return innerUnitSize;
	}

	public ScrollState verticalScrollState() {
		return verticalScrollState;
	}

	public ScrollState horizontalScrollState() {
		return horizontalScrollState;
	}

}
