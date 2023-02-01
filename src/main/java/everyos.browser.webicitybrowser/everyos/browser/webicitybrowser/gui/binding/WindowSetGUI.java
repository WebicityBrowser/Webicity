package everyos.browser.webicitybrowser.gui.binding;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import everyos.browser.webicitybrowser.gui.window.GUIWindow;
import everyos.browser.webicitybrowser.ui.Window;
import everyos.browser.webicitybrowser.ui.WindowSet;
import everyos.browser.webicitybrowser.ui.event.WindowSetMutationEventListener;
import everyos.desktop.thready.core.gui.component.Component;

public class WindowSetGUI {

	private final WindowSet windowSet;
	private final BiConsumer<Component, Consumer<GUIWindow>> windowCreator;

	public WindowSetGUI(WindowSet windowSet, BiConsumer<Component, Consumer<GUIWindow>> windowCreator) {
		this.windowSet = windowSet;
		this.windowCreator = windowCreator;
	}

	public void start() {
		addWindowEventListeners();
	}

	private void addWindowEventListeners() {
		windowSet.addWindowSetMutationEventListener(new WindowSetMutationEventListener() {
		
			public void onWindowAdded(WindowSet windowSet, Window window) {
				createWindowGUI(window);
			}
			
			public void onClose(WindowSet windowSet) {}
			
		}, true);
	}

	private void createWindowGUI(Window window) {
		WindowGUI windowGUI = new WindowGUI(window, windowCreator);
		windowGUI.start();
	}
	
}
