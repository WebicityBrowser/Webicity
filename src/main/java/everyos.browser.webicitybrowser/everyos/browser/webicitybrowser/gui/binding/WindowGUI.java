package everyos.browser.webicitybrowser.gui.binding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Supplier;

import com.github.webicity.lace.basics.color.RGBA16ColorImp;
import com.github.webicity.lace.basics.component.ContainerComponent;
import com.github.webicity.lace.basics.component.directive.BackgroundDirective;
import com.github.webicity.lace.basics.layout.auto.ChildrenDirective;
import com.github.webicity.lace.basics.layout.auto.PositionDirective;
import com.github.webicity.lace.basics.layout.auto.SizeDirective;
import com.github.webicity.lace.core.component.Component;
import com.github.webicity.lace.core.laf.LookAndFeel;
import com.github.webicity.lace.imputils.shape.PositionImp;
import com.github.webicity.lace.imputils.shape.RelativePositionImp;
import com.github.webicity.lace.imputils.shape.RelativeSizeImp;
import com.github.webicity.lace.imputils.shape.SizeImp;

import everyos.browser.webicity.lacewebextensions.webuilaf.WebUILookAndFeel;
import everyos.browser.webicity.lacewebextensions.webuilaf.WebUIPalette;
import everyos.browser.webicitybrowser.gui.Styling;
import everyos.browser.webicitybrowser.gui.WebicityLookAndFeel;
import everyos.browser.webicitybrowser.gui.behavior.ActionButtonBehavior;
import everyos.browser.webicitybrowser.gui.colors.Colors;
import everyos.browser.webicitybrowser.gui.colors.NormalColors;
import everyos.browser.webicitybrowser.gui.colors.OctoberColors;
import everyos.browser.webicitybrowser.gui.colors.PrivateColors;
import everyos.browser.webicitybrowser.gui.component.CircularButton;
import everyos.browser.webicitybrowser.gui.component.TabButton;
import everyos.browser.webicitybrowser.gui.component.WebicityMenuButton;
import everyos.browser.webicitybrowser.gui.util.ImageUtil;
import everyos.browser.webicitybrowser.gui.window.GUIWindow;
import everyos.browser.webicitybrowser.ui.Tab;
import everyos.browser.webicitybrowser.ui.Window;
import everyos.browser.webicitybrowser.ui.event.WindowMutationEventListener;

public class WindowGUI {

	private final Window window;
	private final GUIWindow windowHandle;
	private final WindowEventListener mutationListener;
	private final List<TabGUI> tabs;
	
	private TabGUI selected;
	private Component tabStrip;
	private Component tabContentPane;
	private Colors colors;
	
	public WindowGUI(Window window, GUIWindow windowHandle) {
		this.window = window;
		this.windowHandle = windowHandle;
		this.mutationListener = new WindowEventListener();
		this.tabs = new ArrayList<>();
	}

	public void start() {
		windowHandle.addCloseListener(() -> close());
		
		createColors();
		
		Component innerContent = createInnerContent();
		Component windowBorder = createWindowBorder(innerContent);
		
		windowHandle.setRootComponent(windowBorder);
		LookAndFeel lookAndFeel = WebicityLookAndFeel.createLookAndFeel();
		WebUILookAndFeel.extendLookAndFeel(lookAndFeel, createWebUIPalette());
		windowHandle.setLookAndFeel(lookAndFeel);
		
		setupInitialTabs();
		
		window.start();
	}

	private void createColors() {
		// TODO: Use Lace palette
		this.colors = window.isPrivateWindow() ?
			new PrivateColors() :
			Calendar.getInstance().get(Calendar.MONTH) == Calendar.OCTOBER ?
				new OctoberColors() :
				new NormalColors();
	}
	
	private WebUIPalette createWebUIPalette() {
		return new WebUIPalette() {
			
		};
	}

	private Component createInnerContent() {
		Component windowDecor = createWindowDecor();
		float decorHeight = Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING * 1.5f;
		windowDecor.directive(PositionDirective.of(new PositionImp(0, 0)));
		windowDecor.directive(SizeDirective.of(RelativeSizeImp.of(1, 0, 0, decorHeight)));
		
		this.tabContentPane = new ContainerComponent();
		tabContentPane.directive(SizeDirective.of(RelativeSizeImp.of(1, 0, 1, -decorHeight)));
		tabContentPane.directive(PositionDirective.of(new PositionImp(0, decorHeight)));
		
		Component innerContent = new ContainerComponent();
		innerContent.directive(ChildrenDirective.of(windowDecor, tabContentPane));
		
		return innerContent;
	}

