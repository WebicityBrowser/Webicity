package everyos.browser.webicitybrowser.gui.binding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Supplier;

import everyos.browser.webicitybrowser.gui.Styling;
import everyos.browser.webicitybrowser.gui.behavior.ActionButtonBehavior;
import everyos.browser.webicitybrowser.gui.colors.Colors;
import everyos.browser.webicitybrowser.gui.colors.NormalColors;
import everyos.browser.webicitybrowser.gui.colors.OctoberColors;
import everyos.browser.webicitybrowser.gui.colors.PrivateColors;
import everyos.browser.webicitybrowser.gui.component.CircularText;
import everyos.browser.webicitybrowser.gui.component.TabButton;
import everyos.browser.webicitybrowser.gui.component.WebicityButton;
import everyos.browser.webicitybrowser.gui.window.RibbonWindow;
import everyos.browser.webicitybrowser.ui.Tab;
import everyos.browser.webicitybrowser.ui.Window;
import everyos.browser.webicitybrowser.ui.event.WindowMutationEventListener;
import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.directive.BackgroundDirective;
import everyos.engine.ribbon.core.directive.ForegroundDirective;
import everyos.engine.ribbon.core.directive.PositionDirective;
import everyos.engine.ribbon.core.directive.SizeDirective;
import everyos.engine.ribbon.core.event.EventListener;
import everyos.engine.ribbon.core.graphics.paintfill.Color;
import everyos.engine.ribbon.core.input.mouse.ExternalMouseListenerDirective;
import everyos.engine.ribbon.core.input.mouse.MouseEvent;
import everyos.engine.ribbon.core.input.mouse.MouseListenerDirective;
import everyos.engine.ribbon.core.shape.Location;
import everyos.engine.ribbon.core.shape.Position;

public class WindowGUI {
	private final Window window;
	private final RibbonWindow windowGrip;
	private final WindowEventListener mutationListener;
	private final List<TabGUI> tabs;
	
	private TabGUI selected;
	private Component tabPane;
	private Component tabPaneContainer;
	private Colors colors;

	public WindowGUI(Window window, RibbonWindow windowGrip) {
		this.window = window;
		this.windowGrip = windowGrip;
		this.mutationListener = new WindowEventListener();
		this.tabs = new ArrayList<>();
	}

	public void start() {
		this.colors = window.isPrivateWindow() ?
			new PrivateColors() :
			Calendar.getInstance().get(Calendar.MONTH) == Calendar.OCTOBER ?
				new OctoberColors() :
				new NormalColors();
		
		Component pane = windowGrip.getDisplayPane();
		configureWindow(pane);

		window.addWindowMutationListener(mutationListener);

		for (Tab tab: window.getTabs()) {
			mutationListener.onTabAdded(window, tab);
		}
		if (tabs.size() > 0) {
			selectTab(tabs.get(0));
		}
		
		window.start();
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
		pane.directive(ForegroundDirective.of(colors.getForegroundPrimary()));
		
		Component innerOuterPane = new BlockComponent();
		innerOuterPane.directive(SizeDirective.of(new Location(1, -2, 1, -2)));
		innerOuterPane.directive(PositionDirective.of(new Location(0, 1, 0, 1)));
		innerOuterPane.directive(BackgroundDirective.of(Color.of(92, 92, 92)));
		pane.addChild(innerOuterPane);

		Component innerPane = new BlockComponent();
		innerPane.directive(SizeDirective.of(new Location(1, -2, 1, -2)));
		innerPane.directive(PositionDirective.of(new Location(0, 1, 0, 1)));
		innerPane.directive(BackgroundDirective.of(Color.WHITE));
		innerOuterPane.addChild(innerPane);

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
		windowDecor.directive(BackgroundDirective.of(colors.getBackgroundPrimary()));

		// Add the menu button
		//TODO: Menu is overlapping window edges
		WebicityButton menuButton = new WebicityButton(windowGrip.getDisplayPane(), window, colors);
		menuButton.directive(BackgroundDirective.of(colors.getBackgroundSecondary()));
		menuButton.directive(PositionDirective.of(new Location(0, 0, 0, 0)));
		menuButton.directive(SizeDirective.of(new Location(
			0, Styling.BUTTON_WIDTH * 3 + Styling.ELEMENT_PADDING * 2 + Styling.BORDER_PADDING,
			0, Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING)));
		addButtonBehavior(menuButton, menuButton::toggleMenu);

		windowDecor.addChild(menuButton);

		// Add the window action buttons
		CircularText minimizeButton = new CircularText();
		minimizeButton.directive(PositionDirective.of(new Location(
			1, -Styling.BORDER_PADDING-Styling.BUTTON_WIDTH*3-Styling.ELEMENT_PADDING*2,
			0, Styling.ELEMENT_PADDING)));
		minimizeButton.directive(SizeDirective.of(new Location(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)));
		minimizeButton.text("-");
		addButtonBehavior(minimizeButton, ()->windowGrip.minimize());

		windowDecor.addChild(minimizeButton);

		CircularText maximizeButton = new CircularText();
		maximizeButton.directive(PositionDirective.of(new Location(
			1, -Styling.BORDER_PADDING-Styling.BUTTON_WIDTH*2-Styling.ELEMENT_PADDING,
			0, Styling.ELEMENT_PADDING)));
		maximizeButton.directive(SizeDirective.of(new Location(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)));
		maximizeButton.text("+");
		addButtonBehavior(maximizeButton, ()->windowGrip.restore());

		windowDecor.addChild(maximizeButton);

		CircularText closeButton = new CircularText();
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
		CircularText newTabButton = new CircularText();
		newTabButton.directive(PositionDirective.of(new Location(
			0, Styling.BORDER_PADDING+Styling.BUTTON_WIDTH*3+Styling.ELEMENT_PADDING*3,
			0, Styling.ELEMENT_PADDING)));
		newTabButton.directive(SizeDirective.of(new Location(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)));
		newTabButton.text("+");
		addButtonBehavior(newTabButton, ()->window.openNewTab());

		windowDecor.addChild(newTabButton);
		
		addDragBehavior(windowDecor);
		addDragBehavior(tabPane);

		return windowDecor;
	}

