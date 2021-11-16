package everyos.browser.webicitybrowser.tty.binding;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.webicitybrowser.WebicityInstance;
import everyos.browser.webicitybrowser.tty.console.TtygConsole;
import everyos.browser.webicitybrowser.ui.Window;
import everyos.browser.webicitybrowser.ui.event.InstanceMutationEventListener;

public class InstanceTTY {

	//TODO: Add tooltips
	private final WebicityInstance instance;
	private final MutationEventListener mutationListener;
	private List<WindowTTY> windows = new ArrayList<>();
	private final TtygConsole console;

	public InstanceTTY(WebicityInstance instance, TtygConsole console) {
		this.instance = instance;
		this.console = console;
		this.mutationListener = new MutationEventListener();
	}
	
	public void start() {
		configureConsoleDecorations(console);
		
		instance.addInstanceMutationListener(mutationListener);
		
		for (Window window: instance.getWindows()) {
			mutationListener.onWindowAdded(window);
		}
	}

	public void cleanup() {
		instance.removeInstanceMutationListener(mutationListener);
		for (WindowTTY window: windows) {
			window.cleanup();
		}
	}
	
	private class MutationEventListener implements InstanceMutationEventListener {
		@Override
		public void onWindowAdded(Window window) {
			WindowTTY windowTTY = new WindowTTY(window);
			windows.add(windowTTY);
			windowTTY.start();
		}
	}
	
	private void configureConsoleDecorations(TtygConsole console) {
		console.getDisplayPane();
	}

}
