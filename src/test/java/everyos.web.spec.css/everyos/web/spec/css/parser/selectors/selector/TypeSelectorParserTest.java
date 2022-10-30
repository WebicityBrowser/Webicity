package everyos.web.spec.css.parser.selectors.selector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import everyos.web.spec.css.QualifiedName;
import everyos.web.spec.css.parser.tokens.DelimToken;
import everyos.web.spec.css.parser.tokens.IdentToken;
import everyos.web.spec.css.parser.tokens.Token;
import everyos.web.spec.css.selectors.selector.TypeSelector;

public class TypeSelectorParserTest {

	private TypeSelectorParser parser;
	
	@BeforeEach
	public void beforeEach() {
		parser = new TypeSelectorParser();
	}
	
	@Test
	@DisplayName("Can parse element with simple name")
	public void canParseElementWithSimpleName() {
		IdentToken nameToken = Mockito.mock(IdentToken.class);
		Mockito.when(nameToken.getValue()).thenReturn("name");
		Token[] tokens = new Token[] { nameToken };
		TypeSelector selector = Assertions.assertDoesNotThrow(() -> parser.parse(tokens, 0, 1));
		QualifiedName name = selector.getQualifiedName();
		Assertions.assertEquals(QualifiedName.DEFAULT_NAMESPACE, name.getNamespace());
		Assertions.assertEquals("name", name.getName());
	}
	
	@Test
	@DisplayName("Can parse wildcard element")
	public void canParseWildcardElement() {
		DelimToken nameToken = Mockito.mock(DelimToken.class);
		Mockito.when(nameToken.getValue()).thenReturn((int) '*');
		Token[] tokens = new Token[] { nameToken };
		TypeSelector selector = Assertions.assertDoesNotThrow(() -> parser.parse(tokens, 0, 1));
		QualifiedName name = selector.getQualifiedName();
		Assertions.assertEquals(QualifiedName.DEFAULT_NAMESPACE, name.getNamespace());
		Assertions.assertEquals(QualifiedName.ANY_NAME, name.getName());
	}
	
	@Test
	@DisplayName("Can parse element without namespace")
	public void canParseElementWithoutNamespace() {
		DelimToken delimToken = Mockito.mock(DelimToken.class);
		Mockito.when(delimToken.getValue()).thenReturn((int) '|');
		IdentToken nameToken = Mockito.mock(IdentToken.class);
		Mockito.when(nameToken.getValue()).thenReturn("name");
		Token[] tokens = new Token[] { delimToken, nameToken };
		TypeSelector selector = Assertions.assertDoesNotThrow(() -> parser.parse(tokens, 0, 2));
		QualifiedName name = selector.getQualifiedName();
		Assertions.assertEquals(QualifiedName.NO_NAMESPACE, name.getNamespace());
		Assertions.assertEquals("name", name.getName());
	}
	
	@Test
	@DisplayName("Can parse element with namespace")
	public void canParseElementWithNamespace() {
		IdentToken namespaceToken = Mockito.mock(IdentToken.class);
		Mockito.when(namespaceToken.getValue()).thenReturn("namespace");
		DelimToken delimToken = Mockito.mock(DelimToken.class);
		Mockito.when(delimToken.getValue()).thenReturn((int) '|');
		IdentToken nameToken = Mockito.mock(IdentToken.class);
		Mockito.when(nameToken.getValue()).thenReturn("name");
		Token[] tokens = new Token[] { namespaceToken, delimToken, nameToken };
		TypeSelector selector = Assertions.assertDoesNotThrow(() -> parser.parse(tokens, 0, 3));
		QualifiedName name = selector.getQualifiedName();
		Assertions.assertEquals("namespace", name.getNamespace());
		Assertions.assertEquals("name", name.getName());
	}
	
	@Test
	@DisplayName("Can parse element with glob namespace")
	public void canParseElementWithGlobNamespace() {
		DelimToken namespaceToken = Mockito.mock(DelimToken.class);
		Mockito.when(namespaceToken.getValue()).thenReturn((int) '*');
		DelimToken delimToken = Mockito.mock(DelimToken.class);
		Mockito.when(delimToken.getValue()).thenReturn((int) '|');
		IdentToken nameToken = Mockito.mock(IdentToken.class);
		Mockito.when(nameToken.getValue()).thenReturn("name");
		Token[] tokens = new Token[] { namespaceToken, delimToken, nameToken };
		TypeSelector selector = Assertions.assertDoesNotThrow(() -> parser.parse(tokens, 0, 3));
		QualifiedName name = selector.getQualifiedName();
		Assertions.assertEquals(QualifiedName.ANY_NAMESPACE, name.getNamespace());
		Assertions.assertEquals("name", name.getName());
	}
	
}
