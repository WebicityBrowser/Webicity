package everyos.browser.webicity.core.ui;

import everyos.browser.webicity.core.ui.event.FrameMutationEventListener;
import everyos.browser.webicity.core.ui.renderer.Renderer;
import everyos.web.spec.uri.URL;

public interface Frame {

	void start();
	
	void close();
	
	String getName();
	
	Renderer getCurrentRenderer();
	
	URL getURL();
	
	void navigate(URL url);
	
	void reload();
	
	void back();
	
	void forward();
	
	void addFrameMutationListener(FrameMutationEventListener mutationListener, boolean sync);
	
	void removeFrameMutationListener(FrameMutationEventListener mutationListener);
	
}
