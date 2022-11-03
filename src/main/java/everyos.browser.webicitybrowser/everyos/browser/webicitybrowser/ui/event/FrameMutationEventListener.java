package everyos.browser.webicitybrowser.ui.event;

import everyos.browser.webicitybrowser.event.EventListener;
import everyos.browser.webicitybrowser.ui.Frame;
import everyos.browser.webicitybrowser.ui.Renderer;
import everyos.web.spec.uri.URL;

public interface FrameMutationEventListener extends EventListener {

	default void onNavigate(Frame frame, URL navigation) {};
	
	default void onRendererCreated(Frame frame, Renderer r) {};
	
	default void onClose(Frame frame) {};
	
}
