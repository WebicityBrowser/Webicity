package everyos.engine.doublej.parser;

class EcmaGrammarOptional extends EcmaGrammar {
	private EcmaGrammar element;
	public EcmaGrammarOptional(EcmaGrammar optional) {
		this.element = optional;
	}
	@Override public void parse(EcmaParser parser, EcmaParseState state) {
		element.parent = state.incoming;
		parser.paths.add(new EcmaParseState(state.finished, element, state.pos));
		parser.paths.add(new EcmaParseState(state.finished, state.incoming, state.pos));
	}
}