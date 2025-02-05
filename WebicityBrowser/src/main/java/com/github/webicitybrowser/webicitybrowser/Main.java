package com.github.webicitybrowser.webicitybrowser;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.url.InvalidURLException;
import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.thready.drawing.core.image.EncodedBytesImageSource;
import com.github.webicitybrowser.thready.drawing.core.image.ImageSource;
import com.github.webicitybrowser.thready.drawing.core.text.CommonFontWeights;
import com.github.webicitybrowser.thready.drawing.core.text.FontDecoration;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;
import com.github.webicitybrowser.thready.drawing.core.text.source.FontSource;
import com.github.webicitybrowser.thready.drawing.core.text.source.NamedFontSource;
import com.github.webicitybrowser.thready.gui.directive.basics.style.OneOneStyleGeneratorRoot;
import com.github.webicitybrowser.thready.gui.graphical.base.GUIContent;
import com.github.webicitybrowser.thready.gui.graphical.base.GUIContent.GUIContentConfiguration;
import com.github.webicitybrowser.thready.gui.graphical.base.imp.GUIContentImp;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeelBuilder;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.SimpleLookAndFeel;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.thready.windowing.core.DesktopWindow;
import com.github.webicitybrowser.thready.windowing.core.GraphicsSystem;
import com.github.webicitybrowser.thready.windowing.skija.SkijaGraphicsSystem;
import com.github.webicitybrowser.webicity.core.RenderingEngine;
import com.github.webicitybrowser.webicitybrowser.engine.RenderingEngineCreator;
import com.github.webicitybrowser.webicitybrowser.gui.binding.WindowSetGUI;
import com.github.webicitybrowser.webicitybrowser.gui.ui.WebicityLookAndFeel;
import com.github.webicitybrowser.webicitybrowser.gui.window.DesktopGUIWindow;
import com.github.webicitybrowser.webicitybrowser.gui.window.GUIWindow;
import com.github.webicitybrowser.webicitybrowser.imp.BrowserInstanceImp;
import com.github.webicitybrowser.webicitybrowser.startup.WebicityArgumentReader;
import com.github.webicitybrowser.webicitybrowser.ui.Window;
import com.github.webicitybrowser.webicitybrowser.ui.WindowSet;
import com.github.webicitybrowser.webicitybrowser.ui.imp.WindowSetImp;

import everyos.api.getopts.ParserFailedException;

public class Main {

	/**
	 * The entry point to Webicity Browser.
	 * Parses the command line arguments and creates an instance
	 * of the browser.
	 * @param args The command line arguments
	 */
	public static void main(String[] args) {
		//try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); }
		
		try {
			startInstanceFromArguments(WebicityArgumentReader.parseArguments(args));
		} catch (ParserFailedException e) {
			// The parser likely already handled this exception for us already
		}
	}

	private static void startInstanceFromArguments(WebicityArguments arguments) {
		configureLogging(arguments);
		
		RenderingEngine renderingEngine = createRenderingEngine();
		BrowserInstance browserInstance = new BrowserInstanceImp(renderingEngine);
		
		WindowSet windowSet = new WindowSetImp(browserInstance);
		Window window = windowSet.openWindow(() -> arguments.getPrivate());
		openInitialPages(window, arguments);
		windowSet.start();
		
		startGUIFor(browserInstance, windowSet);
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
			openURL(window, URL.of("webicity://welcome"));
		} catch (InvalidURLException e) {
			e.printStackTrace();
		}
	}
	
	private static void openURL(Window window, URL url) {
		window.openTab().navigate(url);
	}

	private static void configureLogging(WebicityArguments arguments) {
		System.setProperty("logging.level", arguments.getVerbose() ? "TRACE" : "INFO");
	}

	private static RenderingEngine createRenderingEngine() {
		return RenderingEngineCreator.create();
	}

	private static void startGUIFor(BrowserInstance browserInstance, WindowSet windowSet) {
		GraphicsSystem graphicsSystem = SkijaGraphicsSystem.createDefault(() -> browserInstance.tick());
		new WindowSetGUI(browserInstance, windowSet,
			(component, callback) -> createWindowFor(graphicsSystem, component, callback)).start();
		graphicsSystem.startRenderLoop();
	}
	
	private static void createWindowFor(GraphicsSystem graphicsSystem, Component component, Consumer<GUIWindow> callback) {
		GUIContent content = createContent(component);
		
		graphicsSystem.createWindow(window -> {
			DesktopWindow desktopWindow = (DesktopWindow) window;
			
			window
				.getScreen()
				.setScreenContent(content);
			
			desktopWindow.setTitle("Webicity");
			desktopWindow.setDecorated(false);

			try {
				desktopWindow.setIcons(new ImageSource[] {
					new EncodedBytesImageSource(ClassLoader.getSystemClassLoader().getResourceAsStream("webicity.png").readAllBytes())
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			callback.accept(new DesktopGUIWindow((DesktopWindow) window));
		});
	}

	private static GUIContent createContent(Component component) {
		LookAndFeel lookAndFeel = createLookAndFeel();
		FontSettings fontSettings = new FontSettings(
			new FontSource[] { new NamedFontSource("Open Sans") },
			16, CommonFontWeights.NORMAL, new FontDecoration[0]);

		GUIContent content = new GUIContentImp();
		content.setRoot(new GUIContentConfiguration(
			component, lookAndFeel,
			new OneOneStyleGeneratorRoot(), fontSettings));

		return content;
	}

	private static LookAndFeel createLookAndFeel() {
		LookAndFeelBuilder lafBuilder = LookAndFeelBuilder.create();
		SimpleLookAndFeel.installTo(lafBuilder);
		WebicityLookAndFeel.installTo(lafBuilder);
		
		return lafBuilder.build();
	}
	
}
