package everyos.browser.webicitybrowser;

import java.net.MalformedURLException;

import everyos.api.getopts.ArgumentParser;
import everyos.api.getopts.Flag;
import everyos.api.getopts.ParserFailedException;
import everyos.browser.webicity.net.URL;
import everyos.browser.webicitybrowser.gui.binding.InstanceGUI;
import everyos.browser.webicitybrowser.gui.window.SkijaWindow;
import everyos.browser.webicitybrowser.imp.WebicityArgumentsImp;
import everyos.browser.webicitybrowser.tty.binding.InstanceTTY;
import everyos.browser.webicitybrowser.tty.console.JLineConsole;
import everyos.engine.ribbon.renderer.skijarenderer.SkijaRenderingThread;
import everyos.engine.ttyg.jlinerenderer.JLineRenderingThread;

public class Webicity {
	
	/**
	 * The entry point to Webicity Browser.
	 * Parses the command line arguments and creates an instance
	 * of the browser.
	 * @param args The command line arguments
	 */
	public static void main(String[] args) {
		WebicityArguments arguments;
		try {
			arguments = parseArguments(args);
			startInstance(arguments);
		} catch (ParserFailedException e) {
			// The parser likely already handled this exception for us already
		}
	}

	private static WebicityArguments parseArguments(String[] args) throws ParserFailedException {
		ArgumentParser parser = createArgumentParser();
		
		return new WebicityArgumentsImp(parser, parser.parse(args, System.out));
	}
	
	private static ArgumentParser createArgumentParser() {
		//TODO: Help flag?
		
		Flag[] flags = {
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
		
		//TODO: Should we have a builder?
		return ArgumentParser.createBuilder()
			.setFlags(flags)
			.setAllowLooseArguments(true)
			.setHelpHeader("Webicity Browser by EveryOS (Jason Gronn)\n\nA web browser and rendering engine written in Java")
			.setErrorFooter("Re-run Webicity with only the \"--help\" flag to view the help screen")
			.setHelpFooter("Any extra arguments must be a URL to open automatically")
			.build();
	}

	private static void startInstance(WebicityArguments arguments) {
		//TODO: Static stuff like this is probably better elsewhere
		System.setProperty("logging.level", "INFO");
		if (arguments.getVerbose()) {
			System.setProperty("logging.level", "TRACE");
		}
		
		WebicityInstance instance = new WebicityInstance(arguments.getPrivate());
		
		if (arguments.getURLs().length == 0) {
			try {
				//TODO: Configuration
				//instance.open(new URL("webicity://csstest"));
				//instance.open(new URL("https://wpt.live/css/css-color/"));
				//instance.open(new URL("https://www.google.com/"));
				//instance.open(new URL("https://www.yahoo.com/"));
				//instance.open(new URL("https://www.whatismybrowser.com/"));
				instance.open(new URL("https://www.example.com/"));
				//instance.open(new URL("https://khronos.org/registry/vulkan/specs/1.2-extensions/html/vkspec.html"));
				//instance.open(new URL("https://html.spec.whatwg.org/multipage/parsing.html#named-character-reference-state"));
				//instance.open(new URL("file:///C:\\Users\\JasonGronn\\Downloads\\vkspec.html"));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else {
			for (URL url: arguments.getURLs()) {
				instance.open(url);
			}
		}
		
		instance.start();
		
		boolean useTTY = false;
		
		if (!useTTY) {
			SkijaRenderingThread.run(()->{
				new InstanceGUI(instance, ()->{
					try {
						return SkijaWindow.create();
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}).start();
			});
		} else {
			JLineRenderingThread.run(()->{
				new InstanceTTY(instance, JLineConsole.get()).start();
			});
		}
	}
}
