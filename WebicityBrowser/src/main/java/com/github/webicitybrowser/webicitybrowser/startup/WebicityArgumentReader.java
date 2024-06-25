package com.github.webicitybrowser.webicitybrowser.startup;

import com.github.webicitybrowser.webicitybrowser.WebicityArguments;
import com.github.webicitybrowser.webicitybrowser.imp.WebicityArgumentsImp;

import everyos.api.getopts.ArgumentParser;
import everyos.api.getopts.Flag;
import everyos.api.getopts.ParserFailedException;

public final class WebicityArgumentReader {
	
	private WebicityArgumentReader() {}
	
	public static WebicityArguments parseArguments(String[] args) throws ParserFailedException {
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

}
