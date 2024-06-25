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
	private static final int T4_ID = 4;
	private static final int T5_ID = 5;
	
	private static ArgumentParser parserWithDefaultAllowed;
	
	@BeforeAll
	public static void setup() {
		ArgumentParserBuilder parserBuilder = ArgumentParser.createBuilder()
			.setFlags(new Flag[] {
				Flag.createBuilder("t1")
					.setID(T1_ID)
					.setAlias(new String[] {"1"})
					.setAllowDuplicates(true)
					.build(),
				Flag.createBuilder("t2")
					.setID(T2_ID)
					.setAlias(new String[] {"2"})
					.setNumberRequiredArguments(1)
					.build(),
				Flag.createBuilder("t3")
					.setID(T3_ID)
					.setAlias(new String[] {"3"})
					.setNumberRequiredArguments(2)
					.build(),
				Flag.createBuilder("t4")
					.setID(T4_ID)
					.setAlias(new String[] {"4"})
					.setNumberOptionalArguments(1)
					.build(),
				Flag.createBuilder("t5")
					.setID(T5_ID)
					.setAlias(new String[] {"5"})
					.setAllowDuplicates(false)
					.build(),
			});
		
		parserBuilder.setAllowLooseArguments(true);
		parserBuilder.setLogStream(StringPrintStream.create());
		parserWithDefaultAllowed = parserBuilder.build();	
	}
	
	@Test
	@DisplayName("No flags parses correctly")
	public void checkNoFlagsParsesCorrectly() {			
		try {
			FlagArgumentPairCollection arguments = parserWithDefaultAllowed.parse(new String[] {});
			
			Assertions.assertEquals(arguments.get(Flag.NO_FLAG).length, 1);
			Assertions.assertEquals(arguments.get(Flag.NO_FLAG)[0].getArguments().length, 0);
			Assertions.assertEquals(arguments.get(T1_ID).length, 0);
			Assertions.assertEquals(arguments.get(T2_ID).length, 0);
			Assertions.assertEquals(arguments.get(T3_ID).length, 0);
		} catch (ParserFailedException e) {
			Assertions.fail(e);
		}
	}
	
	@Test
	@DisplayName("Single flag parses correctly")
	public void checkSingleFlagParsesCorrectly() {			
		try {
			FlagArgumentPairCollection arguments = parserWithDefaultAllowed.parse(new String[] {"--t1"});
			
			Assertions.assertEquals(arguments.get(Flag.NO_FLAG).length, 1);
			Assertions.assertEquals(arguments.get(Flag.NO_FLAG)[0].getArguments().length, 0);
			Assertions.assertEquals(arguments.get(T1_ID).length, 1);
			Assertions.assertEquals(arguments.get(T2_ID).length, 0);
		} catch (ParserFailedException e) {
			Assertions.fail(e);
		}
	}
	
	@Test
	@DisplayName("Single alias parses correctly")
	public void checkSingleAliasParsesCorrectly() {			
		try {
			FlagArgumentPairCollection arguments = parserWithDefaultAllowed.parse(new String[] {"-1"});
			
			Assertions.assertEquals(arguments.get(Flag.NO_FLAG).length, 1);
			Assertions.assertEquals(arguments.get(Flag.NO_FLAG)[0].getArguments().length, 0);
			Assertions.assertEquals(arguments.get(T1_ID).length, 1);
			Assertions.assertEquals(arguments.get(T2_ID).length, 0);
		} catch (ParserFailedException e) {
			Assertions.fail(e);
		}
	}
	
	@Test
	@DisplayName("Flag with one argument parses correctly")
	public void checkOneArgumentFlagParsesCorrectly() {			
		try {
			FlagArgumentPairCollection arguments = parserWithDefaultAllowed.parse(new String[] {"--t2", "hello"});
			
			Assertions.assertEquals(arguments.get(Flag.NO_FLAG).length, 1);
			Assertions.assertEquals(arguments.get(Flag.NO_FLAG)[0].getArguments().length, 0);
			Assertions.assertEquals(arguments.get(T1_ID).length, 0);
			Assertions.assertEquals(arguments.get(T2_ID).length, 1);
			
			FlagArgumentPair[] pair = arguments.get(T2_ID);
			Assertions.assertEquals(pair.length, 1);
			Assertions.assertEquals(pair[0].getFlag().getID(), T2_ID);
			Argument[] flagArguments = pair[0].getArguments();
			Assertions.assertEquals(flagArguments.length, 1);
			Assertions.assertEquals(flagArguments[0].read((s, eh) -> s), "hello");
			
		} catch (ParserFailedException e) {
			Assertions.fail(e);
		}
	}
	
	@Test
	@DisplayName("Alias with one argument parses correctly")
	public void checkOneArgumentAliasParsesCorrectly() {			
		try {
			FlagArgumentPairCollection arguments = parserWithDefaultAllowed.parse(new String[] {"-2", "hello"});
			
			Assertions.assertEquals(arguments.get(Flag.NO_FLAG).length, 1);
			Assertions.assertEquals(arguments.get(Flag.NO_FLAG)[0].getArguments().length, 0);
			Assertions.assertEquals(arguments.get(T1_ID).length, 0);
			Assertions.assertEquals(arguments.get(T2_ID).length, 1);
			
			FlagArgumentPair[] pair = arguments.get(T2_ID);
			Assertions.assertEquals(pair.length, 1);
			Assertions.assertEquals(pair[0].getFlag().getID(), T2_ID);
			Argument[] flagArguments = pair[0].getArguments();
			Assertions.assertEquals(flagArguments.length, 1);
			Assertions.assertEquals(flagArguments[0].read((s, eh) -> s), "hello");
			
		} catch (ParserFailedException e) {
			Assertions.fail(e);
		}
	}
	
	@Test
	@DisplayName("Flag with two arguments parses correctly")
	public void checkTwoArgumentFlagParsesCorrectly() {			
		try {
			FlagArgumentPairCollection arguments = parserWithDefaultAllowed.parse(new String[] {"--t3", "hello", "world"});
			
			Assertions.assertEquals(arguments.get(Flag.NO_FLAG).length, 1);
			Assertions.assertEquals(arguments.get(Flag.NO_FLAG)[0].getArguments().length, 0);
			Assertions.assertEquals(arguments.get(T1_ID).length, 0);
			Assertions.assertEquals(arguments.get(T3_ID).length, 1);
			
			FlagArgumentPair[] pair = arguments.get(T3_ID);
			Assertions.assertEquals(pair.length, 1);
			Assertions.assertEquals(pair[0].getFlag().getID(), T3_ID);
			Argument[] flagArguments = pair[0].getArguments();
			Assertions.assertEquals(flagArguments.length, 2);
			Assertions.assertEquals(flagArguments[0].read((s, eh) -> s), "hello");
			Assertions.assertEquals(flagArguments[1].read((s, eh) -> s), "world");
		} catch (ParserFailedException e) {
			Assertions.fail(e);
		}
	}
	
	@Test
	@DisplayName("Flag with two arguments but applied with one argument throws exception")
	public void checkNotEnoughArgumentsException() {		
		Assertions.assertThrows(ParserFailedException.class, () -> {
			parserWithDefaultAllowed.parse(new String[] {"--t3", "hello"});
		});
	}
	
	@Test
	@DisplayName("Applying flag as argument throws exception")
	public void checkFlagAsArgumentException() {		
		Assertions.assertThrows(ParserFailedException.class, () -> {
			parserWithDefaultAllowed.parse(new String[] {"--t2", "--t1"});
		});
	}
	
	@Test
	@DisplayName("Optional arguments are applied as flag arguments instead of loose arguments")
	public void checkOptionalArgumentsParsesCorrectly() {			
		try {
			FlagArgumentPairCollection arguments = parserWithDefaultAllowed.parse(new String[] {"--t4", "hello"});
			
			Assertions.assertEquals(arguments.get(Flag.NO_FLAG).length, 1);
			Assertions.assertEquals(arguments.get(Flag.NO_FLAG)[0].getArguments().length, 0);
			Assertions.assertEquals(arguments.get(T4_ID).length, 1);
		} catch (ParserFailedException e) {
			Assertions.fail(e);
		}
	}
	
	@Test
	@DisplayName("Optional arguments are optional")
	public void checkOptionalArgumentsAreOptional() {			
		try {
			FlagArgumentPairCollection arguments = parserWithDefaultAllowed.parse(new String[] {"--t4"});
			
			Assertions.assertEquals(arguments.get(T4_ID)[0].getArguments().length, 0);
		} catch (ParserFailedException e) {
			Assertions.fail(e);
		}
	}
	
	@Test
	@DisplayName("Duplicatable flags can be duplicated")
	public void checkDuplicatableFlagsCanBeDuplicated() {			
		try {
			parserWithDefaultAllowed.parse(new String[] {"--t1", "--t1"});
		} catch (ParserFailedException e) {
			Assertions.fail(e);
		}
	}
	
	@Test
	@DisplayName("Non-duplicatable flags cannot be duplicated")
	public void checkNonDuplicatableFlagsCannotBeDuplicated() {			
		Assertions.assertThrows(ParserFailedException.class, () -> {
			parserWithDefaultAllowed.parse(new String[] {"--t4", "--t4"});
		});
	}
	

	@Test
	@DisplayName("Non-duplicatable flags cannot be duplicated with alias")
	public void checkNonDuplicatableFlagsCannotBeDuplicatedWithAlias() {			
		Assertions.assertThrows(ParserFailedException.class, () -> {
			parserWithDefaultAllowed.parse(new String[] {"--t4", "-4"});
		});
	}
	
	//TODO: Test flags with infinite optional arguments
	//TODO: Tests for mandatory flags and loose arguments
	//TODO: Tests for help and error screens?
	
}
