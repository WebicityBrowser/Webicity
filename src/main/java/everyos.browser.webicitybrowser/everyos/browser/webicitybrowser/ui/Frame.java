package everyos.browser.webicitybrowser.ui;

import java.io.Closeable;

import everyos.browser.webicitybrowser.ui.event.FrameMutationEventListener;
import everyos.web.spec.uri.URL;

public interface Frame extends Closeable {

	void start();
	
	String getName();
	
	Renderer getRenderer();
	
	URL getURL();
	
	void navigate(URL url);
	
	void reload();
	
	void back();
	
	void forward();
	
	void addFrameMutationListener(FrameMutationEventListener mutationListener, boolean sync);
	
	void removeFrameMutationListener(FrameMutationEventListener mutationListener);
	
}