	private void addDragBehavior(Component windowDecor) {
		EventListener<MouseEvent> mouseListener = new EventListener<>() {
			boolean isSelected = false;
			private Position position;
			private int startX;
			private int startY;
			
			@Override
			public void accept(MouseEvent e) {
				if (e.getAction() == MouseEvent.PRESS && e.getButton() == MouseEvent.LEFT_BUTTON && !e.isExternal()) {
					isSelected = true;
					this.position = windowGrip.getPosition();
					this.startX = e.getScreenX();
					this.startY = e.getScreenY();
				} else if (e.getAction() == MouseEvent.RELEASE && e.getButton() == MouseEvent.LEFT_BUTTON) {
					isSelected = false;
				} else if (e.getAction() == MouseEvent.DRAG && isSelected) {
					windowGrip.setPosition(
						e.getScreenX()-startX+position.getX(),
						e.getScreenY()-startY+position.getY());
				}
			}
		};
		
		windowDecor.directive(MouseListenerDirective.of(mouseListener));
		windowDecor.directive(ExternalMouseListenerDirective.of(mouseListener));
	}

	private void close() {
		windowGrip.close();
		window.close();
	}

	private void addButtonBehavior(Component button, Runnable handler) {
		addButtonBehavior(button, handler, ()->false);
	}
	
	private void addButtonBehavior(Component button, Runnable handler, Supplier<Boolean> activeChecker) {
		ActionButtonBehavior.configure(button, handler, colors.getBackgroundSecondary(),
			colors.getBackgroundSecondaryHover(), colors.getBackgroundSecondarySelected(), colors.getBackgroundSecondaryActive(),
			activeChecker);
	}
	
	private void addDangerousButtonBehavior(Component button, Runnable handler) {
		ActionButtonBehavior.configure(button, handler, colors.getBackgroundSecondaryDanger(),
			colors.getBackgroundSecondaryHover(), colors.getBackgroundSecondarySelected(), colors.getBackgroundSecondaryActive(),
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
		tabGUI.start(colors);

		tabGUI.setSelected(false);
		tabs.add(tabGUI);

		TabButton tabButton = tabGUI.getTabButton();
		tabButton.directive(SizeDirective.of(new Location(0, 150, 0, Styling.BUTTON_WIDTH+Styling.ELEMENT_PADDING)));
		addButtonBehavior(tabButton, ()->selectTab(tabGUI), ()->tabGUI.isSelected());
		addButtonBehavior(tabButton.getCloseButton(), ()->closeTab(tab, tabGUI), ()->tabGUI.isSelected());

		Component spacer = tabButton.getSpacer();
		spacer.directive(SizeDirective.of(new Location(0, Styling.ELEMENT_PADDING, 0, Styling.BUTTON_WIDTH)));

		tabPane.addChild(tabButton);
		tabPane.addChild(spacer);

		return tabGUI;
	}

	private void closeTab(Tab tab, TabGUI tabGUI) {
		tab.close();
		tabs.remove(tabGUI);
		if (selected == tabGUI) {
			if (tabs.size() == 0) {
				close();
			} else {
				selectTab(tabs.get(0));
			}
		}
	}

	private class WindowEventListener implements WindowMutationEventListener {
		@Override
		public void onTabAdded(Window window, Tab tab) {
			TabGUI newTabGUI = createTabGUI(tab);
			selectTab(newTabGUI);
		}
	}
}
