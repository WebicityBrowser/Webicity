package com.github.webicitybrowser.webicitybrowser.gui.binding.component.tab;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeSize;
import com.github.webicitybrowser.thready.drawing.core.text.CommonFontWeights;
import com.github.webicitybrowser.thready.drawing.core.text.FontDecoration;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;
import com.github.webicitybrowser.thready.drawing.core.text.source.FontSource;
import com.github.webicitybrowser.thready.drawing.core.text.source.NamedFontSource;
import com.github.webicitybrowser.thready.gui.directive.basics.ChildrenDirective;
import com.github.webicitybrowser.thready.gui.graphical.directive.BackgroundColorDirective;
import com.github.webicitybrowser.thready.gui.graphical.directive.FontDirective;
import com.github.webicitybrowser.thready.gui.graphical.directive.PositionDirective;
import com.github.webicitybrowser.thready.gui.graphical.directive.SizeDirective;
import com.github.webicitybrowser.thready.gui.tree.basics.ContainerComponent;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.webicitybrowser.component.CircularButtonComponent;
import com.github.webicitybrowser.webicitybrowser.gui.Styling;
import com.github.webicitybrowser.webicitybrowser.gui.behavior.ActionButtonBehavior;
import com.github.webicitybrowser.webicitybrowser.gui.colors.ColorPalette;
import com.github.webicitybrowser.webicitybrowser.gui.util.ImageUtil;
import com.github.webicitybrowser.webicitybrowser.ui.Tab;

public class TabPickerComponent extends TabDisplayComponent {
	
	private static final float TAB_WIDTH = 150;
	private static final float DEFAULT_TAB_HEIGHT = Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING / 2;
	private static final float SELECTED_TAB_HEIGHT = Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING;

	private final TabSystemConfiguration configs;
	private final List<TabComponent> tabs = new ArrayList<>();
	private final ContainerComponent tabDisplayPane = ContainerComponent.create();
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
	
	public TabComponent addTab(Tab tab) {
		TabComponent tabComponent = new ContentTabComponent(configs, tab);
		setupTabComponent(tabComponent);
		tabs.add(tabComponent);
		if (tabs.size() == 1) {
			selectTab(tabComponent);
		}
		
		regenerateContents();

		return tabComponent;
	}

	private void selectTab(TabComponent tabComponent) {
		selectedTab = tabComponent;
		regenerateContents();
		updateTabDisplay();
	}

	private void setupTabComponent(TabComponent tabComponent) {
		addButtonBehavior(tabComponent, () -> selectTab(tabComponent), () -> selectedTab == tabComponent);
		tabComponent.setIsTopLevel(isTopLevel);

		tabComponent.directive(FontDirective.of(new FontSettings(
			new FontSource[] { new NamedFontSource("Open Sans") },
			12, CommonFontWeights.NORMAL, new FontDecoration[0]
		)));
	}

	private void updateTabDisplay() {
		selectedTab.getDisplay()
			.directive(SizeDirective.of(new RelativeSize(1, 0, 1, 0)));
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
		addButtonBehavior(button, () -> {
			TabComponent newTab = addTab(configs.createTab());
			selectTab(newTab);
		}, () -> false);
		
		return button
			.directive(PositionDirective.of(new AbsolutePosition(0, Styling.ELEMENT_PADDING)))
			.directive(SizeDirective.of(new AbsoluteSize(22, 22)));
	}

	private void setTabPosition(Component tab, float xStart) {
		tab.directive(PositionDirective.of(new AbsolutePosition(xStart, 0)));
	}
	
	private void decorateSelectedTab(Component tab) {
		tab
			.directive(SizeDirective.of(new RelativeSize(0, TAB_WIDTH, 0, SELECTED_TAB_HEIGHT)))
			.directive(BackgroundColorDirective.of(configs.getColors().getBackgroundSecondaryActive()));
	}
	
	private void decorateUnselectedTab(Component tab) {
		tab
			.directive(SizeDirective.of(new RelativeSize(0, TAB_WIDTH, 0, DEFAULT_TAB_HEIGHT)))
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
