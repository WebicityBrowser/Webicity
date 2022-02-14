package everyos.browser.webicitybrowser;

import java.net.MalformedURLException;

import com.github.anythingide.lace.backend.skija.SkijaWindow;
import com.github.anythingide.lace.backend.skija.SkijaWindowCreationSettings;
import com.github.anythingide.lace.backend.skija.SkijaWindowCreationSettingsBuilder;

import everyos.api.getopts.ArgumentParser;
import everyos.api.getopts.Flag;
import everyos.api.getopts.ParserFailedException;
import everyos.browser.spec.jnet.URL;
import everyos.browser.webicitybrowser.gui.binding.InstanceGUI;
import everyos.browser.webicitybrowser.gui.window.SkijaGUIWindow;
import everyos.browser.webicitybrowser.imp.WebicityArgumentsImp;
import everyos.browser.webicitybrowser.tty.binding.InstanceTTY;
import everyos.browser.webicitybrowser.tty.console.JLineConsole;
import everyos.engine.ttyg.jlinerenderer.JLineRenderingThread;

public class Webicity {
	
	/**
	 * The entry point to Webicity Browser.
	 * Parses the command line arguments and creates an instance
	 * of the browser.
	 * @param args The command line arguments
	 */
	public static void main(String[] args) {
		try {
			startInstanceFromArguments(parseArguments(args));
		} catch (ParserFailedException e) {
			// The parser likely already handled this exception for us already
		}
	}

	private static WebicityArguments parseArguments(String[] args) throws ParserFailedException {
		ArgumentParser parser = createArgumentParser();
		
		return new WebicityArgumentsImp(parser, parser.parse(args));
	}
	
	private static ArgumentParser createArgumentParser() {
		Flag[] flags = createArgumentParserFlags();
		
		return createArgumentParserWithFlags(flags);
	}

	private static ArgumentParser createArgumentParserWithFlags(Flag[] flags) {
		return ArgumentParser.createBuilder()
			.setFlags(flags)
			.setAllowLooseArguments(true)
			.setHelpHeader("Webicity Browser by EveryOS (Jason Gronn)\n\nA web browser and rendering engine written in Java")
			.setErrorFooter("Re-run Webicity with only the \"--help\" flag to view the help screen")
			.setHelpFooter("Any extra arguments must be a URL to open automatically")
			.setLogStream(System.out)
			.build();
	}

	private static Flag[] createArgumentParserFlags() {
		return new Flag[] {
			Flag.createBuilder("private")
				.setID(WebicityArgumentsImp.PRIVATE_FLAG)
				.setAlias("p")
				.setDescription("Open in a private window")
				.build(),
			Flag.createBuilder("verbose")
				.setID(WebicityArgumentsImp.VERBOSE_FLAG)
				.setAlias("v")
				.setDescription("Use verbose logging")
				.build(),
			Flag.createBuilder("help")
				.setID(WebicityArgumentsImp.HELP_FLAG)
				.setAlias("h")
				.setDescription("Display this screen")
				.build(),
			Flag.createBuilder("display")
				.setID(WebicityArgumentsImp.DISPLAY_FLAG)
				.setAlias("d")
				.setDescription("Set the display backend to one of: skija, jline3")
				.setNumberRequiredArguments(1)
				.setAllowDuplicates(false)
				.build()
		};
	}

	private static void startInstanceFromArguments(WebicityArguments arguments) {
		//TODO: Static stuff like this is probably better elsewhere
		System.setProperty("logging.level", arguments.getVerbose() ? "TRACE" : "INFO");
		
		WebicityInstance instance = new WebicityInstance(arguments.getPrivate());
		openInitialPages(instance, arguments);
		instance.start();
		
		startUIFor(instance);
	}

	private static void openInitialPages(WebicityInstance instance, WebicityArguments arguments) {
		if (arguments.getURLs().length == 0) {
			openDefaultURLs(instance);
		} else {
			openURLsFromArguments(instance, arguments);
		}
	}
	
	private static void openURLsFromArguments(WebicityInstance instance, WebicityArguments arguments) {
		for (URL url: arguments.getURLs()) {
			instance.open(url);
		}
	}

	private static void openDefaultURLs(WebicityInstance instance) {
		try {
			//TODO: Configuration
			//instance.open(new URL("webicity://welcome"));
			instance.open(new URL("webicity://csstest"));
			//instance.open(new URL("https://wpt.live/css/css-display/display-contents-inline-001.html"));
			//instance.open(new URL("https://www.google.com/"));
			//instance.open(new URL("https://www.yahoo.com/"));
			//instance.open(new URL("https://www.whatismybrowser.com/"));
			//instance.open(new URL("https://www.example.com/"));
			//instance.open(new URL("https://khronos.org/registry/vulkan/specs/1.2-extensions/html/vkspec.html"));
			//instance.open(new URL("https://html.spec.whatwg.org/multipage/parsing.html#named-character-reference-state"));
			//instance.open(new URL("file:///C:\\Users\\JasonGronn\\Downloads\\vkspec.html"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	private static void startUIFor(WebicityInstance instance) {
		boolean useTTY = false;
		if (!useTTY) {
			new InstanceGUI(instance, callback -> {
				SkijaWindowCreationSettings windowSettings = new SkijaWindowCreationSettingsBuilder().build();
				SkijaWindow.createWindow(windowSettings, window -> callback.accept(new SkijaGUIWindow(window)));
			}).start();
		} else {
			JLineRenderingThread.run(() -> {
				new InstanceTTY(instance, JLineConsole.get()).start();
			});
		}
	}
	
}
