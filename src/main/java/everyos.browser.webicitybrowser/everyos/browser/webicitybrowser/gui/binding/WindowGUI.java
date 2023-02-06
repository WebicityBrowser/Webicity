package everyos.browser.webicitybrowser.gui.binding;

import java.util.Calendar;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import everyos.browser.webicitybrowser.component.CircularButtonComponent;
import everyos.browser.webicitybrowser.component.MenuButtonComponent;
import everyos.browser.webicitybrowser.gui.Styling;
import everyos.browser.webicitybrowser.gui.behavior.ActionButtonBehavior;
import everyos.browser.webicitybrowser.gui.behavior.WindowDragBehavior;
import everyos.browser.webicitybrowser.gui.binding.component.tab.TabDisplayComponent;
import everyos.browser.webicitybrowser.gui.binding.component.tab.TabPickerComponent;
import everyos.browser.webicitybrowser.gui.binding.component.tab.TabSystemConfiguration;
import everyos.browser.webicitybrowser.gui.colors.ColorPalette;
import everyos.browser.webicitybrowser.gui.colors.NormalColors;
import everyos.browser.webicitybrowser.gui.colors.OctoberColors;
import everyos.browser.webicitybrowser.gui.colors.PrivateColors;
import everyos.browser.webicitybrowser.gui.util.ImageUtil;
import everyos.browser.webicitybrowser.gui.window.GUIWindow;
import everyos.browser.webicitybrowser.ui.Tab;
import everyos.browser.webicitybrowser.ui.Window;
import everyos.browser.webicitybrowser.ui.event.WindowMutationEventListener;
import everyos.browser.webicitybrowser.ui.imp.TabImp;
import everyos.desktop.thready.basic.component.ContainerComponent;
import everyos.desktop.thready.basic.directive.BackgroundColorDirective;
import everyos.desktop.thready.basic.directive.ChildrenDirective;
import everyos.desktop.thready.basic.directive.FontDirective;
import everyos.desktop.thready.basic.layout.flowing.directive.PositionDirective;
import everyos.desktop.thready.basic.layout.flowing.directive.SizeDirective;
import everyos.desktop.thready.core.graphics.color.Colors;
import everyos.desktop.thready.core.graphics.text.FontDecoration;
import everyos.desktop.thready.core.graphics.text.FontInfo;
import everyos.desktop.thready.core.graphics.text.FontWeight;
import everyos.desktop.thready.core.graphics.text.NamedFont;
import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.positioning.imp.RelativePositionImp;
import everyos.desktop.thready.core.positioning.imp.RelativeSizeImp;

public class WindowGUI {

	private final Window window;
	private final BiConsumer<Component, Consumer<GUIWindow>> windowCreator;
	private final ColorPalette colors;
	
	private GUIWindow nativeWindow;

	public WindowGUI(Window window, BiConsumer<Component, Consumer<GUIWindow>> windowCreator) {
		this.window = window;
		this.windowCreator = windowCreator;
		this.colors = createColors();
	}

	public void start() {
		windowCreator.accept(createRootContent(), handle -> nativeWindow = handle);
	}
	
	private void close() {
		nativeWindow.close();
		window.close();
	}

	private ColorPalette createColors() {
		return window.isPrivate() ?
			new PrivateColors() :
			Calendar.getInstance().get(Calendar.MONTH) == Calendar.OCTOBER ?
				new OctoberColors() :
				new NormalColors();
	}
	
	private Component createRootContent() {
		return createWindowBorder(createInnerContent());
	}

	private Component createInnerContent() {
		float decorHeight = Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING * 1.5f;
		
		TabPickerComponent tabPickerComponent = new TabPickerComponent(createTabPickerConfig(), true);
		
		Component windowDecorations = createWindowDecorations(tabPickerComponent)
			.directive(PositionDirective.of(new RelativePositionImp(0, 0, 0, 0)))
			.directive(SizeDirective.of(new RelativeSizeImp(1, 0, 0, decorHeight)));
		
		Component tabContentPane = tabPickerComponent.getDisplayPane()
			.directive(PositionDirective.of(new RelativePositionImp(0, 0, 0, decorHeight)))
			.directive(SizeDirective.of(new RelativeSizeImp(1, 0, 1, -decorHeight)));
		
		addWindowListener(window, tabPickerComponent);
		
		FontInfo font = new FontInfo(new NamedFont("Open Sans"), 16, FontWeight.NORMAL, new FontDecoration[0]);
		return new ContainerComponent()
			.directive(BackgroundColorDirective.of(Colors.WHITE))
			.directive(FontDirective.of(font))
			.directive(ChildrenDirective.of(windowDecorations, tabContentPane));
	}

