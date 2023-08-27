package com.github.webicitybrowser.webicitybrowser.gui.binding;

import com.github.webicitybrowser.spec.url.InvalidURLException;
import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.RelativePosition;
import com.github.webicitybrowser.thready.dimensions.RelativeSize;
import com.github.webicitybrowser.thready.drawing.core.text.FontDecoration;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;
import com.github.webicitybrowser.thready.drawing.core.text.CommonFontWeights;
import com.github.webicitybrowser.thready.drawing.core.text.source.NamedFontSource;
import com.github.webicitybrowser.thready.gui.directive.basics.ChildrenDirective;
import com.github.webicitybrowser.thready.gui.graphical.directive.BackgroundColorDirective;
import com.github.webicitybrowser.thready.gui.graphical.directive.FontDirective;
import com.github.webicitybrowser.thready.gui.graphical.directive.PositionDirective;
import com.github.webicitybrowser.thready.gui.graphical.directive.SizeDirective;
import com.github.webicitybrowser.thready.gui.tree.basics.ContainerComponent;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.webicity.core.component.FrameComponent;
import com.github.webicitybrowser.webicitybrowser.BrowserInstance;
import com.github.webicitybrowser.webicitybrowser.component.CircularButtonComponent;
import com.github.webicitybrowser.webicitybrowser.component.URLBarComponent;
import com.github.webicitybrowser.webicitybrowser.gui.Styling;
import com.github.webicitybrowser.webicitybrowser.gui.behavior.ActionButtonBehavior;
import com.github.webicitybrowser.webicitybrowser.gui.binding.component.tab.TabDisplayComponent;
import com.github.webicitybrowser.webicitybrowser.gui.colors.ColorPalette;
import com.github.webicitybrowser.webicitybrowser.gui.util.ImageUtil;
import com.github.webicitybrowser.webicitybrowser.ui.Tab;

public class TabGUI {

	private final Tab tab;
	private final TabDisplayComponent tabPane;
	private final FrameComponent frameComponent;
	private final ColorPalette colors;

	public TabGUI(BrowserInstance browserInstance, Tab tab, ColorPalette colors) {
		this.tab = tab;
		this.colors = colors;
		this.frameComponent = createFrameComponent();
		this.tabPane = createTabPane();
		
		decorateTabPane();
	}

	public void start() {
		
	}
	
	public TabDisplayComponent getComponent() {
		return this.tabPane;
	}

	private FrameComponent createFrameComponent() {
		float decorHeight = Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING * 1.5f;
		
		FrameComponent frameComponent = new FrameComponent(tab.getFrame());
		frameComponent.directive(SizeDirective.of(new RelativeSize(1, 0, 1, -decorHeight)));
		frameComponent.directive(PositionDirective.of(new RelativePosition(0, 0, 0, decorHeight)));
		
		return frameComponent;
	}
	
	private TabDisplayComponent createTabPane() {
		TabDisplayComponent tabDisplayComponent = new TabDisplayComponent() {
			@Override
			public String getName() {
				return tab.getName();
			}
		};
		
		return tabDisplayComponent;
	}
	
	private void decorateTabPane() {
		float decorHeight = Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING * 1.5f;
		Component tabControls = createTabControls()
			.directive(PositionDirective.of(new AbsolutePosition(0, 0)))
			.directive(SizeDirective.of(new RelativeSize(1, 0, 0, decorHeight)));
		
		tabPane
			.directive(ChildrenDirective.of(tabControls, frameComponent));
	}

	private Component createTabControls() {
		float controlButtonsSize = (Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING) * 3;
		float urlBarPosition = Styling.BORDER_PADDING + controlButtonsSize;
		
		Component navigationButtons = createNavigationButtonsContainer()
			.directive(PositionDirective.of(new AbsolutePosition(Styling.BORDER_PADDING / 2, Styling.ELEMENT_PADDING / 2)));
		
		Component urlBar = createURLBar()
			.directive(PositionDirective.of(new AbsolutePosition(urlBarPosition, Styling.ELEMENT_PADDING / 2)))
			.directive(SizeDirective.of(new RelativeSize(1, -urlBarPosition - Styling.BORDER_PADDING, 0, Styling.BUTTON_WIDTH)));
		
		return ContainerComponent.create()
			.directive(BackgroundColorDirective.of(colors.getBackgroundPrimary()))
			.directive(ChildrenDirective.of(navigationButtons, urlBar));
	}

	private Component createURLBar() {
		URLBarComponent component = new URLBarComponent();
		component
			.setValue(tab.getURL().toString())
			.directive(BackgroundColorDirective.of(colors.getBackgroundSecondary()))
			.directive(FontDirective.of(new FontSettings(new NamedFontSource("Open Sans"), 12, CommonFontWeights.NORMAL, new FontDecoration[0])));
		
		component.setAction(url -> {
			try {
				tab.navigate(URL.of(url));
			} catch (InvalidURLException e) {
				e.printStackTrace();
			}
		});
		
		return component;
	}

	private Component createNavigationButtonsContainer() {
		Component[] buttons = new Component[] {
			createBackButton(),
			createReloadButton(),
			createForwardButton()
		};
		
		float pos = 0;
		for (int i = 0; i < buttons.length; i++) {
			Component button = buttons[i];
			button.directive(PositionDirective.of(new RelativePosition(0, pos, 0, 0)));
			pos += Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING;
		}
		
		Component windowActionButtons = ContainerComponent.create();
		windowActionButtons.directive(ChildrenDirective.of(buttons));
		
		return windowActionButtons;
	}
	
	private Component createBackButton() {
		return createButton("icons/backward.png", () -> tab.back());
	}

	private Component createForwardButton() {
		return createButton("icons/forward.png", () -> tab.forward());
	}

	private Component createReloadButton() {
		return createButton("icons/reload.png", () -> tab.reload());
	}
	
	private Component createButton(String imagePath, Runnable handler) {
		Component button = new CircularButtonComponent(ImageUtil.loadImageFromResource(imagePath));
		addButtonBehavior(button, handler);
		
		return button;
	}
	
	private void addButtonBehavior(Component button, Runnable handler) {
		ActionButtonBehavior.configure(
			button, handler,
			colors.getBackgroundPrimary(), colors.getBackgroundSecondaryHover(),
			colors.getBackgroundSecondarySelected(), colors.getBackgroundSecondaryActive(),
			() -> false);
	}
	
}
