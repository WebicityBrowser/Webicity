package com.github.webicitybrowser.thready.gui.graphical.base.imp;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.base.GUIContent;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.graphical.message.keyboard.CharMessage;
import com.github.webicitybrowser.thready.gui.graphical.message.keyboard.KeyMessage;
import com.github.webicitybrowser.thready.gui.graphical.message.mouse.MouseMessage;
import com.github.webicitybrowser.thready.gui.message.FocusManager;
import com.github.webicitybrowser.thready.gui.message.Message;
import com.github.webicitybrowser.thready.gui.message.MessageContext;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.thready.windowing.core.event.ScreenEvent;
import com.github.webicitybrowser.thready.windowing.core.event.mouse.MouseConstants;

public class GUIContentImp implements GUIContent {
	
	private static final Logger logger = LoggerFactory.getLogger(GUIContentImp.class);
	
	private final FocusManager focusManager = new FocusManagerImp();
	
	private InvalidationLevel invalidationLevel = InvalidationLevel.NONE;
	
	private ComponentUI rootUI;
	private GUIContentConfiguration configuration;
	
	private boolean redrawRequested = false;
	private AbsoluteSize oldContentSize;
	private Object rootContext;
	private Box rootBox;
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
		if (rootUnit != null) {
			Message message = MessageConverter.convertEventToMessage(e);
			if (message == null) {
				return;
			}
			
			MessageContext messageContext = createMessageContext();
			if (message instanceof KeyMessage || message instanceof CharMessage) {
				focusManager.messageFocused(messageContext, message);
			} else {
				messageRoot(messageContext, message, contentSize);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private <V extends RenderedUnit> void messageRoot(MessageContext messageContext, Message message, AbsoluteSize contentSize) {
		resetFocusIfClick(messageContext, message);
		UIDisplay<?, ?, V> display = (UIDisplay<?, ?, V>) rootUI.getRootDisplay();
		display
			.createMessageHandler((V) rootUnit, createDocumentRect(contentSize))
			.onMessage(messageContext, message);
	}

	private void resetFocusIfClick(MessageContext messageContext, Message message) {
		if (
			message instanceof MouseMessage mouseMessage &&
			mouseMessage.getScreenEvent().getAction() == MouseConstants.PRESS
		) {
			focusManager.setFocused(null, messageContext);
		}
	}

	private MessageContext createMessageContext() {
		return () -> focusManager;
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
		case PAINT:
		case NONE:
			// Even if the invalidation level is NONE, there is
			// probably a reason that redraw was called.
			// For example, if a buffer must be drawn twice.
			performPaintCycle(redrawContext);
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
	
	@SuppressWarnings("unchecked")
	private <V extends RenderedUnit> void performPaintCycle(ScreenContentRedrawContext redrawContext) {
		AbsoluteSize contentSize = redrawContext.contentSize();
		Canvas2D canvas = redrawContext.canvas();
		Rectangle viewport = new Rectangle(new AbsolutePosition(0, 0), contentSize);
		
		clearPaint(canvas, contentSize);
		
		UIDisplay<?, ?, V> display = (UIDisplay<?, ?, V>) rootUI.getRootDisplay();
		GlobalPaintContext globalPaintContext = new PaintContextImp(viewport, redrawContext.invalidationScheduler());
		LocalPaintContext localPaintContext = new LocalPaintContext(canvas, createDocumentRect(contentSize));
		display.paint((V) rootUnit, globalPaintContext, localPaintContext);
	}

	private void clearPaint(Canvas2D canvas, AbsoluteSize contentSize) {
		// TODO: What if we have a transparent window?
		// The default paint is white
		canvas.drawRect(0, 0, contentSize.width(), contentSize.height());
	}
	
	private void detectPipelineInvalidations(ScreenContentRedrawContext redrawContext) {
		if (!redrawContext.contentSize().equals(oldContentSize) && invalidationLevel.compareTo(InvalidationLevel.RENDER) < 0) {
			oldContentSize = redrawContext.contentSize();
			this.invalidationLevel = InvalidationLevel.RENDER;
		}
	}
	
	private Rectangle createDocumentRect(AbsoluteSize windowSize) {
		return new Rectangle(new AbsolutePosition(0, 0), windowSize);
	}
	
}
