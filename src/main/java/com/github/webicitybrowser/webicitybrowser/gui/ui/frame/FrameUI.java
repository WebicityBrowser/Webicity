package com.github.webicitybrowser.webicitybrowser.gui.ui.frame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.BasicBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.stage.style.SimpleStylePoolGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleBoxGenerator;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent;
import com.github.webicitybrowser.webicity.core.component.FrameComponent;
import com.github.webicitybrowser.webicity.core.renderer.ExceptionRendererCrashReason;
import com.github.webicitybrowser.webicity.core.renderer.RendererCrashReason;
import com.github.webicitybrowser.webicity.core.renderer.RendererHandle;
import com.github.webicitybrowser.webicity.renderer.backend.html.HTMLRendererBackend;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.core.ThreadyRendererFrontend;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.ThreadyHTMLRendererFrontend;

public class FrameUI implements ComponentUI {
	
	private static final Logger logger = LoggerFactory.getLogger(FrameUI.class);
	
	private final FrameComponent component;
	private final ComponentUI parent;
	private final SimpleStylePoolGenerator stylePoolGenerator;

	// TODO: Update whenever frame has new renderer
	private ScreenContent screenContent;

	public FrameUI(Component component, ComponentUI parent) {
		this.component = (FrameComponent) component;
		this.parent = parent;
		this.stylePoolGenerator = new SimpleStylePoolGenerator(this, component.getStyleDirectives());
		addFrameEventListener();
	}

	@Override
	public Component getComponent() {
		return this.component;
	}
	
	@Override
	public void invalidate(InvalidationLevel level) {
		parent.invalidate(level);
	}

	@Override
	public Box[] generateBoxes(BoxContext context, DirectivePool parentDirectives, StyleGenerator styleGenerator) {
		if (screenContent == null) {
			return SimpleBoxGenerator.generateBoxes(() -> new Box[0]);
		}
		
		DirectivePool directives = stylePoolGenerator.createStylePool(parentDirectives, styleGenerator);
		return SimpleBoxGenerator.generateBoxes(() -> new Box[] {
			new BasicBox(
				component, directives,
				(box, children) -> new FrameComponentRenderer(box, screenContent))
		});
	}
	
	private void addFrameEventListener() {
		component
			.getFrame()
			.addEventListener(rendererHandle -> updateCurrentRenderer(rendererHandle), true);
	}
	
	private void updateCurrentRenderer(RendererHandle rendererHandle) {
		this.screenContent = bindRenderer(rendererHandle);
		invalidate(InvalidationLevel.BOX); // TODO: Detect screen invalidation level
	}
	
	private ScreenContent bindRenderer(RendererHandle rendererHandle) {
		if (rendererHandle.getCrashReason().isPresent()) {
			logCrash(rendererHandle.getCrashReason().get());
			return null; // TODO: Return crash message
		}
		
		HTMLRendererBackend backend = (HTMLRendererBackend) rendererHandle.getRenderer().get();
		ThreadyRendererFrontend frontend = backend.createFrontend(
			context -> new ThreadyHTMLRendererFrontend(backend, context));
		
		return frontend.getContent();
	}
	
	private static void logCrash(RendererCrashReason rendererCrashReason) {
		String message = "Renderer crashed! Error: " + rendererCrashReason.getTitle();
		if (rendererCrashReason instanceof ExceptionRendererCrashReason exceptionCrashReason) {
			Exception reason = exceptionCrashReason.getException();
			logger.error(message, reason);
		} else {
			logger.error(message);
		}
	}

}