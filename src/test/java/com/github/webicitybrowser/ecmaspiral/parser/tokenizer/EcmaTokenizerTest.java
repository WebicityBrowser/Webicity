package com.github.webicitybrowser.ecmaspiral.parser.tokenizer;

import java.io.Reader;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.ecmaspiral.parser.tokenizer.imp.TokenizerImp;
import com.github.webicitybrowser.ecmaspiral.parser.tokens.BinaryToken;
import com.github.webicitybrowser.ecmaspiral.parser.tokens.CommentToken;
import com.github.webicitybrowser.ecmaspiral.parser.tokens.IdentifierToken;
import com.github.webicitybrowser.ecmaspiral.parser.tokens.NewlineToken;
import com.github.webicitybrowser.ecmaspiral.parser.tokens.NullToken;
import com.github.webicitybrowser.ecmaspiral.parser.tokens.NumericToken;
import com.github.webicitybrowser.ecmaspiral.parser.tokens.PunctuatorToken;
import com.github.webicitybrowser.ecmaspiral.parser.tokens.StringToken;
import com.github.webicitybrowser.ecmaspiral.parser.tokens.Token;
import com.github.webicitybrowser.ecmaspiral.parser.tokens.WhitespaceToken;

public class EcmaTokenizerTest {
	
	@Test
	@DisplayName("Can tokenize empty source text")
	public void canTokenizeEmptySourceText() {
		Reader source = new StringReader("");
		Tokenizer tokenizer = new TokenizerImp();
		List<Token> tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertTrue(tokens.isEmpty());
	}

	@Test
	@DisplayName("Can tokenize source text with whitespace")
	public void canTokenizeSourceTextWithWhitespace() {
		Reader source = new StringReader(" \t\u000b\f\ufeff\u00A0");
		Tokenizer tokenizer = new TokenizerImp();
		List<Token> tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(1, tokens.size());
		Token token = tokens.get(0);
		Assertions.assertInstanceOf(WhitespaceToken.class, token);
		WhitespaceToken whitespaceToken = (WhitespaceToken) token;
		Assertions.assertEquals(" \t\u000b\f\ufeff\u00A0", whitespaceToken.whitespace());
	}

	@Test
	@DisplayName("Can tokenize source text with newlines")
	public void canTokenizeSourceTextWithNewlines() {
		Reader source = new StringReader("\r\n\n\r\u2029\u2028");
		Tokenizer tokenizer = new TokenizerImp();
		List<Token> tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(1, tokens.size());
		Token token = tokens.get(0);
		Assertions.assertInstanceOf(NewlineToken.class, token);
		NewlineToken newlineToken = (NewlineToken) token;
		Assertions.assertEquals("\r\n\n\u2029\u2028", newlineToken.text());
	}

	@Test
	@DisplayName("Can tokenize source text with single line comment")
	public void canTokenizeSourceTextWithSingleLineComment() {
		Reader source = new StringReader("// comment");
		Tokenizer tokenizer = new TokenizerImp();
		List<Token> tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(1, tokens.size());
		Token token = tokens.get(0);
		Assertions.assertInstanceOf(CommentToken.class, token);
		CommentToken commentToken = (CommentToken) token;
		Assertions.assertEquals("// comment", commentToken.text());
	}

	@Test
	@DisplayName("Can tokenize source text with multi line comment")
	public void canTokenizeSourceTextWithMultiLineComment() {
		Reader source = new StringReader("/* comment\n */");
		Tokenizer tokenizer = new TokenizerImp();
		List<Token> tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(1, tokens.size());
		Token token = tokens.get(0);
		Assertions.assertInstanceOf(CommentToken.class, token);
		CommentToken commentToken = (CommentToken) token;
		Assertions.assertEquals("/* comment\n */", commentToken.text());
	}

	@Test
	@DisplayName("Can tokenize source text with simple identifier")
	public void canTokenizeSourceTextWithSimpleIdentifier() {
		Reader source = new StringReader("identifier");
		Tokenizer tokenizer = new TokenizerImp();
		List<Token> tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(1, tokens.size());
		Token token = tokens.get(0);
		Assertions.assertInstanceOf(IdentifierToken.class, token);
		IdentifierToken identifierToken = (IdentifierToken) token;
		Assertions.assertEquals("identifier", identifierToken.name());
	}

	@Test
	@DisplayName("Can tokenize source text with string")
	public void canTokenizeSourceTextWithString() {
		Reader source = new StringReader("\"string\"");
		Tokenizer tokenizer = new TokenizerImp();
		List<Token> tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(1, tokens.size());
		Token token = tokens.get(0);
		Assertions.assertInstanceOf(StringToken.class, token);
		StringToken stringToken = (StringToken) token;
		Assertions.assertEquals("string", stringToken.value());
	}

