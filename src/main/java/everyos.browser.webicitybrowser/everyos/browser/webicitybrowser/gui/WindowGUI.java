package everyos.browser.webicitybrowser.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import everyos.browser.webicitybrowser.gui.behavior.ActionButtonBehavior;
import everyos.browser.webicitybrowser.gui.component.CircularText;
import everyos.browser.webicitybrowser.gui.component.TabButton;
import everyos.browser.webicitybrowser.gui.component.WebicityButton;
import everyos.browser.webicitybrowser.gui.window.RibbonWindow;
import everyos.browser.webicitybrowser.gui.window.SkijaWindow;
import everyos.browser.webicitybrowser.ui.Tab;
import everyos.browser.webicitybrowser.ui.Window;
import everyos.browser.webicitybrowser.ui.event.WindowMutationEventListener;
import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.directive.BackgroundDirective;
import everyos.engine.ribbon.core.directive.ForegroundDirective;
import everyos.engine.ribbon.core.directive.PositionDirective;
import everyos.engine.ribbon.core.directive.SizeDirective;
import everyos.engine.ribbon.core.graphics.Color;
import everyos.engine.ribbon.core.shape.Location;

public class WindowGUI {
	private Window window;
	private RibbonWindow windowGrip;
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
			//TODO: Pass as constructor argument
			this.windowGrip = SkijaWindow.create();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		Component pane = windowGrip.getDisplayPane();
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

		Component innerPane = new BlockComponent();
		innerPane.directive(SizeDirective.of(new Location(1, -2, 1, -2)));
		innerPane.directive(PositionDirective.of(new Location(0, 1, 0, 1)));
		innerPane.directive(BackgroundDirective.of(Color.WHITE));
		pane.addChild(innerPane);

		Component windowDecor = createWindowDecorations();
		int decorHeight = Styling.BUTTON_WIDTH+(int)(Styling.ELEMENT_PADDING*1.5);
		windowDecor.directive(SizeDirective.of(new Location(1, 0, 0, decorHeight)));
		innerPane.addChild(windowDecor);

		this.tabPaneContainer = new BlockComponent();
		tabPaneContainer.directive(SizeDirective.of(new Location(1, 0, 1, -decorHeight)));
		tabPaneContainer.directive(PositionDirective.of(new Location(0, 0, 0, decorHeight)));
		innerPane.addChild(tabPaneContainer);
	}

	private Component createWindowDecorations() {
		BlockComponent windowDecor = new BlockComponent();
		windowDecor.directive(BackgroundDirective.of(Styling.BACKGROUND_PRIMARY));

		// Add the menu button
		WebicityButton menuButton = new WebicityButton(windowGrip.getDisplayPane());
		menuButton.directive(BackgroundDirective.of(Styling.BACKGROUND_SECONDARY));
//		menuButton.directive(PositionDirective.of(new Location(0, Styling.BORDER_PADDING, 0, Styling.ELEMENT_PADDING)));
		menuButton.directive(PositionDirective.of(new Location(0, 0, 0, 0)));
		menuButton.directive(SizeDirective.of(new Location(
				0, Styling.BUTTON_WIDTH * 3 + Styling.ELEMENT_PADDING * 2 + Styling.BORDER_PADDING,
				0, Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING)));
		addButtonBehavior(menuButton, menuButton::toggleMenu);

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
		addButtonBehavior(maximizeButton, ()->windowGrip.restore());

		windowDecor.addChild(maximizeButton);

		CircularText closeButton = new CircularText(null);
		closeButton.directive(PositionDirective.of(new Location(1, -Styling.BORDER_PADDING-Styling.BUTTON_WIDTH, 0, Styling.ELEMENT_PADDING)));
		closeButton.directive(SizeDirective.of(new Location(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)));
		closeButton.text("X");
		addDangerousButtonBehavior(closeButton, ()->close());

		windowDecor.addChild(closeButton);

		// Create the tab pane
		this.tabPane = new BlockComponent();
		tabPane.directive(PositionDirective.of(new Location(
			0, Styling.BORDER_PADDING+(Styling.BUTTON_WIDTH+Styling.ELEMENT_PADDING)*4,
			0, 0)));
		tabPane.directive(SizeDirective.of(new Location(
			1, -Styling.BORDER_PADDING*2-(Styling.BUTTON_WIDTH+Styling.ELEMENT_PADDING)*7,
			0, Styling.BUTTON_WIDTH+Styling.ELEMENT_PADDING)));

		windowDecor.addChild(tabPane);

		// and the New Tab button...
		CircularText newTabButton = new CircularText(null);
		newTabButton.directive(PositionDirective.of(new Location(
				0, Styling.BORDER_PADDING+Styling.BUTTON_WIDTH*3+Styling.ELEMENT_PADDING*3,
				0, Styling.ELEMENT_PADDING)));
		newTabButton.directive(SizeDirective.of(new Location(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)));
		newTabButton.text("+");
		addButtonBehavior(newTabButton, ()->window.openNewTab());

		Component spacer = new BlockComponent();
		spacer.directive(SizeDirective.of(new Location(0, Styling.ELEMENT_PADDING, 0, Styling.BUTTON_WIDTH)));

		windowDecor.addChild(newTabButton);
//		tabPane.addChild(spacer);

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

		TabButton tabButton = tabGUI.getTabButton();
		tabButton.directive(SizeDirective.of(new Location(0, 150, 0, Styling.BUTTON_WIDTH+Styling.ELEMENT_PADDING)));
		addButtonBehavior(tabButton, ()->selectTab(tabGUI), ()->tabGUI.isSelected());
		// TODO: How do we remove/close tabs?
		addButtonBehavior(tabButton.getCloseButton(), ()->tab.close(), ()->tabGUI.isSelected());

		Component spacer = new BlockComponent();
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
