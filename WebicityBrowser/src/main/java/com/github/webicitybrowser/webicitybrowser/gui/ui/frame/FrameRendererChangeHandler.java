package com.github.webicitybrowser.webicitybrowser.gui.ui.frame;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent;
import com.github.webicitybrowser.webicity.core.component.FrameComponent;
import com.github.webicitybrowser.webicity.core.renderer.ExceptionRendererCrashReason;
import com.github.webicitybrowser.webicity.core.renderer.RendererCrashReason;
import com.github.webicitybrowser.webicity.core.renderer.RendererHandle;
import com.github.webicitybrowser.webicity.core.ui.FrameEventListener;
import com.github.webicitybrowser.webicity.renderer.backend.html.HTMLRendererBackend;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.core.ThreadyRendererFrontend;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.ThreadyHTMLRendererFrontend;

public class FrameRendererChangeHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(FrameRendererChangeHandler.class);
	
	public static void addFrameEventListener(ComponentUI componentUI, Consumer<ScreenContent> screenContentChangeListener) {
		((FrameComponent) componentUI.getComponent())
			.getFrame()
			.addEventListener(new FrameEventListener() {
				@Override
				public void onRendererChange(RendererHandle rendererHandle) {
					updateCurrentRenderer(rendererHandle, componentUI, screenContentChangeListener);
				}
			}, true);
	}
	
	private static void updateCurrentRenderer(RendererHandle rendererHandle, ComponentUI componentUI, Consumer<ScreenContent> screenContentChangeListener) {
		ScreenContent content = bindRenderer(rendererHandle);
		if (content == null) return;
		content.onRedrawRequest(() -> componentUI.invalidate(InvalidationLevel.PAINT));
		screenContentChangeListener.accept(content);
		componentUI.invalidate(InvalidationLevel.STYLE);
	}
	
	private static ScreenContent bindRenderer(RendererHandle rendererHandle) {
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