	private Component createWindowDecor() {
		Component menuButton = createMenuButtonComponent();
		menuButton.directive(PositionDirective.of(new PositionImp(0, 0)));
		menuButton.directive(SizeDirective.of(RelativeSizeImp.of(
			0, Styling.BUTTON_WIDTH*3 + Styling.ELEMENT_PADDING*2 + Styling.BORDER_PADDING,
			0, Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING)));
		
		Component windowActionButtons = createWindowActionButtonsComponent();
		float windowActionButtonsSize = (Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING)*3 - Styling.ELEMENT_PADDING;
		windowActionButtons.directive(PositionDirective.of(RelativePositionImp.of(
			1, -windowActionButtonsSize - Styling.BORDER_PADDING, 0, Styling.ELEMENT_PADDING)));
		windowActionButtons.directive(SizeDirective.of(new SizeImp(windowActionButtonsSize, Styling.BUTTON_WIDTH)));
		
		Component newTabButton = createNewTabButton();
		newTabButton.directive(PositionDirective.of(new PositionImp(
			Styling.BORDER_PADDING + Styling.BUTTON_WIDTH*3 + Styling.ELEMENT_PADDING*3,
			Styling.ELEMENT_PADDING)));
		newTabButton.directive(SizeDirective.of(new SizeImp(Styling.BUTTON_WIDTH, Styling.BUTTON_WIDTH)));
		
		createTabPane();
		tabStrip.directive(PositionDirective.of(new PositionImp(
			Styling.BORDER_PADDING + (Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING)*4, 0)));
		tabStrip.directive(SizeDirective.of(RelativeSizeImp.of(
			1, -Styling.BORDER_PADDING*2 - (Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING)*7,
			0, Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING)));
		
		Component windowDecor = new ContainerComponent();
		windowDecor.directive(BackgroundDirective.of(colors.getBackgroundPrimary()));
		windowDecor.directive(ChildrenDirective.of(menuButton, windowActionButtons, newTabButton, tabStrip));
		
		return windowDecor;
	}

	private Component createMenuButtonComponent() {
		Component menuButton = new WebicityMenuButton(Styling.PRODUCT_NAME);
		menuButton.directive(BackgroundDirective.of(colors.getBackgroundSecondary()));
		
		//TODO: Mouse
		
		return menuButton;
	}
	
	private Component createWindowActionButtonsComponent() {
		Component[] buttons = new Component[] {
			createMinimizeButton(),
			createRestoreButton(),
			createXButton()
		};
		
		float pos = 0;
		for (int i = 0; i < buttons.length; i++) {
			Component button = buttons[i];
			button.directive(PositionDirective.of(new PositionImp(pos, 0)));
			pos += Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING;
		}
		
		Component windowActionButtons = new ContainerComponent();
		windowActionButtons.directive(ChildrenDirective.of(buttons));
		
		return windowActionButtons;
	}

	//TODO: Add actions to buttons
	private Component createMinimizeButton() {
		Component button = new CircularButton(ImageUtil.loadImageFromResource("icons/minimize.png"));
		button.directive(SizeDirective.of(new SizeImp(Styling.BUTTON_WIDTH, Styling.BUTTON_WIDTH)));
		addButtonBehavior(button, () -> windowHandle.minimize());
		
		return button;
	}

	private Component createRestoreButton() {
		//TODO: Convert to restore button when maximized
		Component button = new CircularButton(ImageUtil.loadImageFromResource("icons/maximize.png"));
		button.directive(SizeDirective.of(new SizeImp(Styling.BUTTON_WIDTH, Styling.BUTTON_WIDTH)));
		addButtonBehavior(button, () -> windowHandle.restore());
		
		return button;
	}

	private Component createXButton() {
		Component button = new CircularButton(ImageUtil.loadImageFromResource("icons/stop.png"));
		button.directive(SizeDirective.of(new SizeImp(Styling.BUTTON_WIDTH, Styling.BUTTON_WIDTH)));
		addDangerousButtonBehavior(button, () -> close());
		
		return button;
	}
	
