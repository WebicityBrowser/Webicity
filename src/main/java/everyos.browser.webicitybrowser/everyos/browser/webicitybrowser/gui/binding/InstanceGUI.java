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
	private final WebicityInstance instance;
	private final MutationEventListener mutationListener;
	private final List<WindowGUI> windows;
	private final Supplier<RibbonWindow> windowSupplier;

	public InstanceGUI(WebicityInstance instance, Supplier<RibbonWindow> windowSupplier) {
		this.instance = instance;
		this.windowSupplier = windowSupplier;
		this.mutationListener = new MutationEventListener();
		this.windows = new ArrayList<>();
	}
	
	public void start() {
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
