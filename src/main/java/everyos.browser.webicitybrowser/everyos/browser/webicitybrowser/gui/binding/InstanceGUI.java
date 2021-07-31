package everyos.browser.webicitybrowser.gui.binding;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import everyos.browser.webicitybrowser.WebicityInstance;
import everyos.browser.webicitybrowser.gui.window.RibbonWindow;
import everyos.browser.webicitybrowser.ui.Window;
import everyos.browser.webicitybrowser.ui.event.InstanceMutationEventListener;

public class InstanceGUI {
	//TODO: Add tooltips
	private WebicityInstance instance;
	private MutationEventListener mutationListener;
	private List<WindowGUI> windows = new ArrayList<>();
	private Supplier<RibbonWindow> windowSupplier;

	public InstanceGUI(WebicityInstance instance, Supplier<RibbonWindow> windowSupplier) {
		this.instance = instance;
		this.windowSupplier = windowSupplier;
	}
	
	public void start() {
		mutationListener = new MutationEventListener();
		instance.addInstanceMutationListener(mutationListener);
		
		for (Window window: instance.getWindows()) {
			mutationListener.onWindowAdded(window);
		}
	}
	
	public void cleanup() {
		instance.removeInstanceMutationListener(mutationListener);
		for (WindowGUI window: windows) {
			window.cleanup();
		}
	}
	
	private class MutationEventListener implements InstanceMutationEventListener {
		@Override
		public void onWindowAdded(Window window) {
			WindowGUI windowGUI = new WindowGUI(window, windowSupplier.get());
			windows.add(windowGUI);
			windowGUI.start();
		}
	}
}
