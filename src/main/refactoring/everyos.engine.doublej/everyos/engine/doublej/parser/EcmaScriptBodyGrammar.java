package everyos.engine.doublej.parser;

class EcmaScriptBodyGrammar extends EcmaGrammar {
	@Override public void parse(EcmaParser parser, EcmaParseState state) {
		parser.paths.add(new EcmaParseState(state.finished,
			new EcmaStatementListGrammar(false, false, false).parent(state.incoming), state.pos));
	}
}