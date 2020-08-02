package everyos.engine.doublej.parser;

class EcmaVariableStatementGrammar extends EcmaGrammar {
	@SuppressWarnings("unused")
	private boolean yield;
	@SuppressWarnings("unused")
	private boolean await;

	public EcmaVariableStatementGrammar(boolean yield, boolean await) {
		this.yield = yield;
		this.await = await;
	}

	@Override public void parse(EcmaParser parser, EcmaParseState state) {
		//if (parser.content.substring(state.pos, state.pos+5).equals("var "))
	}
}