	@Test
	@DisplayName("Can tokenize source text with punctuator")
	public void canTokenizeSourceTextWithPunctuator() {
		Reader source = new StringReader("!==");
		Tokenizer tokenizer = new TokenizerImp();
		List<Token> tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(1, tokens.size());
		Token token = tokens.get(0);
		Assertions.assertInstanceOf(PunctuatorToken.class, token);
		PunctuatorToken punctuatorToken = (PunctuatorToken) token;
		Assertions.assertEquals("!==", punctuatorToken.punctuator());
	}

	@Test
	@DisplayName("Can tokenize source text with integer")
	public void canTokenizeSourceTextWithInteger() {
		Reader source = new StringReader("123");
		Tokenizer tokenizer = new TokenizerImp();
		List<Token> tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(1, tokens.size());
		Token token = tokens.get(0);
		Assertions.assertInstanceOf(NumericToken.class, token);
		NumericToken numericToken = (NumericToken) token;
		Assertions.assertEquals(123, numericToken.value().intValue());
	}

	@Test
	@DisplayName("Can tokenize source text with float")
	public void canTokenizeSourceTextWithFloat() {
		Reader source = new StringReader("123.456");
		Tokenizer tokenizer = new TokenizerImp();
		List<Token> tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(1, tokens.size());
		Token token = tokens.get(0);
		Assertions.assertInstanceOf(NumericToken.class, token);
		NumericToken numericToken = (NumericToken) token;
		Assertions.assertEquals(123.456, numericToken.value().doubleValue());
	}

	@Test
	@DisplayName("Can tokenize source text with float with leading dot")
	public void canTokenizeSourceTextWithFloatWithLeadingDot() {
		Reader source = new StringReader(".456");
		Tokenizer tokenizer = new TokenizerImp();
		List<Token> tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(1, tokens.size());
		Token token = tokens.get(0);
		Assertions.assertInstanceOf(NumericToken.class, token);
		NumericToken numericToken = (NumericToken) token;
		Assertions.assertEquals(0.456, numericToken.value().doubleValue());
	}

	@Test
	@DisplayName("Can tokenize source text with exponent")
	public void canTokenizeSourceTextWithExponent() {
		Reader source = new StringReader("123e2");
		Tokenizer tokenizer = new TokenizerImp();
		List<Token> tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(1, tokens.size());
		Token token = tokens.get(0);
		Assertions.assertInstanceOf(NumericToken.class, token);
		NumericToken numericToken = (NumericToken) token;
		Assertions.assertEquals(123e2, numericToken.value().doubleValue());
	}

	@Test
	@DisplayName("Can tokenize source text with integer with separator")
	public void canTokenizeSourceTextWithIntegerWithSeparator() {
		Reader source = new StringReader("123_456_789");
		Tokenizer tokenizer = new TokenizerImp();
		List<Token> tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(1, tokens.size());
		Token token = tokens.get(0);
		Assertions.assertInstanceOf(NumericToken.class, token);
		NumericToken numericToken = (NumericToken) token;
		Assertions.assertEquals(123456789, numericToken.value().intValue());
	}

	@Test
	@DisplayName("Can tokenize source text with zero")
	public void canTokenizeSourceTextWithZero() {
		Reader source = new StringReader("0");
		Tokenizer tokenizer = new TokenizerImp();
		List<Token> tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(1, tokens.size());
		Token token = tokens.get(0);
		Assertions.assertInstanceOf(NumericToken.class, token);
		NumericToken numericToken = (NumericToken) token;
		Assertions.assertEquals(0, numericToken.value().intValue());
	}

	@Test
	@DisplayName("Can tokenize source text with octal")
	public void canTokenizeSourceTextWithOctal() {
		Reader source = new StringReader("0o1234567");
		Tokenizer tokenizer = new TokenizerImp();
		List<Token> tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(1, tokens.size());
		Token token = tokens.get(0);
		Assertions.assertInstanceOf(NumericToken.class, token);
		NumericToken numericToken = (NumericToken) token;
		Assertions.assertEquals(01234567, numericToken.value().intValue());
	}

	@Test
	@DisplayName("Can tokenize source text with octal with separator")
	public void canTokenizeSourceTextWithOctalWithSeparator() {
		Reader source = new StringReader("0o1234567");
		Tokenizer tokenizer = new TokenizerImp();
		List<Token> tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(1, tokens.size());
		Token token = tokens.get(0);
		Assertions.assertInstanceOf(NumericToken.class, token);
		NumericToken numericToken = (NumericToken) token;
		Assertions.assertEquals(01234567, numericToken.value().intValue());
	}

