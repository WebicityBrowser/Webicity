package everyos.browser.webicitybrowser.ui.event;

import everyos.browser.webicity.ui.Frame;
import everyos.browser.webicity.ui.renderer.Renderer;
import everyos.browser.webicitybrowser.event.EventListener;
import everyos.web.spec.uri.URL;

public interface FrameMutationEventListener extends EventListener {

	default void onNavigate(Frame frame, URL navigation) {};
	
	default void onRendererCreated(Frame frame, Renderer r) {};
	
	default void onClose(Frame frame) {};
	
}
