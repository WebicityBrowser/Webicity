package everyos.web.spec.idl.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import everyos.web.spec.idl.IDLFragment;
import everyos.web.spec.idl.definition.EnumDefinition;
import everyos.web.spec.idl.parser.tokens.DelimToken;
import everyos.web.spec.idl.parser.tokens.EOFToken;
import everyos.web.spec.idl.parser.tokens.IdentToken;
import everyos.web.spec.idl.parser.tokens.KeywordToken;
import everyos.web.spec.idl.parser.tokens.Token;

public class IDLParserTest {

	private IDLParser idlParser;
	
	@BeforeEach
	public void beforeEach() {
		this.idlParser = new IDLParserImp();
	}
	
	@Test
	@DisplayName("Can parse empty IDL body")
	public void canParseEmptyIDLBody() {
		Token[] tokens = new Token[] {};
		IDLFragment fragment = idlParser.parse(tokens);
		Assertions.assertEquals(0, fragment.getDefinitions().length);
	}
	
	@Test
	@DisplayName("Can parse IDL with empty enum definition")
	public void canParseIDLWithSimpleDefinition() {
		KeywordToken keywordToken = Mockito.mock(KeywordToken.class);
		Mockito.when(keywordToken.getValue()).thenReturn("enum");
		IdentToken identToken = Mockito.mock(IdentToken.class);
		Mockito.when(identToken.getValue()).thenReturn("name");
		DelimToken lcBracketToken = Mockito.mock(DelimToken.class);
		Mockito.when(lcBracketToken.getValue()).thenReturn((int) '{');
		DelimToken rcBracketToken = Mockito.mock(DelimToken.class);
		Mockito.when(rcBracketToken.getValue()).thenReturn((int) '}');
		DelimToken semicolonToken = Mockito.mock(DelimToken.class);
		Mockito.when(semicolonToken.getValue()).thenReturn((int) ';');
		EOFToken eofToken = Mockito.mock(EOFToken.class);
		Token[] tokens = new Token[] {
			keywordToken, identToken,
			lcBracketToken, rcBracketToken,
			semicolonToken, eofToken
		};
		IDLFragment fragment = idlParser.parse(tokens);
		Assertions.assertEquals(1, fragment.getDefinitions().length);
		Assertions.assertInstanceOf(EnumDefinition.class, fragment.getDefinitions()[0]);
		EnumDefinition definition = (EnumDefinition) fragment.getDefinitions()[0];
		Assertions.assertEquals(0, definition.getEnumValues().length);
	}
	
}
