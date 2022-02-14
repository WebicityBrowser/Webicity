package everyos.browser.webicitybrowser.ui;

import java.util.Stack;

import everyos.browser.spec.jnet.URL;
import everyos.browser.webicity.WebicityFrame;
import everyos.browser.webicity.event.FrameCallback;
import everyos.browser.webicity.event.NavigateEvent;
import everyos.browser.webicity.renderer.Renderer;
import everyos.browser.webicitybrowser.WebicityInstance;
import everyos.browser.webicitybrowser.event.EventDispatcher;
import everyos.browser.webicitybrowser.ui.event.FrameMutationEventListener;

public class Frame {
	private final WebicityInstance instance;
	private final EventDispatcher<FrameMutationEventListener> mutationEventDispatcher;
	private final Stack<URL> history;
	private final Stack<URL> forwardHistory;
	
	private WebicityFrame frame;
	
	public Frame(WebicityInstance instance) {
		this.instance = instance;
		
		this.mutationEventDispatcher = new EventDispatcher<>();
		this.history = new Stack<>();
		this.forwardHistory = new Stack<>();
	}
	
	public String getName() {
		String name = frame.getTitle();
		if (name != null) {
			return name;
		}
		
		String host = getURL().getHost();
		return host.isEmpty() ? "New tab" : host;
	}

	public URL getURL() {
		if (frame==null) {
			return URL.ofSafe("about:blank");
		}
		
		return frame.getURL();
	}
	
	public void setURL(URL url) {
		setURL(url, true);
	}
	
	private void setURL(URL url, boolean clearForwardHistory) {
		//TODO: Use a "thread context" instead
		if (clearForwardHistory) {
			forwardHistory.clear();
		}
		
		history.push(url);
		this.frame = createFrame(url);
		mutationEventDispatcher.fire(l->l.onNavigate(()->url));
	}
	
	public void reload() {
		if (frame==null) {
			return;
		}
		
		URL url = frame.getURL();
		this.frame = createFrame(url);
		mutationEventDispatcher.fire(l->l.onNavigate(()->url));
	}
	
	public void back() {
		if (history.size()<2) {
			return;
		}
		
		forwardHistory.push(history.pop());
		setURL(history.pop(), false);
	}
	
	public void forward() {
		if (forwardHistory.isEmpty()) {
			return;
		}
		
		setURL(forwardHistory.pop(), false);
	}
	
	public Renderer getCurrentRenderer() {
		if (this.frame == null) {
			return null;
		}
		
		return frame.getRenderer();
	}

	public void addFrameMutationListener(FrameMutationEventListener mutationListener) {
		mutationEventDispatcher.addListener(mutationListener);
	}
	
	public void removeFrameMutationListener(FrameMutationEventListener mutationListener) {
		mutationEventDispatcher.removeListener(mutationListener);
	}
	
	private WebicityFrame createFrame(URL url) {
		if (frame != null) {
			frame.quit();
		}
		
		WebicityFrame frame = new WebicityFrame(instance.getEngine(), new FrameCallbackImp(), url, instance.getEngine().createThreadQueue());
		
		return frame;
	}
	
	private class FrameCallbackImp implements FrameCallback {
		@Override
		public void onNavigate(NavigateEvent navigateEvent) {
			setURL(navigateEvent.getURL());
			mutationEventDispatcher.fire(l->l.onNavigate(navigateEvent));
		}

		@Override
		public void onRendererCreated(Renderer r) {
			mutationEventDispatcher.fire(l->l.onRendererCreated(r));
		}
	}

	public void close() {
		frame.quit();
	}
}
