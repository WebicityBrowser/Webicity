package everyos.browser.webicitybrowser.ui;

import java.io.Closeable;

import everyos.browser.webicity.event.NavigateEvent;
import everyos.browser.webicity.net.URL;
import everyos.browser.webicity.renderer.Renderer;
import everyos.browser.webicitybrowser.WebicityInstance;
import everyos.browser.webicitybrowser.event.EventDispatcher;
import everyos.browser.webicitybrowser.ui.event.FrameMutationEventListener;
import everyos.browser.webicitybrowser.ui.event.TabMutationEventListener;

public class Tab implements Closeable {
	
	private final Frame frame;
	private final EventDispatcher<TabMutationEventListener> mutationEventDispatcher;
	private final FrameMutationListener frameMutationListener;

	public Tab(WebicityInstance instance) {
		this.frame = new Frame(instance);
		
		this.mutationEventDispatcher = new EventDispatcher<>();
		this.frameMutationListener = new FrameMutationListener();
	}
	
	public void start() {
		frame.addFrameMutationListener(frameMutationListener);
	};

	@Override
	public void close() {
		frame.removeFrameMutationListener(frameMutationListener);
		frame.close();
		mutationEventDispatcher.fire(l->l.onClose());
	}

	public String getName() {
		return frame.getName();
	}

	public URL getURL() {
		return frame.getURL();
	}
	
	public void setURL(URL url) {
		frame.setURL(url);
	}
	
	public void reload() {
		frame.reload();
	}
	
	public void back() {
		frame.back();
	}
	
	public void forward() {
		frame.forward();
	}
	
	public Frame getFrame() {
		return frame;
	}

	public void addTabMutationListener(TabMutationEventListener mutationListener) {
		mutationEventDispatcher.addListener(mutationListener);
	}
	
	public void removeTabMutationListener(TabMutationEventListener mutationListener) {
		mutationEventDispatcher.removeListener(mutationListener);
	}
	
	private class FrameMutationListener implements FrameMutationEventListener {
		@Override
		public void onNavigate(NavigateEvent event) {
			mutationEventDispatcher.fire(l->l.onNavigate(event.getURL()));
		}
		
		@Override
		public void onRendererCreated(Renderer r) {
			r.addReadyHook(()->
				mutationEventDispatcher.fire(l->l.onTitleChange(frame.getName())));
		}
	}
}
