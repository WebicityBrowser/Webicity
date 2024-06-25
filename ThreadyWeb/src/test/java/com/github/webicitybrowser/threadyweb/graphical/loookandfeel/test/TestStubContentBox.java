package com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test;

import org.mockito.Mockito;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.directive.basics.pool.BasicDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class TestStubContentBox implements Box {

	private final boolean isFluid;
	private final AbsoluteSize size;
	private final DirectivePool styleDirectives;

	private AbsoluteSize lastTargetSize;

	public TestStubContentBox(boolean isFluid, AbsoluteSize size, DirectivePool styleDirectives) {
		this.isFluid = isFluid;
		this.size = size;
		this.styleDirectives = styleDirectives == null ?
			new BasicDirectivePool() :
			styleDirectives;
	}
	
	@Override
	public boolean isFluid() {
		return isFluid;
	}

	@Override
	@SuppressWarnings("unchecked")
	public UIDisplay<?, ?, ?> display() {
		UIDisplay<?, TestStubContentBox, RenderedUnit> display = Mockito.mock(UIDisplay.class);
		RenderedUnit renderedUnit = Mockito.mock(RenderedUnit.class);
		Mockito.when(renderedUnit.fitSize()).thenReturn(size);
		Mockito.when(renderedUnit.styleDirectives()).thenReturn(styleDirectives);
		Mockito.when(display.renderBox(Mockito.eq(this), Mockito.any(), Mockito.any())).thenAnswer((invocation) -> {
			LocalRenderContext localRenderContext = invocation.getArgument(2);
			lastTargetSize = localRenderContext.preferredSize();
			
			return renderedUnit;
		});

		return display;
	}

	@Override
	public DirectivePool styleDirectives() {
		return styleDirectives;
	}

	@Override
	public Component owningComponent() {
		throw new UnsupportedOperationException("Unimplemented method 'owningComponent'");
	}

	public AbsoluteSize getLastTargetSize() {
		return lastTargetSize;
	}

}
