package everyos.browser.webicitybrowser;

import java.net.MalformedURLException;

import everyos.api.getopts.ParserFailedException;
import everyos.browser.webicitybrowser.gui.binding.WindowSetGUI;
import everyos.browser.webicitybrowser.gui.ui.WebicityLookAndFeel;
import everyos.browser.webicitybrowser.gui.window.SkijaGUIWindow;
import everyos.browser.webicitybrowser.startup.WebicityArgumentReader;
import everyos.browser.webicitybrowser.ui.Tab;
import everyos.browser.webicitybrowser.ui.Window;
import everyos.browser.webicitybrowser.ui.WindowSet;
import everyos.browser.webicitybrowser.ui.imp.TabImp;
import everyos.browser.webicitybrowser.ui.imp.WindowSetImp;
import everyos.desktop.thready.basic.directive.generator.noop.NoopStyleGeneratorRoot;
import everyos.desktop.thready.core.gui.laf.LookAndFeelBuilder;
import everyos.desktop.thready.laf.simple.SimpleLookAndFeel;
import everyos.desktop.thready.renderer.skija.SkijaWindow;
import everyos.web.spec.uri.URL;

public class Webicity {

	/**
	 * The entry point to Webicity Browser.
	 * Parses the command line arguments and creates an instance
	 * of the browser.
	 * @param args The command line arguments
	 */
	public static void main(String[] args) {
		try {
			startInstanceFromArguments(WebicityArgumentReader.parseArguments(args));
		} catch (ParserFailedException e) {
			// The parser likely already handled this exception for us already
		}
	}

	private static void startInstanceFromArguments(WebicityArguments arguments) {
		//TODO: Static stuff like this is probably better elsewhere
		System.setProperty("logging.level", arguments.getVerbose() ? "TRACE" : "INFO");
		
		WindowSet windowSet = new WindowSetImp();
		Window window = windowSet.openWindow(() -> arguments.getPrivate());
		openInitialPages(window, arguments);
		windowSet.start();
		
		startUIFor(windowSet);
	}

	private static void openInitialPages(Window window, WebicityArguments arguments) {
		if (arguments.getURLs().length == 0) {
			openDefaultURLs(window);
		} else {
			openURLsFromArguments(window, arguments);
		}
	}
	
	private static void openURLsFromArguments(Window window, WebicityArguments arguments) {
		for (URL url: arguments.getURLs()) {
			openURL(window, url);
		}
	}

	private static void openDefaultURLs(Window window) {
		try {
			//TODO: Configuration
			openURL(window, URL.createFromString("webicity://welcome"));
			openURL(window, URL.createFromString("webicity://welcome"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private static void openURL(Window window, URL url) {
		Tab tab = new TabImp();
		tab.navigate(url);
		
		window.addTab(tab);
	}

	private static void startUIFor(WindowSet windowSet) {
		new WindowSetGUI(windowSet, (rootComponent, callback) -> {
			SkijaWindow.createWindow(window -> {
				window.setTitle("Webicity");
				window.setDecorated(false);
				
				LookAndFeelBuilder lookAndFeelBuilder = LookAndFeelBuilder.create();
				SimpleLookAndFeel.installTo(lookAndFeelBuilder);
				WebicityLookAndFeel.installTo(lookAndFeelBuilder);
				
				window.getScreen().setGUI(rootComponent, lookAndFeelBuilder.build(), new NoopStyleGeneratorRoot());
				callback.accept(new SkijaGUIWindow(window));
			});
		}).start();
	}

}
