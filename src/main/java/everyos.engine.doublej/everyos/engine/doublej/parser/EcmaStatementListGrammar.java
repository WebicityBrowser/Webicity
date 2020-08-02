package everyos.engine.doublej.parser;

class EcmaStatementListGrammar extends EcmaGrammar {
	private boolean yield;
	private boolean await;
	private boolean ret;

	public EcmaStatementListGrammar(boolean yield, boolean await, boolean ret) {
		this.yield = yield;
		this.await = await;
		this.ret = ret;
	}

	@Override public void parse(EcmaParser parser, EcmaParseState state) {
		parser.paths.add(new EcmaParseState(state.finished,
			new EcmaGrammarOptional(
				new EcmaStatementListGrammar(yield, await, ret))
			.parent(
				new EcmaStatementListItemGrammar(yield, await, ret)), state.pos));
	}
}