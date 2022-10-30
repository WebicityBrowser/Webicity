package everyos.web.spec.css.parser.tokens;

public interface NumberToken extends Token {

	Number getValue();

	NumberTypeFlag getTypeFlag();
	
}
