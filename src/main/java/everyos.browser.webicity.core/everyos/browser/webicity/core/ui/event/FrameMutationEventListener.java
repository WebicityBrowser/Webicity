package everyos.browser.webicity.core.ui.event;

import everyos.browser.webicity.core.ui.Frame;
import everyos.browser.webicity.core.ui.renderer.Renderer;
import everyos.browser.webicitybrowser.event.EventListener;
import everyos.web.spec.uri.URL;

public interface FrameMutationEventListener extends EventListener {

	default void onNavigate(Frame frame, URL navigation) {};
	
	default void onRendererCreated(Frame frame, Renderer r) {};
	
	default void onClose(Frame frame) {};
	
}
