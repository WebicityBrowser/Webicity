package com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test;

import org.mockito.Mockito;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class TestStubSizeDependentBox implements Box {

	private final boolean isFluid;
	private final DirectivePool styleDirectives;

	public TestStubSizeDependentBox(boolean isFluid, DirectivePool styleDirectives) {
		this.isFluid = isFluid;
		this.styleDirectives = styleDirectives;
	}
	
	@Override
	public boolean isFluid() {
		return isFluid;
	}

	@Override
	@SuppressWarnings("unchecked")
	public UIDisplay<?, ?, ?> display() {
		UIDisplay<?, TestStubSizeDependentBox, RenderedUnit> display = Mockito.mock(UIDisplay.class);
		Mockito.when(display.renderBox(Mockito.eq(this), Mockito.any(), Mockito.any())).thenAnswer((invocation) -> {
			LocalRenderContext localRenderContext = invocation.getArgument(2);
			
			RenderedUnit renderedUnit = Mockito.mock(RenderedUnit.class);
			Mockito.when(renderedUnit.fitSize()).thenReturn(
				localRenderContext.preferredSize().width() >= 50 || localRenderContext.preferredSize().width() == RelativeDimension.UNBOUNDED
					? new AbsoluteSize(50, 10)
					: new AbsoluteSize(25, 10));
			Mockito.when(renderedUnit.styleDirectives()).thenReturn(styleDirectives);

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

}
