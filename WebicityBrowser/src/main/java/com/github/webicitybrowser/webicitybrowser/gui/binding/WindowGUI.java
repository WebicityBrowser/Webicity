package com.github.webicitybrowser.webicitybrowser.gui.binding;

import java.util.Calendar;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.github.webicitybrowser.thready.color.Colors;
import com.github.webicitybrowser.thready.dimensions.RelativePosition;
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
import com.github.webicitybrowser.webicitybrowser.BrowserInstance;
import com.github.webicitybrowser.webicitybrowser.component.CircularButtonComponent;
import com.github.webicitybrowser.webicitybrowser.component.MenuButtonComponent;
import com.github.webicitybrowser.webicitybrowser.gui.Styling;
import com.github.webicitybrowser.webicitybrowser.gui.behavior.ActionButtonBehavior;
import com.github.webicitybrowser.webicitybrowser.gui.behavior.WindowDragBehavior;
import com.github.webicitybrowser.webicitybrowser.gui.binding.component.tab.TabDisplayComponent;
import com.github.webicitybrowser.webicitybrowser.gui.binding.component.tab.TabPickerComponent;
import com.github.webicitybrowser.webicitybrowser.gui.binding.component.tab.TabSystemConfiguration;
import com.github.webicitybrowser.webicitybrowser.gui.colors.ColorPalette;
import com.github.webicitybrowser.webicitybrowser.gui.colors.NormalColors;
import com.github.webicitybrowser.webicitybrowser.gui.colors.OctoberColors;
import com.github.webicitybrowser.webicitybrowser.gui.colors.PrivateColors;
import com.github.webicitybrowser.webicitybrowser.gui.util.ImageUtil;
import com.github.webicitybrowser.webicitybrowser.gui.window.GUIWindow;
import com.github.webicitybrowser.webicitybrowser.ui.Tab;
import com.github.webicitybrowser.webicitybrowser.ui.Window;
import com.github.webicitybrowser.webicitybrowser.ui.event.WindowMutationEventListener;
import com.github.webicitybrowser.webicitybrowser.ui.imp.TabImp;

public class WindowGUI {

	private final Window window;
	private final BiConsumer<Component, Consumer<GUIWindow>> windowCreator;
	private final BrowserInstance browserInstance;
	private final ColorPalette colors;
	
	private GUIWindow nativeWindow;

	public WindowGUI(BrowserInstance browserInstance, Window window, BiConsumer<Component, Consumer<GUIWindow>> windowCreator) {
		this.window = window;
		this.windowCreator = windowCreator;
		this.browserInstance = browserInstance;
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
			.directive(PositionDirective.of(new RelativePosition(0, 0, 0, 0)))
			.directive(SizeDirective.of(new RelativeSize(1, 0, 0, decorHeight)));
		
		Component tabContentPane = tabPickerComponent.getDisplayPane()
			.directive(PositionDirective.of(new RelativePosition(0, 0, 0, decorHeight)))
			.directive(SizeDirective.of(new RelativeSize(1, 0, 1, -decorHeight)));
		
		addWindowListener(window, tabPickerComponent);
		
		FontSettings font = new FontSettings(
			new FontSource[] { new NamedFontSource("Open Sans") },
			16, CommonFontWeights.NORMAL, new FontDecoration[0]);
		return ContainerComponent.create()
			.directive(BackgroundColorDirective.of(Colors.WHITE))
			.directive(FontDirective.of(font))
			.directive(ChildrenDirective.of(windowDecorations, tabContentPane));
	}

	private Component createWindowBorder(Component innerContent) {
		innerContent
			.directive(BackgroundColorDirective.of(Colors.WHITE))
			.directive(PositionDirective.of(new RelativePosition(0, 1, 0, 1)))
			.directive(SizeDirective.of(new RelativeSize(1, -2, 1, -2)));
		
		Component innerBorder = ContainerComponent.create()
			.directive(BackgroundColorDirective.of(Colors.LIGHT_GRAY))
			.directive(PositionDirective.of(new RelativePosition(0, 1, 0, 1)))
			.directive(SizeDirective.of(new RelativeSize(1, -2, 1, -2)))
			.directive(ChildrenDirective.of(innerContent));
		
		Component outerBorder = ContainerComponent.create()
			.directive(BackgroundColorDirective.of(Colors.DARK_GRAY))
			.directive(ChildrenDirective.of(innerBorder));
		
		return outerBorder;
	}
	
	private Component createWindowDecorations(Component tabPickerComponent) {
		Component menuButton = createMenuButtonComponent()
			.directive(PositionDirective.of(new RelativePosition(0, 0, 0, 0)))
			.directive(SizeDirective.of(new RelativeSize(
				0, Styling.BUTTON_WIDTH*3 + Styling.ELEMENT_PADDING*2 + Styling.BORDER_PADDING,
				0, Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING)));
		
		tabPickerComponent
			.directive(PositionDirective.of(new RelativePosition(
				0, Styling.BORDER_PADDING + Styling.BUTTON_WIDTH*3 + Styling.ELEMENT_PADDING*3,
				0, 0)))
			.directive(SizeDirective.of(new RelativeSize(
				1, -Styling.BORDER_PADDING*2 - (Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING)*6,
				1, 0)));
		
		float windowActionButtonsSize = (Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING)*3 - Styling.ELEMENT_PADDING;
		Component actionButtonsContainer = createActionButtonsContainer()
			.directive(PositionDirective.of(new RelativePosition(
				1, -windowActionButtonsSize - Styling.BORDER_PADDING, 0, Styling.ELEMENT_PADDING)))
			.directive(SizeDirective.of(new RelativeSize(
				0, windowActionButtonsSize, 0, Styling.BUTTON_WIDTH)));
		
		Component windowDecor = ContainerComponent.create()
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
			button.directive(PositionDirective.of(new RelativePosition(0, pos, 0, 0)));
			pos += Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING;
		}
		
		Component windowActionButtons = ContainerComponent.create();
		windowActionButtons.directive(ChildrenDirective.of(buttons));
		
		return windowActionButtons;
	}
	
	private Component createMinimizeButton() {
		Component button = new CircularButtonComponent(ImageUtil.loadImageFromResource("icons/minimize.png"))
			.directive(SizeDirective.of(new RelativeSize(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)));
		addButtonBehavior(button, () -> nativeWindow.minimize());
		
		return button;
	}

	private Component createRestoreButton() {
		//TODO: Convert to restore button when maximized
		Component button = new CircularButtonComponent(ImageUtil.loadImageFromResource("icons/maximize.png"))
			.directive(SizeDirective.of(new RelativeSize(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)));
		addButtonBehavior(button, () -> nativeWindow.restore());
		
		return button;
	}

	private Component createXButton() {
		Component button = new CircularButtonComponent(ImageUtil.loadImageFromResource("icons/stop.png"))
			.directive(SizeDirective.of(new RelativeSize(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)));
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
				return new TabImp(browserInstance);
			}
		};
	}

}
