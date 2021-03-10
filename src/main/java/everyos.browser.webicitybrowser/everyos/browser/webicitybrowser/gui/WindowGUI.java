package everyos.browser.webicitybrowser.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import everyos.browser.webicitybrowser.gui.RibbonWindow.WindowGrip;
import everyos.browser.webicitybrowser.gui.behavior.ActionButtonBehavior;
import everyos.browser.webicitybrowser.gui.component.CircularText;
import everyos.browser.webicitybrowser.ui.Tab;
import everyos.browser.webicitybrowser.ui.Window;
import everyos.browser.webicitybrowser.ui.event.WindowMutationEventListener;
import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.renderer.awtrenderer.RibbonAWTMonitor.NoMonitorAvailableException;
import everyos.engine.ribbon.renderer.guirenderer.directive.BackgroundDirective;
import everyos.engine.ribbon.renderer.guirenderer.directive.ForegroundDirective;
import everyos.engine.ribbon.renderer.guirenderer.directive.PositionDirective;
import everyos.engine.ribbon.renderer.guirenderer.directive.SizeDirective;
import everyos.engine.ribbon.renderer.guirenderer.graphics.Color;
import everyos.engine.ribbon.renderer.guirenderer.shape.Location;

public class WindowGUI {
	private Window window;
	private Component pane;
	private WindowGrip windowGrip;
	private WindowEventListener mutationListener;
	private List<TabGUI> tabs = new ArrayList<>();
	private TabGUI selected;
	private Component tabPane;
	private Component tabPaneContainer;

	public WindowGUI(Window window) {
		this.window = window;
	}

	public void start() {
		try {
			this.windowGrip = RibbonWindow.create();
		} catch (NoMonitorAvailableException e) {
			throw new RuntimeException(e);
		}
		
		this.pane = windowGrip.getDisplayPane();
		configureWindow(pane);
		
		mutationListener = new WindowEventListener();
		window.addWindowMutationListener(mutationListener);
		
		for (Tab tab: window.getTabs()) {
			mutationListener.onTabAdded(window, tab);
		}
		if (tabs.size()>0) selectTab(tabs.get(0));
	}


	public void cleanup() {
		windowGrip.close();
		
		window.removeWindowMutationListener(mutationListener);
		for (TabGUI tab: tabs) {
			tab.cleanup();
		}
	}
	
	private void configureWindow(Component pane) {
		pane.directive(BackgroundDirective.of(Color.DARK_GRAY));
		pane.directive(ForegroundDirective.of(Styling.FOREGROUND_PRIMARY));
		
		Component innerPane = new BlockComponent(null);
		innerPane.directive(SizeDirective.of(new Location(1, -2, 1, -2)));
		innerPane.directive(PositionDirective.of(new Location(0, 1, 0, 1)));
		innerPane.directive(BackgroundDirective.of(Color.WHITE));
		pane.addChild(innerPane);
		
		Component windowDecor = createWindowDecorations();
		int decorHeight = Styling.BUTTON_WIDTH+(int)(Styling.ELEMENT_PADDING*1.5);
		windowDecor.directive(SizeDirective.of(new Location(1, 0, 0, decorHeight)));
		innerPane.addChild(windowDecor);
		
		this.tabPaneContainer = new BlockComponent(null);
		tabPaneContainer.directive(SizeDirective.of(new Location(1, 0, 1, -decorHeight)));
		tabPaneContainer.directive(PositionDirective.of(new Location(0, 0, 0, decorHeight)));
		innerPane.addChild(tabPaneContainer);
	}
	