	private Component createWindowBorder(Component innerContent) {
		innerContent
			.directive(BackgroundColorDirective.of(Colors.WHITE))
			.directive(PositionDirective.of(new RelativePositionImp(0, 1, 0, 1)))
			.directive(SizeDirective.of(new RelativeSizeImp(1, -2, 1, -2)));
		
		Component innerBorder = new ContainerComponent()
			.directive(BackgroundColorDirective.of(Colors.LIGHT_GRAY))
			.directive(PositionDirective.of(new RelativePositionImp(0, 1, 0, 1)))
			.directive(SizeDirective.of(new RelativeSizeImp(1, -2, 1, -2)))
			.directive(ChildrenDirective.of(innerContent));
		
		Component outerBorder = new ContainerComponent()
			.directive(BackgroundColorDirective.of(Colors.DARK_GRAY))
			.directive(ChildrenDirective.of(innerBorder));
		
		return outerBorder;
	}
	
	private Component createWindowDecorations(Component tabPickerComponent) {
		Component menuButton = createMenuButtonComponent()
			.directive(PositionDirective.of(new RelativePositionImp(0, 0, 0, 0)))
			.directive(SizeDirective.of(new RelativeSizeImp(
				0, Styling.BUTTON_WIDTH*3 + Styling.ELEMENT_PADDING*2 + Styling.BORDER_PADDING,
				0, Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING)));
		
		tabPickerComponent
			.directive(PositionDirective.of(new RelativePositionImp(
				0, Styling.BORDER_PADDING + Styling.BUTTON_WIDTH*3 + Styling.ELEMENT_PADDING*3,
				0, 0)))
			.directive(SizeDirective.of(new RelativeSizeImp(
				1, -Styling.BORDER_PADDING*2 - (Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING)*6,
				1, 0)));
		
		float windowActionButtonsSize = (Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING)*3 - Styling.ELEMENT_PADDING;
		Component actionButtonsContainer = createActionButtonsContainer()
			.directive(PositionDirective.of(new RelativePositionImp(
				1, -windowActionButtonsSize - Styling.BORDER_PADDING, 0, Styling.ELEMENT_PADDING)))
			.directive(SizeDirective.of(new RelativeSizeImp(
				0, windowActionButtonsSize, 0, Styling.BUTTON_WIDTH)));
		
		Component windowDecor = new ContainerComponent()
			.directive(BackgroundColorDirective.of(colors.getBackgroundPrimary()))
			.directive(ChildrenDirective.of(
				menuButton, tabPickerComponent, actionButtonsContainer
			));
		
		WindowDragBehavior.addDragBehavior(windowDecor, () -> nativeWindow);
		
		return windowDecor;
	}

	private Component createActionButtonsContainer() {
		Component[] buttons = new Component[] {
			createMinimizeButton(),
			createRestoreButton(),
			createXButton()
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
	
	//TODO: Add actions to buttons
	private Component createMinimizeButton() {
		Component button = new CircularButtonComponent(ImageUtil.loadImageFromResource("icons/minimize.png"))
			.directive(SizeDirective.of(new RelativeSizeImp(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)));
		addButtonBehavior(button, () -> nativeWindow.minimize());
		
		return button;
	}

	private Component createRestoreButton() {
		//TODO: Convert to restore button when maximized
		Component button = new CircularButtonComponent(ImageUtil.loadImageFromResource("icons/maximize.png"))
			.directive(SizeDirective.of(new RelativeSizeImp(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)));
		addButtonBehavior(button, () -> nativeWindow.restore());
		
		return button;
	}

	private Component createXButton() {
		Component button = new CircularButtonComponent(ImageUtil.loadImageFromResource("icons/stop.png"))
			.directive(SizeDirective.of(new RelativeSizeImp(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)));
		addDangerousButtonBehavior(button, () -> close());
		
		return button;
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

	private Component createMenuButtonComponent() {
		Component menuButton = new MenuButtonComponent()
			.directive(BackgroundColorDirective.of(colors.getBackgroundSecondary()));
		
		addButtonBehavior(menuButton, () -> {});
		
		return menuButton;
	}
	
	private void addWindowListener(Window window, TabPickerComponent tabs) {
		window.addWindowMutationEventListener(new WindowMutationEventListener() {
			
			@Override
			public void onTabAdded(Window window, Tab tab) {
				tabs.addTab(tab);
			}
			
		}, true);
	}
	
	private TabSystemConfiguration createTabPickerConfig() {
		return new TabSystemConfiguration() {		
			@Override
			public ColorPalette getColors() {
				return colors;
			}
			
			@Override
			public TabDisplayComponent createTabDisplay(Tab tab) {
				TabGUI gui = new TabGUI(tab, colors);
				gui.start();
				return gui.getComponent();
			}

			@Override
			public Tab createTab() {
				return new TabImp();
			}
		};
	}

}
