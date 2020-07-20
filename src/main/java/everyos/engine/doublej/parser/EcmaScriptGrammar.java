package everyos.engine.doublej.parser;

class EcmaScriptGrammar extends EcmaGrammar {
	@Override public void parse(EcmaParser parser, EcmaParseState state) {
		EcmaGrammar grammar = new EcmaGrammarOptional(new EcmaScriptBodyGrammar()).parent(state.incoming);
		
		parser.paths.add(new EcmaParseState(state.finished, grammar, state.pos));
	}
}