package everyos.browser.webicitybrowser.gui.binding.component.tab;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import everyos.browser.webicitybrowser.component.CircularButtonComponent;
import everyos.browser.webicitybrowser.gui.Styling;
import everyos.browser.webicitybrowser.gui.behavior.ActionButtonBehavior;
import everyos.browser.webicitybrowser.gui.colors.ColorPalette;
import everyos.browser.webicitybrowser.gui.util.ImageUtil;
import everyos.browser.webicitybrowser.ui.Tab;
import everyos.desktop.thready.basic.component.ContainerComponent;
import everyos.desktop.thready.basic.directive.BackgroundColorDirective;
import everyos.desktop.thready.basic.directive.ChildrenDirective;
import everyos.desktop.thready.basic.layout.flowing.directive.PositionDirective;
import everyos.desktop.thready.basic.layout.flowing.directive.SizeDirective;
import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.positioning.imp.AbsolutePositionImp;
import everyos.desktop.thready.core.positioning.imp.AbsoluteSizeImp;
import everyos.desktop.thready.core.positioning.imp.RelativeSizeImp;

public class TabPickerComponent extends TabDisplayComponent {
	
	private static final float TAB_WIDTH = 150;
	private static final float DEFAULT_TAB_HEIGHT = Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING / 2;
	private static final float SELECTED_TAB_HEIGHT = Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING;

	private final TabSystemConfiguration configs;
	private final List<TabComponent> tabs = new ArrayList<>();
	private final ContainerComponent tabDisplayPane = new ContainerComponent();
	private final boolean isTopLevel;
	
	private TabComponent selectedTab;

	public TabPickerComponent(TabSystemConfiguration configs, boolean isTopLevel) {
		this.configs = configs;
		this.isTopLevel = isTopLevel;
	}

	@Override
	public String getName() {
		return selectedTab.getName();
	}
	
	public Component getDisplayPane() {
		return tabDisplayPane;
	}
	
	public void addTab(Tab tab) {
		TabComponent tabComponent = new ContentTabComponent(configs, tab);
		setupTabComponent(tabComponent);
		tabs.add(tabComponent);
		if (tabs.size() == 1) {
			selectTab(tabComponent);
		}
		
		regenerateContents();
	}

	private void selectTab(TabComponent tabComponent) {
		selectedTab = tabComponent;
		regenerateContents();
		updateTabDisplay();
	}

	private void setupTabComponent(TabComponent tabComponent) {
		addButtonBehavior(tabComponent, () -> selectTab(tabComponent), () -> selectedTab == tabComponent);
		tabComponent.setIsTopLevel(isTopLevel);
	}

	private void updateTabDisplay() {
		selectedTab.getDisplay()
			.directive(SizeDirective.of(new RelativeSizeImp(1, 0, 1, 0)));
		tabDisplayPane.directive(ChildrenDirective.of(selectedTab.getDisplay()));
	}

	private void regenerateContents() {
		Component[] contents = new Component[tabs.size() + 1];
		contents[0] = createNewTabButton();
		
		float xStart = Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING;
		for (int i = 0; i < tabs.size(); i++) {
			TabComponent tab = tabs.get(i);
			contents[i + 1] = tab;
			setTabPosition(tab, xStart);
			if (tab == selectedTab) {
				decorateSelectedTab(tab);
			} else {
				decorateUnselectedTab(tab);
			}
			xStart += TAB_WIDTH + Styling.ELEMENT_PADDING;
		}
		directive(ChildrenDirective.of(contents));
	}

	private Component createNewTabButton() {
		Component button = new CircularButtonComponent(ImageUtil.loadImageFromResource("icons/newtab.png"));
		addButtonBehavior(button, () -> addTab(configs.createTab()), () -> false);
		
		return button
			.directive(PositionDirective.of(new AbsolutePositionImp(0, Styling.ELEMENT_PADDING)))
			.directive(SizeDirective.of(new AbsoluteSizeImp(22, 22)));
	}

	private void setTabPosition(Component tab, float xStart) {
		tab.directive(PositionDirective.of(new AbsolutePositionImp(xStart, 0)));
	}
	
	private void decorateSelectedTab(Component tab) {
		tab
			.directive(SizeDirective.of(new RelativeSizeImp(0, TAB_WIDTH, 0, SELECTED_TAB_HEIGHT)))
			.directive(BackgroundColorDirective.of(configs.getColors().getBackgroundSecondaryActive()));
	}
	
	private void decorateUnselectedTab(Component tab) {
		tab
			.directive(SizeDirective.of(new RelativeSizeImp(0, TAB_WIDTH, 0, DEFAULT_TAB_HEIGHT)))
			.directive(BackgroundColorDirective.of(configs.getColors().getBackgroundSecondary()));
	}
	
	private void addButtonBehavior(Component button, Runnable handler, Supplier<Boolean> activeChecker) {
		ColorPalette colors = configs.getColors();
		ActionButtonBehavior.configure(
			button, handler,
			colors.getBackgroundSecondary(), colors.getBackgroundSecondaryHover(),
			colors.getBackgroundSecondarySelected(), colors.getBackgroundSecondaryActive(),
			activeChecker);
	}
	
}
