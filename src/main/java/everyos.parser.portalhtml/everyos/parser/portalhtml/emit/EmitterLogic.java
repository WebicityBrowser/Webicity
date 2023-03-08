package everyos.parser.portalhtml.emit;

import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.Token;

public final class EmitterLogic {
	
	private EmitterLogic() {}

	public static void emit(HTMLParserContext context, Token token) {
		// TODO: Foreign elements
		context
			.getCurrentInsertionMode()
			.emit(token);;
	}

}