	@Test
	@DisplayName("Can tokenize source text with non-octal integer starting with zero")
	public void canTokenizeSourceTextWithNonOctalIntegerStartingWithZero() {
		Reader source = new StringReader("01234567");
		Tokenizer tokenizer = new TokenizerImp();
		List<Token> tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(1, tokens.size());
		Token token = tokens.get(0);
		Assertions.assertInstanceOf(NumericToken.class, token);
		NumericToken numericToken = (NumericToken) token;
		Assertions.assertEquals(01234567, numericToken.value().intValue());
	}

	@Test
	@DisplayName("Can tokenize source text with hexadecimal")
	public void canTokenizeSourceTextWithHexadecimal() {
		Reader source = new StringReader("0xF7");
		Tokenizer tokenizer = new TokenizerImp();
		List<Token> tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(1, tokens.size());
		Token token = tokens.get(0);
		Assertions.assertInstanceOf(NumericToken.class, token);
		NumericToken numericToken = (NumericToken) token;
		Assertions.assertEquals(0xF7, numericToken.value().intValue());
	}

	@Test
	@DisplayName("Can tokenize source text with hexadecimal with separator")
	public void canTokenizeSourceTextWithHexadecimalWithSeparator() {
		Reader source = new StringReader("0xF_0");
		Tokenizer tokenizer = new TokenizerImp();
		List<Token> tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(1, tokens.size());
		Token token = tokens.get(0);
		Assertions.assertInstanceOf(NumericToken.class, token);
		NumericToken numericToken = (NumericToken) token;
		Assertions.assertEquals(0xF0, numericToken.value().intValue());
	}

	@Test
	@DisplayName("Can tokenize source text with binary")
	public void canTokenizeSourceTextWithBinary() {
		Reader source = new StringReader("0b11110000");
		Tokenizer tokenizer = new TokenizerImp();
		List<Token> tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(1, tokens.size());
		Token token = tokens.get(0);
		Assertions.assertInstanceOf(NumericToken.class, token);
		NumericToken numericToken = (NumericToken) token;
		Assertions.assertEquals(0xF0, numericToken.value().intValue());
	}

	@Test
	@DisplayName("Can tokenize source text with binary with separator")
	public void canTokenizeSourceTextWithBinaryWithSeparator() {
		Reader source = new StringReader("0b1111_0000");
		Tokenizer tokenizer = new TokenizerImp();
		List<Token> tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(1, tokens.size());
		Token token = tokens.get(0);
		Assertions.assertInstanceOf(NumericToken.class, token);
		NumericToken numericToken = (NumericToken) token;
		Assertions.assertEquals(0xF0, numericToken.value().intValue());
	}

	@Test
	@DisplayName("Can tokenize source text with decimal big integer")
	public void canTokenizeSourceTextWithDecimalBigInteger() {
		Reader source = new StringReader("1234567890123456789012345678901234567890n");
		Tokenizer tokenizer = new TokenizerImp();
		List<Token> tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(1, tokens.size());
		Token token = tokens.get(0);
		Assertions.assertInstanceOf(NumericToken.class, token);
		NumericToken numericToken = (NumericToken) token;
		Assertions.assertEquals(new BigInteger("1234567890123456789012345678901234567890"), numericToken.value());
	}

	@Test
	@DisplayName("Can tokenize source text with float starting with zero dot")
	public void canTokenizeSourceTextWithFloatStartingWithZeroDot() {
		Reader source = new StringReader("0.1234");
		Tokenizer tokenizer = new TokenizerImp();
		List<Token> tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(1, tokens.size());
		Token token = tokens.get(0);
		Assertions.assertInstanceOf(NumericToken.class, token);
		NumericToken numericToken1 = (NumericToken) token;
		Assertions.assertEquals(0.1234, numericToken1.value().doubleValue());
	}

	@Test
	@DisplayName("Can tokenize source text with null")
	public void canTokenizeSourceTextWithNull() {
		Reader source = new StringReader("null");
		Tokenizer tokenizer = new TokenizerImp();
		List<Token> tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(1, tokens.size());
		Assertions.assertInstanceOf(NullToken.class, tokens.get(0));
	}

	@Test
	@DisplayName("Can tokenize source text with boolean")
	public void canTokenizeSourceTextWithBoolean() {
		Reader source = new StringReader("true false");
		Tokenizer tokenizer = new TokenizerImp();
		List<Token> tokens = Assertions.assertDoesNotThrow(() -> tokenizer.tokenize(source));
		Assertions.assertEquals(3, tokens.size());
		Assertions.assertInstanceOf(BinaryToken.class, tokens.get(0));
		BinaryToken binaryToken1 = (BinaryToken) tokens.get(0);
		Assertions.assertTrue(binaryToken1.value());
		Assertions.assertInstanceOf(WhitespaceToken.class, tokens.get(1));
		Assertions.assertInstanceOf(BinaryToken.class, tokens.get(2));
		BinaryToken binaryToken2 = (BinaryToken) tokens.get(2);
		Assertions.assertFalse(binaryToken2.value());
	}

}
