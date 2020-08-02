package everyos.engine.doublej.parser;

class EcmaGrammarCompletion extends EcmaGrammar {
	@Override public void parse(EcmaParser parser, EcmaParseState state) {
		//if (!(state.pos==parser.content.length())) return;
		parser.completions.add(state.incoming);
		System.out.println("YAY!");
	}
}