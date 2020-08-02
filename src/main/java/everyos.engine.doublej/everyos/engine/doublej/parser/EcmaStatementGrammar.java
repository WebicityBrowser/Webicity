package everyos.engine.doublej.parser;

class EcmaStatementGrammar extends EcmaGrammar {
	private boolean yield;
	private boolean await;
	@SuppressWarnings("unused")
	private boolean ret;

	public EcmaStatementGrammar(boolean yield, boolean await, boolean ret) {
		this.yield = yield;
		this.await = await;
		this.ret = ret;
	}

	@Override public void parse(EcmaParser parser, EcmaParseState state) {
		//TODO: Other statements
		parser.paths.add(new EcmaParseState(state.finished, new EcmaVariableStatementGrammar(yield, await).parent(state.incoming), state.pos));
	}
}