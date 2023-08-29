package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.imp;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.BuildableRenderedUnit;

public class BuildableRenderedUnitImp implements BuildableRenderedUnit {

	private final List<ChildLayoutResult> childUnits = new ArrayList<>(1);
	private final UIDisplay<?, ?, ?> display;
	private final DirectivePool styleDirectives;
	
	private AbsoluteSize preferredSize;
	private boolean markedFinished;

	public BuildableRenderedUnitImp(UIDisplay<?, ?, ?> display, DirectivePool styleDirectives) {
		this.display = display;
		this.styleDirectives = styleDirectives;
	}

	@Override
	public void setFitSize(AbsoluteSize preferredSize) {
		this.preferredSize = preferredSize;
	}

	@Override
	public AbsoluteSize fitSize() {
		if (this.preferredSize == null) {
			throw new IllegalStateException("Preferred size not set");
		}
		return this.preferredSize;
	}

	@Override
	public UIDisplay<?, ?, ?> display() {
		return this.display;
	}

	@Override
	public DirectivePool styleDirectives() {
		return this.styleDirectives;
	}

	@Override
	public void addChildUnit(RenderedUnit childUnit, Rectangle relativeRect) {
		this.childUnits.add(new ChildLayoutResult(childUnit, relativeRect));
	}

	@Override
	public ChildLayoutResult[] childLayoutResults() {
		return this.childUnits.toArray(ChildLayoutResult[]::new);
	}

	@Override
	public void markFinished() {
		this.markedFinished = true;
	}

	@Override
	public boolean wasMarkedFinished() {
		return this.markedFinished;
	}
	
}