	private Component createWindowDecorations() {
		BlockComponent windowDecor = new BlockComponent(null);
		windowDecor.directive(BackgroundDirective.of(Styling.BACKGROUND_PRIMARY));
		
		// Add the menu button
		CircularText menuButton = new CircularText(null);
		menuButton.directive(BackgroundDirective.of(Styling.BACKGROUND_SECONDARY));
		menuButton.directive(PositionDirective.of(new Location(0, Styling.BORDER_PADDING, 0, Styling.ELEMENT_PADDING)));
		menuButton.directive(SizeDirective.of(new Location(
			0, (Styling.BUTTON_WIDTH+Styling.ELEMENT_PADDING)*3-Styling.ELEMENT_PADDING,
			0, Styling.BUTTON_WIDTH)));
		menuButton.text(Styling.PRODUCT_NAME);
		addButtonBehavior(menuButton, ()->{});
		
		windowDecor.addChild(menuButton);
		
		// Add the window action buttons
		CircularText minimizeButton = new CircularText(null);
		minimizeButton.directive(PositionDirective.of(new Location(
			1, -Styling.BORDER_PADDING-Styling.BUTTON_WIDTH*3-Styling.ELEMENT_PADDING*2,
			0, Styling.ELEMENT_PADDING)));
		minimizeButton.directive(SizeDirective.of(new Location(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)));
		minimizeButton.text("-");
		addButtonBehavior(minimizeButton, ()->windowGrip.minimize());
		
		windowDecor.addChild(minimizeButton);
		
		CircularText maximizeButton = new CircularText(null);
		maximizeButton.directive(PositionDirective.of(new Location(
			1, -Styling.BORDER_PADDING-Styling.BUTTON_WIDTH*2-Styling.ELEMENT_PADDING,
			0, Styling.ELEMENT_PADDING)));
		maximizeButton.directive(SizeDirective.of(new Location(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)));
		maximizeButton.text("+");
		addButtonBehavior(maximizeButton, ()->{});
		
		windowDecor.addChild(maximizeButton);
		
		CircularText closeButton = new CircularText(null);
		closeButton.directive(PositionDirective.of(new Location(1, -Styling.BORDER_PADDING-Styling.BUTTON_WIDTH, 0, Styling.ELEMENT_PADDING)));
		closeButton.directive(SizeDirective.of(new Location(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)));
		closeButton.text("X");
		addDangerousButtonBehavior(closeButton, ()->close());
		
		windowDecor.addChild(closeButton);
		
		// Create the tab pane
		this.tabPane = new BlockComponent(null);
		tabPane.directive(PositionDirective.of(new Location(
			0, Styling.BORDER_PADDING+(Styling.BUTTON_WIDTH+Styling.ELEMENT_PADDING)*3,
			0, Styling.ELEMENT_PADDING)));
		tabPane.directive(SizeDirective.of(new Location(
			1, -Styling.BORDER_PADDING*2-(Styling.BUTTON_WIDTH+Styling.ELEMENT_PADDING)*6,
			0, Styling.BUTTON_WIDTH)));
		
		windowDecor.addChild(tabPane);
		
		// and the New Tab button...
		CircularText newTabButton = new CircularText(null);
		//newTabButton.directive(PositionDirective.of(new Location(1, -Styling.BORDER_PADDING-Styling.BUTTON_WIDTH, 0, Styling.ELEMENT_PADDING)));
		newTabButton.directive(SizeDirective.of(new Location(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)));
		newTabButton.text("+");
		addButtonBehavior(newTabButton, ()->window.openNewTab());
		
		Component spacer = new BlockComponent(null);
		spacer.directive(SizeDirective.of(new Location(0, Styling.ELEMENT_PADDING, 0, Styling.BUTTON_WIDTH)));
		
		tabPane.addChild(newTabButton);
		tabPane.addChild(spacer);
		
		return windowDecor;
	}

	private void close() {
		windowGrip.close();
		window.close();
	}

	private void addButtonBehavior(Component button, Runnable handler) {
		addButtonBehavior(button, handler, ()->false);
	}
	private void addButtonBehavior(Component button, Runnable handler, Supplier<Boolean> activeChecker) {
		ActionButtonBehavior.configure(button, handler, Styling.BACKGROUND_SECONDARY,
			Styling.BACKGROUND_SECONDARY_HOVER, Styling.BACKGROUND_SECONDARY_SELECTED, Styling.BACKGROUND_SECONDARY_ACTIVE,
			activeChecker);
	}
	private void addDangerousButtonBehavior(Component button, Runnable handler) {
		ActionButtonBehavior.configure(button, handler, Styling.BACKGROUND_SECONDARY_DANGER,
			Styling.BACKGROUND_SECONDARY_HOVER, Styling.BACKGROUND_SECONDARY_SELECTED, Styling.BACKGROUND_SECONDARY_ACTIVE,
			()->false);
	}
	
	private void selectTab(TabGUI tab) {
		if (selected!=null) {
			selected.setSelected(false);
		}
		selected = tab;
		selected.setSelected(true);
		
		Component tabPane = tab.getTabPane();
		tabPane.directive(SizeDirective.of(new Location(1, 0, 1, 0)));
		tabPaneContainer.children(new Component[] {tabPane});
	}
	
	private TabGUI createTabGUI(Tab tab) {
		TabGUI tabGUI = new TabGUI(tab);
		tabGUI.start();
		
		tabGUI.setSelected(false);
		tabs.add(tabGUI);
		
		Component tabButton = tabGUI.getTabButton();
		tabButton.directive(SizeDirective.of(new Location(0, 150, 0, Styling.BUTTON_WIDTH)));
		addButtonBehavior(tabButton, ()->selectTab(tabGUI), ()->tabGUI.isSelected());
		
		Component spacer = new BlockComponent(null);
		spacer.directive(SizeDirective.of(new Location(0, Styling.ELEMENT_PADDING, 0, Styling.BUTTON_WIDTH)));
		
		tabPane.addChild(tabButton);
		tabPane.addChild(spacer);
		
		return tabGUI;
	}
	
	private class WindowEventListener implements WindowMutationEventListener {
		@Override
		public void onTabAdded(Window window, Tab tab) {
			TabGUI newTabGUI = createTabGUI(tab);
			selectTab(newTabGUI);
		}
	}
}
