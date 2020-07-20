package everyos.engine.doublej.parser;

class EcmaGrammarExit extends EcmaGrammar {
	@Override public void parse(EcmaParser parser, EcmaParseState state) {
		parser.paths.add(new EcmaParseState(state.finished, state.incoming, state.pos)); //Just skip
	}
}