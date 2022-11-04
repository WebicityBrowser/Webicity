package everyos.web.spec.idl.parser;

import everyos.web.spec.idl.IDLFragment;
import everyos.web.spec.idl.parser.tokens.Token;

public interface IDLParser {

	IDLFragment parse(Token[] tokens);

}
