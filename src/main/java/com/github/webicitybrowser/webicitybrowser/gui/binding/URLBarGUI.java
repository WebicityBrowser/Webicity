package com.github.webicitybrowser.webicitybrowser.gui.binding;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.github.webicitybrowser.spec.url.InvalidURLException;
import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.thready.drawing.core.text.CommonFontWeights;
import com.github.webicitybrowser.thready.drawing.core.text.FontDecoration;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;
import com.github.webicitybrowser.thready.drawing.core.text.source.FontSource;
import com.github.webicitybrowser.thready.drawing.core.text.source.NamedFontSource;
import com.github.webicitybrowser.thready.gui.graphical.directive.BackgroundColorDirective;
import com.github.webicitybrowser.thready.gui.graphical.directive.FontDirective;
import com.github.webicitybrowser.webicitybrowser.component.URLBarComponent;
import com.github.webicitybrowser.webicitybrowser.gui.colors.ColorPalette;
import com.github.webicitybrowser.webicitybrowser.ui.Tab;
import com.github.webicitybrowser.webicitybrowser.ui.event.TabMutationEventListener;

public class URLBarGUI {
	
	private final Tab tab;
	private final ColorPalette colors;
	private final URLBarComponent urlBarComponent;

	public URLBarGUI(Tab tab, ColorPalette colors) {
		this.tab = tab;
		this.colors = colors;

		this.urlBarComponent = setupURLBarComponent();
	}

	public URLBarComponent getComponent() {
		return urlBarComponent;
	}

	private URLBarComponent setupURLBarComponent() {
		URLBarComponent component = new URLBarComponent(
			() -> tab.getURL().toString(),
			url -> onURLEntry(url));

		component
			.directive(BackgroundColorDirective.of(colors.getBackgroundSecondary()))
			.directive(FontDirective.of(new FontSettings(
				new FontSource[] { new NamedFontSource("Open Sans") },
				12, CommonFontWeights.NORMAL, new FontDecoration[0])));

		addTabChangeHandler(component);
		
		return component;
	}

	private void onURLEntry(String url) {
		try {
			tab.navigate(URL.of(url));
		} catch (InvalidURLException e) {
			search(url);
		}
	}

	private void search(String url) {
		url = URLEncoder.encode(url, StandardCharsets.UTF_8);
		tab.navigate(URL.ofSafe("https://google.com/search?gbv=1&q=" + url));
	}

	private void addTabChangeHandler(URLBarComponent component) {
		tab.addTabMutationEventListener(new TabMutationEventListener() {
			@Override
			public void onNavigate(Tab tab, URL url) {
				component.notifyURLChangeListeners();
			}
		}, false);
	}
	
}
