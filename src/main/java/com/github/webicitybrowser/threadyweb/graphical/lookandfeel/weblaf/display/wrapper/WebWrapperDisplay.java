package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.render.unit.MappingRenderedUnitGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.message.NoopMessageHandler;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class WebWrapperDisplay<T extends Context, U extends Box, V extends RenderedUnit> implements UIDisplay<WebWrapperContext<T>, WebWrapperBox<U, V>, WebWrapperUnit<V>> {

	private final UIDisplay<T, U, V> innerDisplay;

	public WebWrapperDisplay(UIDisplay<T, U, V> innerDisplay) {
		this.innerDisplay = innerDisplay;
	}

	@Override
	public WebWrapperContext<T> createContext(ComponentUI componentUI) {
		return new WebWrapperContext<>(componentUI, innerDisplay.createContext(componentUI));
	}

	@Override
	public List<WebWrapperBox<U, V>> generateBoxes(WebWrapperContext<T> displayContext, BoxContext boxContext, StyleGenerator styleGenerator) {
		DirectivePool styleDirectives = styleGenerator.getStyleDirectives();
		Component owningComponent = displayContext.componentUI().getComponent();
		return WebWrapperBoxGenerator.generateBoxes(styleDirectives, () -> {
			List<U> rawBoxes = innerDisplay.generateBoxes(displayContext.innerContext(), boxContext, styleGenerator);
			List<WebWrapperBox<U, V>> wrapperBoxes = new ArrayList<>(rawBoxes.size());
			for (U box: rawBoxes) {
				BoundBox<U, V> boundBox = BoundBox.create(box, innerDisplay);
				wrapperBoxes.add(new WebWrapperBox<U, V>(owningComponent, styleDirectives, innerDisplay, boundBox));
			}
			
			return wrapperBoxes;
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public RenderedUnitGenerator<WebWrapperUnit<V>> renderBox(WebWrapperBox<U, V> box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		RenderedUnitGenerator<V> innerGenerator = box.display().renderBox((U) box.innerBox().getRaw(), renderContext, localRenderContext);
		return new MappingRenderedUnitGenerator<>(innerGenerator, g -> new WebWrapperUnit<>(g.preferredSize(), box, g, box.display()));
	}

	@Override
	public void paint(WebWrapperUnit<V> unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		WebWrapperPainter.paint(unit, globalPaintContext, localPaintContext);
	}

	@Override
	public MessageHandler createMessageHandler(WebWrapperUnit<V> unit, Rectangle documentRect) {
		return new NoopMessageHandler();
	}

}
