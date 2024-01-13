package com.github.webicitybrowser.thready.gui.graphical.base.imp;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.base.GUIContent;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.base.imp.message.ContentMessageHandler;
import com.github.webicitybrowser.thready.gui.graphical.base.imp.stage.box.BoxContextImp;
import com.github.webicitybrowser.thready.gui.graphical.base.imp.stage.composite.ContentCompositor;
import com.github.webicitybrowser.thready.gui.graphical.base.imp.stage.paint.ContentPainter;
import com.github.webicitybrowser.thready.gui.graphical.base.imp.stage.render.RenderCacheImp;
import com.github.webicitybrowser.thready.gui.graphical.base.imp.stage.render.RenderContextImp;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeLayer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.thready.windowing.core.event.ScreenEvent;

public class GUIContentImp implements GUIContent {
	
	private static final Logger logger = LoggerFactory.getLogger(GUIContentImp.class);

	private final ContentMessageHandler messageHandler = new ContentMessageHandler();
	
	private InvalidationLevel invalidationLevel = InvalidationLevel.NONE;
	
	private ComponentUI rootUI;
	private GUIContentConfiguration configuration;
	
	private boolean redrawRequested = false;
	private AbsoluteSize oldContentSize;
	private Object rootContext;
	private Box rootBox;
	private List<CompositeLayer> compositeLayers;
	private RenderedUnit rootUnit;

	@Override
	public void setRoot(GUIContentConfiguration configuration) {
		this.redrawRequested = true;
		this.rootUI = createRootUI(configuration.component(), configuration.lookAndFeel());
		this.rootContext = rootUI.getRootDisplay().createContext(rootUI);
		this.configuration = configuration;
		this.invalidationLevel = InvalidationLevel.BOX;
	}

	@Override
	public boolean redrawRequested() {
		return this.redrawRequested;
	}

	@Override
	public void redraw(ScreenContentRedrawContext redrawContext) {
		this.redrawRequested = false;
		if (rootUI != null && configuration.lookAndFeel() != null) {
			detectPipelineInvalidations(redrawContext);
			performRenderPipeline(redrawContext);
		}
	}

	@Override
	public void handleEvent(ScreenEvent e, AbsoluteSize contentSize) {
		Rectangle rootDocumentRect = new Rectangle(AbsolutePosition.ZERO_POSITION, contentSize);
		messageHandler.handleEvent(e, rootUnit, rootDocumentRect);
	}

	private ComponentUI createRootUI(Component component, LookAndFeel lookAndFeel) {
		ComponentUI dummyUI = new RootUI() {
			@Override
			public void invalidate(InvalidationLevel level) {
				if (level.compareTo(invalidationLevel) > 0) {
					invalidationLevel = level;
					redrawRequested = true;
				}
			}

			@Override
			public UIDisplay<?, ?, ?> getRootDisplay() {
				return null;
			}
		};
		
		return lookAndFeel.createUIFor(component, dummyUI);
	}

	private void performRenderPipeline(ScreenContentRedrawContext redrawContext) {
		if (invalidationLevel == InvalidationLevel.BOX) {
			performBoxCycle();
		}
		
		if (this.rootBox == null) {
			return;
		}
		
		switch (invalidationLevel) {
		case BOX:
		case RENDER:
			performRenderCycle(redrawContext);
			System.gc();
		case COMPOSITE:
			this.compositeLayers = ContentCompositor.performCompositeCycle(redrawContext, rootUnit);
		case PAINT:
		case PAINT_LAYERS:
		case NONE:
			// Even if the invalidation level is NONE, there is
			// probably a reason that redraw was called.
			// For example, if a buffer must be drawn twice.
			ContentPainter.performPaintCycle(redrawContext, compositeLayers);
			invalidationLevel = InvalidationLevel.NONE;
			break;
		default:
			logger.warn("Reached unhandled invalidation level: " + invalidationLevel);
			invalidationLevel = InvalidationLevel.NONE;
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends Context> void performBoxCycle() {
		BoxContext boxContext = new BoxContextImp(configuration.lookAndFeel());
		StyleGenerator styleGenerator = configuration.styleGeneratorRoot().generateChildStyleGenerator(rootUI);
		UIDisplay<T, ?, ?> rootDisplay = (UIDisplay<T, ?, ?>) rootUI.getRootDisplay();
		
		List<? extends Box> generatedBoxes = rootDisplay.generateBoxes((T) rootContext, boxContext, styleGenerator);
		if (generatedBoxes.size() == 0) {
			this.rootBox = null;
			return;
		}
		
		Box rootBox = generatedBoxes.get(0);
		
		this.rootBox = rootBox;
	}

	@SuppressWarnings("unchecked")
	private <U extends Box> void performRenderCycle(ScreenContentRedrawContext redrawContext) {
		AbsoluteSize contentSize = redrawContext.contentSize();
		Font2D baseFont = redrawContext.resourceLoader().loadFont(configuration.fontSettings());
		GlobalRenderContext globalRenderContext = new RenderContextImp(
			contentSize, redrawContext.resourceLoader(), baseFont.getMetrics(), new RenderCacheImp());
		LocalRenderContext localRenderContext = LocalRenderContext.create(
			contentSize, baseFont.getMetrics(), new ContextSwitch[0]);
		UIDisplay<?, U, ?> rootDisplay = (UIDisplay<?, U, ?>) rootUI.getRootDisplay();
		this.rootUnit = rootDisplay.renderBox((U) rootBox, globalRenderContext, localRenderContext);
	}
	
	private void detectPipelineInvalidations(ScreenContentRedrawContext redrawContext) {
		if (!redrawContext.contentSize().equals(oldContentSize) && invalidationLevel.compareTo(InvalidationLevel.RENDER) < 0) {
			oldContentSize = redrawContext.contentSize();
			this.invalidationLevel = InvalidationLevel.RENDER;
		}
	}
	
	
	
}
