package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.wrapper;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.WrapperBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public record WebWrapperWrapperBox<U extends Box, V extends RenderedUnit>(Component owningComponent, DirectivePool styleDirectives, BoundBox<U, V> innerBox) implements WrapperBox, WebWrapperBox {

	@Override
	public BoundBox<U, V> innerBox() {
		return innerBox;
	}

	@Override
	@SuppressWarnings("unchecked")
	public WrapperBox rewrap(BoundBox<?, ?> newInnerBox) {
		return new WebWrapperWrapperBox<>(owningComponent, styleDirectives, (BoundBox<U, V>) newInnerBox);
	}

}
