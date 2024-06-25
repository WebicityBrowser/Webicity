package com.github.webicitybrowser.spec.css.parser.tokenizer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.css.parser.tokenizer.imp.TokenizerImp;
import com.github.webicitybrowser.spec.css.parser.tokens.AtKeywordToken;
import com.github.webicitybrowser.spec.css.parser.tokens.CDCToken;
import com.github.webicitybrowser.spec.css.parser.tokens.CDOToken;
import com.github.webicitybrowser.spec.css.parser.tokens.DimensionToken;
import com.github.webicitybrowser.spec.css.parser.tokens.EOFToken;
import com.github.webicitybrowser.spec.css.parser.tokens.FunctionToken;
import com.github.webicitybrowser.spec.css.parser.tokens.HashToken;
import com.github.webicitybrowser.spec.css.parser.tokens.HashToken.HashTypeFlag;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.parser.tokens.NumberToken;
import com.github.webicitybrowser.spec.css.parser.tokens.NumberTypeFlag;
import com.github.webicitybrowser.spec.css.parser.tokens.PercentageToken;
import com.github.webicitybrowser.spec.css.parser.tokens.StringToken;
import com.github.webicitybrowser.spec.css.parser.tokens.Token;
import com.github.webicitybrowser.spec.css.parser.tokens.URLToken;
import com.github.webicitybrowser.spec.css.parser.tokens.WhitespaceToken;

public class CSSTokenizerTest {

	private CSSTokenizer tokenizer;
	
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
		Assertions.assertEquals(HashTypeFlag.ID, ((HashToken) tokens[0]).getTypeFlag());
	}
	
	@Test
	@DisplayName("Can tokenize an integer")
	public void canTokenizeAnInteger() {
		Reader source = reader("+123");
		Token[] tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(2, tokens.length);
		Assertions.assertInstanceOf(NumberToken.class, tokens[0]);
		Assertions.assertInstanceOf(EOFToken.class, tokens[1]);
		Assertions.assertEquals(123, ((NumberToken) tokens[0]).getValue());
		Assertions.assertEquals(NumberTypeFlag.INTEGER, ((NumberToken) tokens[0]).getTypeFlag());
	}
	
	@Test
	@DisplayName("Can tokenize a floating point number")
	public void canTokenizeAFloatingPointNumber() {
		Reader source = reader("-1.5e3");
		Token[] tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(2, tokens.length);
		Assertions.assertInstanceOf(NumberToken.class, tokens[0]);
		Assertions.assertInstanceOf(EOFToken.class, tokens[1]);
		Assertions.assertEquals(-1.5e3, ((NumberToken) tokens[0]).getValue());
		Assertions.assertEquals(NumberTypeFlag.NUMBER, ((NumberToken) tokens[0]).getTypeFlag());
	}
	
	@Test
	@DisplayName("Can tokenize a small floating point number")
	public void canTokenizeASmallFloatingPointNumber() {
		Reader source = reader(".5");
		Token[] tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(2, tokens.length);
		Assertions.assertInstanceOf(NumberToken.class, tokens[0]);
		Assertions.assertInstanceOf(EOFToken.class, tokens[1]);
		Assertions.assertEquals(.5, ((NumberToken) tokens[0]).getValue());
		Assertions.assertEquals(NumberTypeFlag.NUMBER, ((NumberToken) tokens[0]).getTypeFlag());
	}
	
	@Test
	@DisplayName("Can tokenize a dimension")
	public void canTokenizeADimension() {
		Reader source = reader("5px");
		Token[] tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(2, tokens.length);
		Assertions.assertInstanceOf(DimensionToken.class, tokens[0]);
		Assertions.assertInstanceOf(EOFToken.class, tokens[1]);
		Assertions.assertEquals(5, ((DimensionToken) tokens[0]).getValue());
		Assertions.assertEquals(NumberTypeFlag.INTEGER, ((DimensionToken) tokens[0]).getTypeFlag());
		Assertions.assertEquals("px", ((DimensionToken) tokens[0]).getUnit());
	}
	
	@Test
	@DisplayName("Can tokenize a percentage")
	public void canTokenizeAPercentage() {
		Reader source = reader("9%");
		Token[] tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(2, tokens.length);
		Assertions.assertInstanceOf(PercentageToken.class, tokens[0]);
		Assertions.assertInstanceOf(EOFToken.class, tokens[1]);
		Assertions.assertEquals(9, ((PercentageToken) tokens[0]).getValue());
	}
	
	@Test
	@DisplayName("Can tokenize cdc")
	public void canTokenizeCDC() {
		Reader source = reader("-->");
		Token[] tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(2, tokens.length);
		Assertions.assertInstanceOf(CDCToken.class, tokens[0]);
		Assertions.assertInstanceOf(EOFToken.class, tokens[1]);
	}
	
	@Test
	@DisplayName("Can tokenize cdo")
	public void canTokenizeCDO() {
		Reader source = reader("<!--");
		Token[] tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(2, tokens.length);
		Assertions.assertInstanceOf(CDOToken.class, tokens[0]);
		Assertions.assertInstanceOf(EOFToken.class, tokens[1]);
	}
	
	@Test
	@DisplayName("Can tokenize an ident token")
	public void canTokenizeAnIdentToken() {
		Reader source = reader("--hello");
		Token[] tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(2, tokens.length);
		Assertions.assertInstanceOf(IdentToken.class, tokens[0]);
		Assertions.assertInstanceOf(EOFToken.class, tokens[1]);
		Assertions.assertEquals("--hello", ((IdentToken) tokens[0]).getValue());
	}
	
	@Test
	@DisplayName("Can tokenize a function token")
	public void canTokenizeAFunctionToken() {
		Reader source = reader("calc(");
		Token[] tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(2, tokens.length);
		Assertions.assertInstanceOf(FunctionToken.class, tokens[0]);
		Assertions.assertInstanceOf(EOFToken.class, tokens[1]);
		Assertions.assertEquals("calc", ((FunctionToken) tokens[0]).getValue());
	}
	
	@Test
	@DisplayName("Can tokenize a URL token")
	public void canTokenizeAURLToken() {
		Reader source = reader("url(https://google.com/)");
		Token[] tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(2, tokens.length);
		Assertions.assertInstanceOf(URLToken.class, tokens[0]);
		Assertions.assertInstanceOf(EOFToken.class, tokens[1]);
		Assertions.assertEquals("https://google.com/", ((URLToken) tokens[0]).getValue());
	}
	
	@Test
	@DisplayName("Can tokenize an at-keyword token")
	public void canTokenizeAnAtKeywordToken() {
		Reader source = reader("@media");
		Token[] tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(2, tokens.length);
		Assertions.assertInstanceOf(AtKeywordToken.class, tokens[0]);
		Assertions.assertInstanceOf(EOFToken.class, tokens[1]);
		Assertions.assertEquals("media", ((AtKeywordToken) tokens[0]).getValue());
	}
	
	@Test
	@DisplayName("Can tokenize an escaped ident token")
	public void canTokenizeAnEscapedIdentToken() {
		Reader source = reader("\\{hello");
		Token[] tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(2, tokens.length);
		Assertions.assertInstanceOf(IdentToken.class, tokens[0]);
		Assertions.assertInstanceOf(EOFToken.class, tokens[1]);
		Assertions.assertEquals("{hello", ((IdentToken) tokens[0]).getValue());
	}
	
	//

	private Reader reader(String string) {
		InputStream stream = new ByteArrayInputStream(string.getBytes());
		Reader reader = new InputStreamReader(stream);
		
		return reader;
	}
	
}
