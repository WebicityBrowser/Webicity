package everyos.browser.webicitybrowser.gui.binding;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.function.Supplier;

import everyos.browser.webicity.net.URL;
import everyos.browser.webicitybrowser.gui.Styling;
import everyos.browser.webicitybrowser.gui.behavior.ActionButtonBehavior;
import everyos.browser.webicitybrowser.gui.colors.Colors;
import everyos.browser.webicitybrowser.gui.component.CircularText;
import everyos.browser.webicitybrowser.gui.component.TabButton;
import everyos.browser.webicitybrowser.gui.component.URLBar;
import everyos.browser.webicitybrowser.ui.Tab;
import everyos.browser.webicitybrowser.ui.event.TabMutationEventListener;
import everyos.engine.ribbon.components.component.BlockComponent;
import everyos.engine.ribbon.components.directive.BackgroundDirective;
import everyos.engine.ribbon.components.directive.PositionDirective;
import everyos.engine.ribbon.components.directive.SizeDirective;
import everyos.engine.ribbon.core.graphics.Component;
import everyos.engine.ribbon.core.shape.Location;

public class TabGUI {
	
	private final Tab tab;
	private final TabEventListener mutationListener;
	
	private TabButton tabButton;
	private Component tabPane;
	private boolean selected;
	private URLBar urlBar;
	private Colors colors;

	public TabGUI(Tab tab) {
		this.tab = tab;
		this.mutationListener = new TabEventListener();
	}
	
	public void start(Colors colors) {
		this.colors = colors;
		this.tabButton = new TabButton();
		
		createTabPane();
		
		tab.addTabMutationListener(mutationListener);
		mutationListener.onNavigate(tab.getURL());
	}

	public void cleanup() {
		tab.removeTabMutationListener(mutationListener);
	}
	
	public TabButton getTabButton() {
		return this.tabButton;
	}
	
	public Component getTabPane() {
		return this.tabPane;
	}
	
	private void configureTabButton(TabButton tabButton) {
		tabButton.text(tab.getName());
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		if (selected) {
			tabButton.directive(BackgroundDirective.of(colors.getBackgroundSecondaryActive()));
		} else {
			tabButton.directive(BackgroundDirective.of(colors.getBackgroundSecondary()));
		}
	}
	
	public boolean isSelected() {
		return this.selected;
	};
	
	private void createTabPane() {
		this.tabPane = new BlockComponent();
		
		Component tabDecor = createTabDecorations();
		int decorHeight = Styling.BUTTON_WIDTH+(int)(Styling.ELEMENT_PADDING*1.5);
		tabDecor.directive(SizeDirective.of(new Location(1, 0, 0, decorHeight)));
		tabPane.addChild(tabDecor);
		
		FrameGUI frame = new FrameGUI(tab.getFrame());
		frame.start(colors);
		
		Component frameComponent = frame.getDisplayPane();
		frameComponent.directive(SizeDirective.of(new Location(1, 0, 1, -decorHeight)));
		frameComponent.directive(PositionDirective.of(new Location(0, 0, 0, decorHeight)));
		tabPane.addChild(frameComponent);
	}

	private Component createTabDecorations() {
		Component tabDecor = new BlockComponent();
		tabDecor.directive(BackgroundDirective.of(colors.getBackgroundPrimary()));
		
		// Add the tab action buttons
		tabDecor.addChild(createBackButton());
		tabDecor.addChild(createForwardButton());
		tabDecor.addChild(createReloadButton());
		
		this.urlBar = new URLBar();
		urlBar.directive(BackgroundDirective.of(colors.getBackgroundSecondary()));
		urlBar.directive(PositionDirective.of(new Location(
			0, Styling.BORDER_PADDING+(Styling.BUTTON_WIDTH+Styling.ELEMENT_PADDING)*3,
			0, (int) (Styling.ELEMENT_PADDING*.5))));
		urlBar.directive(SizeDirective.of(new Location(
			1, -Styling.BORDER_PADDING*2-(Styling.BUTTON_WIDTH+Styling.ELEMENT_PADDING)*3,
			0, Styling.BUTTON_WIDTH)));
		urlBar.text("about:blank");
		urlBar.setAction(url->{
			try {
				URL finURL = new URL(url);
				tab.setURL(finURL);
			} catch (MalformedURLException e) {
				try {
					URL finURL = URL.ofSafe("https://www.google.com/search?q="+URLEncoder.encode(url, "UTF-8"));
					//TODO: Preferred search engine
					tab.setURL(finURL);
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
			}
		});
		addButtonBehavior(urlBar, ()->{});
		
		tabDecor.addChild(urlBar);
		
		return tabDecor;
	}

	private Component createBackButton() {
		CircularText backButton = new CircularText();
		backButton.directive(PositionDirective.of(new Location(0, Styling.BORDER_PADDING, 0, (int) (Styling.ELEMENT_PADDING*.5))));
		backButton.directive(SizeDirective.of(new Location(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)));
		backButton.text("<");
		addButtonBehavior(backButton, ()->tab.back());
		
		return backButton;
	}

	private Component createForwardButton() {
		CircularText forwardButton = new CircularText();
		forwardButton.directive(PositionDirective.of(new Location(
			0, Styling.BORDER_PADDING+Styling.BUTTON_WIDTH+Styling.ELEMENT_PADDING,
			0, (int) (Styling.ELEMENT_PADDING*.5))));
		forwardButton.directive(SizeDirective.of(new Location(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)));
		forwardButton.text(">");
		addButtonBehavior(forwardButton, ()->tab.forward());
		
		return forwardButton;
	}
	
	private Component createReloadButton() {
		CircularText reloadButton = new CircularText();
		reloadButton.directive(PositionDirective.of(new Location(
			0, Styling.BORDER_PADDING+(Styling.BUTTON_WIDTH+Styling.ELEMENT_PADDING)*2,
			0, (int) (Styling.ELEMENT_PADDING*.5))));
		reloadButton.directive(SizeDirective.of(new Location(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)));
		reloadButton.text("O");
		addButtonBehavior(reloadButton, ()->tab.reload());
		
		return reloadButton;
	}
	
	private void addButtonBehavior(Component button, Runnable handler) {
		addButtonBehavior(button, handler, ()->false);
	}
	
	private void addButtonBehavior(Component button, Runnable handler, Supplier<Boolean> activeChecker) {
		ActionButtonBehavior.configure(button, handler, colors.getBackgroundSecondary(),
			colors.getBackgroundSecondaryHover(), colors.getBackgroundSecondarySelected(), colors.getBackgroundSecondaryActive(),
			activeChecker);
	}
	
	private class TabEventListener implements TabMutationEventListener {
		
		@Override
		public void onNavigate(URL url) {
			configureTabButton(tabButton);
			urlBar.text(url.toString());
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
