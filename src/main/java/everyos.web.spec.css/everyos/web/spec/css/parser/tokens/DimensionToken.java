package everyos.web.spec.css.parser.tokens;

public interface DimensionToken extends Token {

	Number getValue();

	NumberTypeFlag getTypeFlag();
	
	String getUnit();
	
}
