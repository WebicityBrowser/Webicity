package everyos.browser.webicitybrowser.gui.binding;

import com.github.anythingide.lace.basics.component.ContainerComponent;
import com.github.anythingide.lace.basics.component.directive.BackgroundDirective;
import com.github.anythingide.lace.basics.layout.auto.ChildrenDirective;
import com.github.anythingide.lace.basics.layout.auto.PositionDirective;
import com.github.anythingide.lace.basics.layout.auto.SizeDirective;
import com.github.anythingide.lace.core.component.Component;
import com.github.anythingide.lace.imputils.shape.PositionImp;
import com.github.anythingide.lace.imputils.shape.RelativeSizeImp;
import com.github.anythingide.lace.imputils.shape.SizeImp;

import everyos.browser.spec.jnet.URL;
import everyos.browser.webicitybrowser.gui.Styling;
import everyos.browser.webicitybrowser.gui.colors.Colors;
import everyos.browser.webicitybrowser.gui.component.CircularButton;
import everyos.browser.webicitybrowser.gui.component.TabButton;
import everyos.browser.webicitybrowser.gui.component.URLBar;
import everyos.browser.webicitybrowser.gui.util.ImageUtil;
import everyos.browser.webicitybrowser.ui.Tab;
import everyos.browser.webicitybrowser.ui.event.TabMutationEventListener;

public class TabGUI {

	private final Tab tab;
	
	private TabButton tabButton;
	private Component tabPane;
	private Colors colors;
	private TabMutationEventListener mutationListener;

	public TabGUI(Tab tab) {
		this.tab = tab;
		// TODO Auto-generated constructor stub
	}

	public void start(Colors colors) {
		this.colors = colors;
		this.tabButton = createTabButton();
		this.tabPane = createTabPane();
		
		configureMutationListener();
	}

	public void setSelected(boolean b) {
		
	}

	//TODO: We should have a dedicated tab strip manager, which would be in charge of this type of stuff
	public TabButton getTabButton() {
		return tabButton;
	}

	public Component getTabPane() {
		return tabPane;
	}
	
	private TabButton createTabButton() {
		TabButton tabButton = new TabButton();
		tabButton.directive(BackgroundDirective.of(colors.getBackgroundSecondary()));
		
		return tabButton;
	}
	
	private Component createTabPane() {
		Component tabDecor = createTabDecorations();
		float decorHeight = Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING*1.5f;
		tabDecor.directive(PositionDirective.of(new PositionImp(0, 0)));
		tabDecor.directive(SizeDirective.of(RelativeSizeImp.of(1, 0, 0, decorHeight)));
		
		Component tabPane = new ContainerComponent();
		tabPane.directive(ChildrenDirective.of(tabDecor));

		return tabPane;
	}

	private Component createTabDecorations() {
		Component controlButtonsContainer = createTabButtons();
		float controlButtonsSize = (Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING)*3 - Styling.ELEMENT_PADDING;
		controlButtonsContainer.directive(PositionDirective.of(new PositionImp(Styling.BORDER_PADDING, Styling.ELEMENT_PADDING/2)));
		controlButtonsContainer.directive(SizeDirective.of(new SizeImp(controlButtonsSize, Styling.BUTTON_WIDTH)));
		
		Component urlBar = createURLBar();
		float urlBarPosition = Styling.BORDER_PADDING + controlButtonsSize + Styling.ELEMENT_PADDING;
		urlBar.directive(PositionDirective.of(new PositionImp(urlBarPosition, Styling.ELEMENT_PADDING/2)));
		urlBar.directive(SizeDirective.of(RelativeSizeImp.of(1, -urlBarPosition-Styling.BORDER_PADDING, 0, Styling.BUTTON_WIDTH)));
		
		Component tabDecor = new ContainerComponent();
		tabDecor.directive(BackgroundDirective.of(colors.getBackgroundPrimary()));
		tabDecor.directive(ChildrenDirective.of(controlButtonsContainer, urlBar));
		
		return tabDecor;
	}

	private Component createTabButtons() {
		Component[] controlButtons = new Component[] {
			createBackButton(),
			createForwardButton(),
			createReloadButton()
		};
		
		for (int i = 0; i < controlButtons.length; i++) {
			Component button = controlButtons[i];
			
			button.directive(PositionDirective.of(new PositionImp((Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING)*i, 0)));
			button.directive(SizeDirective.of(new SizeImp(Styling.BUTTON_WIDTH, Styling.BUTTON_WIDTH)));
			button.directive(BackgroundDirective.of(colors.getBackgroundSecondary()));
		}
		
		Component controlButtonsContainer = new ContainerComponent();
		controlButtonsContainer.directive(ChildrenDirective.of(controlButtons));
		
		return controlButtonsContainer;
	}

	private Component createBackButton() {
		CircularButton reloadButton = new CircularButton(ImageUtil.loadImageFromResource("icons/backward.png"));
		
		return reloadButton;
	}

	private Component createForwardButton() {
		CircularButton reloadButton = new CircularButton(ImageUtil.loadImageFromResource("icons/forward.png"));
		
		return reloadButton;
	}

	private Component createReloadButton() {
		CircularButton reloadButton = new CircularButton(ImageUtil.loadImageFromResource("icons/reload.png"));
		
		return reloadButton;
	}
	
	private Component createURLBar() {
		Component urlBar = new URLBar();
		urlBar.directive(BackgroundDirective.of(colors.getBackgroundSecondary()));
		
		return urlBar;
	}
	
	private void configureMutationListener() {
		this.mutationListener = new TabEventListener();
		tab.addTabMutationListener(mutationListener);
		mutationListener.onNavigate(tab.getURL());
	}
	
	private void configureTabButton(TabButton tabButton) {
		tabButton.setTitle(tab.getName());
	}
	
	private class TabEventListener implements TabMutationEventListener {
		
		@Override
		public void onNavigate(URL url) {
			configureTabButton(tabButton);
			//urlBar.text(url.toString());
		}
		
		@Override
		public void onTitleChange(String name) {
			configureTabButton(tabButton);
		}
		
		@Override
		public void onClose() {
			//tabButton.delete();
		}
		
	}
	
}
