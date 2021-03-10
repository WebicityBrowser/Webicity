package everyos.browser.webicitybrowser.ui.event;

import everyos.browser.webicity.event.NavigateEvent;
import everyos.browser.webicity.renderer.Renderer;
import everyos.browser.webicitybrowser.event.EventListener;

public interface FrameMutationEventListener extends EventListener {
	default void onNavigate(NavigateEvent event) {};
	default void onRendererCreated(Renderer r) {};
}