	private Component createNewTabButton() {
		Component newTabButton = new CircularButton(ImageUtil.loadImageFromResource("icons/newtab.png"));
		newTabButton.directive(BackgroundDirective.of(colors.getBackgroundSecondary()));
		
		return newTabButton;
	}
	
	private void createTabPane() {
		//TODO: Dedicated component?
		tabStrip = new ContainerComponent();
	}

	private Component createWindowBorder(Component innerContent) {
		innerContent.directive(BackgroundDirective.of(RGBA16ColorImp.WHITE));
		innerContent.directive(PositionDirective.of(new PositionImp(1, 1)));
		innerContent.directive(SizeDirective.of(RelativeSizeImp.of(1, -2, 1, -2)));
		
		Component innerBorder = new ContainerComponent();
		innerBorder.directive(BackgroundDirective.of(RGBA16ColorImp.LIGHT_GRAY));
		innerBorder.directive(PositionDirective.of(new PositionImp(1, 1)));
		innerBorder.directive(SizeDirective.of(RelativeSizeImp.of(1, -2, 1, -2)));
		innerBorder.directive(ChildrenDirective.of(innerContent));
		
		Component outerBorder = new ContainerComponent();
		outerBorder.directive(BackgroundDirective.of(RGBA16ColorImp.DARK_GRAY));
		outerBorder.directive(ChildrenDirective.of(innerBorder));
		
		return outerBorder;
	}
	
	private void setupInitialTabs() {
		window.addWindowMutationListener(mutationListener);

		for (Tab tab: window.getTabs()) {
			mutationListener.onTabAdded(window, tab);
		}
		if (tabs.size() > 0) {
			selectTab(tabs.get(0));
		}
	}
	
	private void selectTab(TabGUI tab) {
		if (selected != null) {
			selected.setSelected(false);
		}
		selected = tab;
		selected.setSelected(true);

		Component tabPane = tab.getTabPane();
		tabPane.directive(PositionDirective.of(new PositionImp(0, 0)));
		tabPane.directive(SizeDirective.of(RelativeSizeImp.of(1, 0, 1, 0)));
		tabContentPane.directive(ChildrenDirective.of(tabPane));
	}
	
	private TabGUI createTabGUI(Tab tab) {
		TabGUI tabGUI = new TabGUI(tab);
		tabGUI.start(colors);

		tabGUI.setSelected(false);
		tabs.add(tabGUI);

		TabButton tabButton = tabGUI.getTabButton();
		tabButton.directive(SizeDirective.of(new SizeImp(150 + Styling.ELEMENT_PADDING, Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING)));
		addButtonBehavior(tabButton, () -> selectTab(tabGUI), () -> tabGUI.isSelected());
		//addButtonBehavior(tabButton.getCloseButton(), ()->closeTab(tab, tabGUI), ()->tabGUI.isSelected());

		// Preferably, find a more efficient way to add children to a layout manager
		tabStrip.directive(ChildrenDirective.of(getTabButtons()));

		return tabGUI;
	}

	private Component[] getTabButtons() {
		return (Component[]) tabs.stream()
			.map(gui -> gui.getTabButton())
			.toList()
			.toArray(new Component[tabs.size()]);
	}
	
	private void addButtonBehavior(Component button, Runnable handler) {
		addButtonBehavior(button, handler, () -> false);
	}
	
	private void addButtonBehavior(Component button, Runnable handler, Supplier<Boolean> activeChecker) {
		ActionButtonBehavior.configure(button, handler, colors.getBackgroundSecondary(),
			colors.getBackgroundSecondaryHover(), colors.getBackgroundSecondarySelected(), colors.getBackgroundSecondaryActive(),
			activeChecker);
	}
	
	private void addDangerousButtonBehavior(Component button, Runnable handler) {
		ActionButtonBehavior.configure(button, handler, colors.getBackgroundSecondaryDanger(),
			colors.getBackgroundSecondaryHover(), colors.getBackgroundSecondarySelected(), colors.getBackgroundSecondaryActive(),
			() -> false);
	}

	public void cleanup() {
		
	}
	
	private void close() {
		window.close();
		windowHandle.close();
	}
	
	private class WindowEventListener implements WindowMutationEventListener {
		@Override
		public void onTabAdded(Window window, Tab tab) {
			TabGUI newTabGUI = createTabGUI(tab);
			selectTab(newTabGUI);
		}
	}
	
}
