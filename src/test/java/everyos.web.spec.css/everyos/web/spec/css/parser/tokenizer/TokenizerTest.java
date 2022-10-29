package everyos.web.spec.css.parser.tokenizer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import everyos.web.spec.css.parser.tokens.EOFToken;
import everyos.web.spec.css.parser.tokens.HashToken;
import everyos.web.spec.css.parser.tokens.HashToken.TypeFlag;
import everyos.web.spec.css.parser.tokens.StringToken;
import everyos.web.spec.css.parser.tokens.Token;
import everyos.web.spec.css.parser.tokens.WhitespaceToken;

public class TokenizerTest {

	private Tokenizer tokenizer;
	
	@BeforeEach
	public void beforeEach() {
		this.tokenizer = new TokenizerImp();
	}
	
	// TODO: Test that we ignore comments
	
	@Test
	@DisplayName("Can tokenize simple whitespace")
	public void canTokenizeSimpleWhitespace() {
		Reader source = reader(" ");
		Token[] tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(2, tokens.length);
		Assertions.assertInstanceOf(WhitespaceToken.class, tokens[0]);
		Assertions.assertInstanceOf(EOFToken.class, tokens[1]);
	}
	
	@Test
	@DisplayName("Can tokenize complex whitespace")
	public void canTokenizeComplexWhitespace() {
		Reader source = reader(" \t\n\r\r\n");
		Token[] tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(2, tokens.length);
		Assertions.assertInstanceOf(WhitespaceToken.class, tokens[0]);
		Assertions.assertInstanceOf(EOFToken.class, tokens[1]);
	}
	
	@Test
	@DisplayName("Can tokenize a string")
	public void canTokenizeAString() {
		Reader source = reader("\"Hello, World!\"");
		Token[] tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(2, tokens.length);
		Assertions.assertInstanceOf(StringToken.class, tokens[0]);
		Assertions.assertInstanceOf(EOFToken.class, tokens[1]);
		Assertions.assertEquals("Hello, World!", ((StringToken) tokens[0]).getValue());
	}
	
	@Test
	@DisplayName("Can tokenize a string with an escape")
	public void canTokenizeAStringWithAnEscape() {
		Reader source = reader("\"\\\"\\ABCD\"");
		Token[] tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(2, tokens.length);
		Assertions.assertInstanceOf(StringToken.class, tokens[0]);
		Assertions.assertInstanceOf(EOFToken.class, tokens[1]);
		Assertions.assertEquals("\"\uABCD", ((StringToken) tokens[0]).getValue());
	}
	
	@Test
	@DisplayName("Can tokenize a string with an apostrophe")
	public void canTokenizeAStringWithAnApostrophe() {
		Reader source = reader("'Hello, World!'");
		Token[] tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(2, tokens.length);
		Assertions.assertInstanceOf(StringToken.class, tokens[0]);
		Assertions.assertInstanceOf(EOFToken.class, tokens[1]);
		Assertions.assertEquals("Hello, World!", ((StringToken) tokens[0]).getValue());
	}
	
	@Test
	@DisplayName("Can tokenize a hash")
	public void canTokenizeAHash() {
		Reader source = reader("#hello");
		Token[] tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(2, tokens.length);
		Assertions.assertInstanceOf(HashToken.class, tokens[0]);
		Assertions.assertInstanceOf(EOFToken.class, tokens[1]);
		Assertions.assertEquals("hello", ((HashToken) tokens[0]).getValue());
		Assertions.assertEquals(TypeFlag.ID, ((HashToken) tokens[0]).getTypeFlag());
	}
	
	//

	private Reader reader(String string) {
		InputStream stream = new ByteArrayInputStream(string.getBytes());
		Reader reader = new InputStreamReader(stream);
		
		return reader;
	}
	
}
