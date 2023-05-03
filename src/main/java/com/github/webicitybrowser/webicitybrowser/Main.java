package com.github.webicitybrowser.webicitybrowser;

import java.util.function.Consumer;

import com.github.webicitybrowser.spec.url.InvalidURLException;
import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.thready.gui.directive.basics.style.NoopStyleGeneratorRoot;
import com.github.webicitybrowser.thready.gui.graphical.base.GUIContent;
import com.github.webicitybrowser.thready.gui.graphical.base.imp.GUIContentImp;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeelBuilder;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.SimpleLookAndFeel;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.thready.windowing.core.DesktopWindow;
import com.github.webicitybrowser.thready.windowing.core.GraphicsSystem;
import com.github.webicitybrowser.thready.windowing.skija.SkijaGraphicsSystem;
import com.github.webicitybrowser.webicity.core.AssetLoader;
import com.github.webicitybrowser.webicity.core.RenderingEngine;
import com.github.webicitybrowser.webicity.core.net.ProtocolRegistry;
import com.github.webicitybrowser.webicity.protocol.AboutProtocol;
import com.github.webicitybrowser.webicity.protocol.FileProtocol;
import com.github.webicitybrowser.webicity.renderer.backend.html.HTMLRendererBackend;
import com.github.webicitybrowser.webicitybrowser.gui.binding.WindowSetGUI;
import com.github.webicitybrowser.webicitybrowser.gui.ui.WebicityLookAndFeel;
import com.github.webicitybrowser.webicitybrowser.gui.window.DesktopGUIWindow;
import com.github.webicitybrowser.webicitybrowser.gui.window.GUIWindow;
import com.github.webicitybrowser.webicitybrowser.imp.BrowserInstanceImp;
import com.github.webicitybrowser.webicitybrowser.loader.ResourceAssetLoader;
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
		AssetLoader assetLoader = new ResourceAssetLoader();
		RenderingEngine renderingEngine = RenderingEngine.create(assetLoader);
		
		ProtocolRegistry protocolRegistry = renderingEngine.getProtocolRegistry();
		protocolRegistry.registerProtocol(new FileProtocol());
		protocolRegistry.registerProtocol(new AboutProtocol());
		
		renderingEngine.getBackendRendererRegistry()
			.registerBackendFactory("text/html", HTMLRendererBackend::new);
		
		return renderingEngine;
	}

	private static void startGUIFor(BrowserInstance browserInstance, WindowSet windowSet) {
		new WindowSetGUI(browserInstance, windowSet,
			(component, callback) -> createWindowFor(component, callback)).start();
	}
	
	private static void createWindowFor(Component component, Consumer<GUIWindow> callback) {
		LookAndFeel lookAndFeel = createLookAndFeel();
		GraphicsSystem graphicsSystem = SkijaGraphicsSystem.createDefault();
		GUIContent content = new GUIContentImp();
		content.setRoot(component, lookAndFeel, new NoopStyleGeneratorRoot());
		
		graphicsSystem.createWindow(window -> {
			DesktopWindow desktopWindow = (DesktopWindow) window;
			
			window
				.getScreen()
				.setScreenContent(content);
			
			desktopWindow.setTitle("Webicity");
			desktopWindow.setDecorated(false);
			
			callback.accept(new DesktopGUIWindow((DesktopWindow) window));
		});
	}

	private static LookAndFeel createLookAndFeel() {
		LookAndFeelBuilder lafBuilder = LookAndFeelBuilder.create();
		SimpleLookAndFeel.installTo(lafBuilder);
		WebicityLookAndFeel.installTo(lafBuilder);
		
		return lafBuilder.build();
	}
	
}
