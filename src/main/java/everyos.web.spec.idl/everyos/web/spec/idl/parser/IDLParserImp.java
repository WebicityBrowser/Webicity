package everyos.web.spec.idl.parser;

import java.util.ArrayList;
import java.util.List;

import everyos.web.spec.idl.IDLFragment;
import everyos.web.spec.idl.definition.Definition;
import everyos.web.spec.idl.definition.EnumDefinition;
import everyos.web.spec.idl.parser.tokens.EOFToken;
import everyos.web.spec.idl.parser.tokens.Token;

public class IDLParserImp implements IDLParser {

	@Override
	public IDLFragment parse(Token[] tokens) {
		TokenStream stream = new TokenStreamImp(tokens);
		List<Definition> definitions = parseDefinitions(stream);
		
		return createIDLSheet(definitions);
	}

	private List<Definition> parseDefinitions(TokenStream stream) {
		List<Definition> definitions = new ArrayList<>();
		
		if (!(stream.read() instanceof EOFToken)) {
			definitions.add(createEnumDefinition());
		}
		
		return definitions;
	}

	private EnumDefinition createEnumDefinition() {
		return () -> new String[0];
	}

	private IDLFragment createIDLSheet(List<Definition> definitionsList) {
		Definition[] definitions = definitionsList.toArray(new Definition[definitionsList.size()]);
		
		return new IDLFragment() {
			@Override
			public Definition[] getDefinitions() {
				return definitions;
			}
		};
	}

}
