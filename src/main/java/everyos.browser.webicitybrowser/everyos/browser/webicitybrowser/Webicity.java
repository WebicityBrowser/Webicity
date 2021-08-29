package everyos.browser.webicitybrowser;

import java.net.MalformedURLException;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import everyos.api.getopts.ArgumentParser;
import everyos.api.getopts.Flag;
import everyos.api.getopts.ParserFailedException;
import everyos.browser.webicity.net.URL;
import everyos.browser.webicitybrowser.gui.binding.InstanceGUI;
import everyos.browser.webicitybrowser.gui.window.SkijaWindow;
import everyos.browser.webicitybrowser.imp.WebicityArgumentsImp;

public class Webicity {
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
		
		return new WebicityArgumentsImp(parser, parser.parse(args));
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
		Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		logger.setLevel(Level.INFO);
		if (arguments.getVerbose()) {
			logger.setLevel(Level.ALL);
		}
		
		WebicityInstance instance = new WebicityInstance(arguments.getPrivate());
		
		if (arguments.getURLs().length==0) {
			try {
				//TODO: Configuration
				instance.open(new URL("webicity://csstest"));
				//instance.open(new URL("https://www.yahoo.com/"));
				//instance.open(new URL("https://www.whatismybrowser.com/"));
				//instance.open(new URL("https://www.example.com/"));
				instance.open(new URL("https://khronos.org/registry/vulkan/specs/1.2-extensions/html/vkspec.html"));
				//instance.open(new URL("https://html.spec.whatwg.org/multipage/parsing.html#named-character-reference-state"));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else {
			for (URL url: arguments.getURLs()) {
				instance.open(url);
			}
		}
		
		instance.start();
		
		new InstanceGUI(instance, ()->{
			try {
				return SkijaWindow.create();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).start();
	}
}
