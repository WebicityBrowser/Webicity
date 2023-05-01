package com.github.webicitybrowser.webicitybrowser.gui.binding;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.webicitybrowser.BrowserInstance;
import com.github.webicitybrowser.webicitybrowser.gui.window.GUIWindow;
import com.github.webicitybrowser.webicitybrowser.ui.Window;
import com.github.webicitybrowser.webicitybrowser.ui.WindowSet;
import com.github.webicitybrowser.webicitybrowser.ui.event.WindowSetMutationEventListener;

public class WindowSetGUI {

	private final WindowSet windowSet;
	private final BiConsumer<Component, Consumer<GUIWindow>> windowCreator;
	private final BrowserInstance browserInstance;

	public WindowSetGUI(BrowserInstance browserInstance, WindowSet windowSet, BiConsumer<Component, Consumer<GUIWindow>> windowCreator) {
		this.windowSet = windowSet;
		this.windowCreator = windowCreator;
		this.browserInstance = browserInstance;
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
		WindowGUI windowGUI = new WindowGUI(browserInstance, window, windowCreator);
		windowGUI.start();
	}
	
}
