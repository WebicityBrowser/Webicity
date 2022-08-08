package everyos.browser.webicity.event;

import everyos.browser.webicity.renderer.Renderer;

public interface FrameCallback {
	
	default void onRendererCreated(Renderer r) {};
	
	default void onNavigate(NavigateEvent navigateEvent) {};
	
}
