package everyos.browser.webicitybrowser.gui.binding;

import everyos.browser.webicitybrowser.component.CircularButtonComponent;
import everyos.browser.webicitybrowser.gui.Styling;
import everyos.browser.webicitybrowser.gui.behavior.ActionButtonBehavior;
import everyos.browser.webicitybrowser.gui.behavior.URLBarComponent;
import everyos.browser.webicitybrowser.gui.binding.component.tab.TabDisplayComponent;
import everyos.browser.webicitybrowser.gui.colors.ColorPalette;
import everyos.browser.webicitybrowser.gui.util.ImageUtil;
import everyos.browser.webicitybrowser.ui.Tab;
import everyos.desktop.thready.basic.component.ContainerComponent;
import everyos.desktop.thready.basic.directive.BackgroundColorDirective;
import everyos.desktop.thready.basic.directive.ChildrenDirective;
import everyos.desktop.thready.basic.directive.FontDirective;
import everyos.desktop.thready.basic.layout.flowing.directive.PositionDirective;
import everyos.desktop.thready.basic.layout.flowing.directive.SizeDirective;
import everyos.desktop.thready.core.graphics.text.FontDecoration;
import everyos.desktop.thready.core.graphics.text.FontInfo;
import everyos.desktop.thready.core.graphics.text.FontWeight;
import everyos.desktop.thready.core.graphics.text.NamedFont;
import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.positioning.imp.AbsolutePositionImp;
import everyos.desktop.thready.core.positioning.imp.RelativePositionImp;
import everyos.desktop.thready.core.positioning.imp.RelativeSizeImp;

public class TabGUI {

	private final Tab tab;
	private final TabDisplayComponent tabPane;
	private final ColorPalette colors;

	public TabGUI(Tab tab, ColorPalette colors) {
		this.tab = tab;
		this.colors = colors;
		this.tabPane = createTabPane();
		
		decorateTabPane();
	}
	
	public void start() {
		
	}
	
	public TabDisplayComponent getComponent() {
		return this.tabPane;
	}

	private TabDisplayComponent createTabPane() {
		return new TabDisplayComponent() {
			@Override
			public String getName() {
				return tab.getName();
			}
		};
	}
	
	private void decorateTabPane() {
		float decorHeight = Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING * 1.5f;
		Component tabControls = createTabControls()
			.directive(PositionDirective.of(new AbsolutePositionImp(0, 0)))
			.directive(SizeDirective.of(new RelativeSizeImp(1, 0, 0, decorHeight)));
		
		tabPane
			.directive(ChildrenDirective.of(tabControls));
	}

	private Component createTabControls() {
		float controlButtonsSize = (Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING) * 3;
		float urlBarPosition = Styling.BORDER_PADDING + controlButtonsSize;
		
		Component navigationButtons = createNavigationButtonsContainer()
			.directive(PositionDirective.of(new AbsolutePositionImp(Styling.BORDER_PADDING, Styling.ELEMENT_PADDING / 2)));
		
		Component urlBar = createURLBar()
			.directive(PositionDirective.of(new AbsolutePositionImp(urlBarPosition, Styling.ELEMENT_PADDING / 2)))
			.directive(SizeDirective.of(new RelativeSizeImp(1, -urlBarPosition - Styling.BORDER_PADDING, 0, Styling.BUTTON_WIDTH)));
		
		return new ContainerComponent()
			.directive(BackgroundColorDirective.of(colors.getBackgroundPrimary()))
			.directive(ChildrenDirective.of(navigationButtons, urlBar));
	}

	private Component createURLBar() {
		return new URLBarComponent()
			.setValue(tab.getURL().toString())
			.directive(BackgroundColorDirective.of(colors.getBackgroundSecondary()))
			.directive(FontDirective.of(new FontInfo(new NamedFont("Open Sans"), 12, FontWeight.NORMAL, new FontDecoration[0])));
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
			button.directive(PositionDirective.of(new RelativePositionImp(0, pos, 0, 0)));
			pos += Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING;
		}
		
		Component windowActionButtons = new ContainerComponent();
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
			colors.getBackgroundSecondary(), colors.getBackgroundSecondaryHover(),
			colors.getBackgroundSecondarySelected(), colors.getBackgroundSecondaryActive(),
			() -> false);
	}
	
}
