package everyos.api.getopts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import everyos.api.getopts.TestUtils.StringPrintStream;

public class ArgumentParserTest {
	private static final int T1_ID = 1;
	private static final int T2_ID = 2;
	private static final int T3_ID = 3;
	
	private static ArgumentParser parserWithDefaultAllowed;
	
	@BeforeAll
	public static void setup() {
		ArgumentParserBuilder parserBuilder = ArgumentParser.createBuilder()
			.setFlags(new Flag[] {
				Flag.createBuilder("t1")
					.setID(T1_ID)
					.setNumberRequiredArguments(1)
					.build(),
				Flag.createBuilder("t2")
					.setID(T2_ID)
					.setAlias(new String[] {"t4"})
					.setNumberRequiredArguments(2)
					.build(),
				Flag.createBuilder("t3")
					.setID(T3_ID)
					.build(),
			});
		
		parserBuilder.setAllowLooseArguments(true);
		parserWithDefaultAllowed = parserBuilder.build();	
	}
	
	@Test
	@DisplayName("All flags detected properly")
	public void checkArgumentParserParsesCorrectly() {			
		try {
			FlagArgumentPairCollection arguments = parserWithDefaultAllowed.parse(new String[] {"abc", "--t1", "hi", "-t4", "0", "1"}, StringPrintStream.create());
			
			Assertions.assertEquals(arguments.get(T1_ID).length, 1);
			Assertions.assertEquals(arguments.get(T2_ID).length, 1);
			Assertions.assertEquals(arguments.get(T3_ID).length, 0);
		} catch (ParserFailedException e) {
			Assertions.fail(e);
		}
	}
}
