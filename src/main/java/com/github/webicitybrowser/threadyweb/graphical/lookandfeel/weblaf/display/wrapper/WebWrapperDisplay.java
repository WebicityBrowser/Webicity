package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundRenderedUnitGenerator;
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

public class WebWrapperDisplay<T extends Context, U extends Box, V extends RenderedUnit> implements UIDisplay<WebWrapperContext<T>, WebWrapperBox, WebWrapperUnit<V>> {

	private final UIDisplay<T, U, V> innerDisplay;

	public WebWrapperDisplay(UIDisplay<T, U, V> innerDisplay) {
		this.innerDisplay = innerDisplay;
	}

	@Override
	public WebWrapperContext<T> createContext(ComponentUI componentUI) {
		return new WebWrapperContext<>(componentUI, innerDisplay.createContext(componentUI));
	}

	@Override
	public List<WebWrapperBox> generateBoxes(WebWrapperContext<T> displayContext, BoxContext boxContext, StyleGenerator styleGenerator) {
		DirectivePool styleDirectives = styleGenerator.getStyleDirectives();
		Component owningComponent = displayContext.componentUI().getComponent();
		return WebWrapperBoxGenerator.generateBoxes(owningComponent, styleDirectives, () -> {
			List<U> rawBoxes = innerDisplay.generateBoxes(displayContext.innerContext(), boxContext, styleGenerator);
			List<BoundBox<?, ?>> wrapperBoxes = new ArrayList<>(rawBoxes.size());
			for (U box: rawBoxes) {
				BoundBox<?, ?> boundBox = BoundBox.create(box, innerDisplay);
				wrapperBoxes.add(boundBox);
			}
			
			return wrapperBoxes;
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public RenderedUnitGenerator<WebWrapperUnit<V>> renderBox(WebWrapperBox box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		if (box instanceof WebWrapperWrapperBox) {
			WebWrapperWrapperBox<U, V> wrapperBox = (WebWrapperWrapperBox<U, V>) box;
			BoundRenderedUnitGenerator<V> innerGenerator = (BoundRenderedUnitGenerator<V>) wrapperBox.innerBox().render(renderContext, localRenderContext);

			return new WebWrapperUnitWrapperGenerator<>(innerGenerator, wrapperBox);
		} else {
			throw new IllegalArgumentException("Box must be a WebWrapperWrapperBox");
		}
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
