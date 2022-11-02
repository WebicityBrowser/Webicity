package everyos.browser.webicitybrowser.ui.event;

import everyos.browser.webicitybrowser.event.EventListener;
import everyos.browser.webicitybrowser.ui.Renderer;
import everyos.web.spec.uri.URL;

public interface FrameMutationEventListener extends EventListener {

	default void onNavigate(URL navigation) {};
	
	default void onRendererCreated(Renderer r) {};
	
}
