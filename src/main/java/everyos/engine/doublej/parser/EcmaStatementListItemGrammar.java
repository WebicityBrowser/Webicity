package everyos.engine.doublej.parser;

class EcmaStatementListItemGrammar extends EcmaGrammar {
	private boolean yield;
	private boolean await;
	private boolean ret;

	public EcmaStatementListItemGrammar(boolean yield, boolean await, boolean ret) {
		this.yield = yield;
		this.await = await;
		this.ret = ret;
	}

	@Override public void parse(EcmaParser parser, EcmaParseState state) {
		parser.paths.add(new EcmaParseState(state.finished, new EcmaStatementGrammar(yield, await, ret).parent(state.incoming), state.pos));
		parser.paths.add(new EcmaParseState(state.finished, new EcmaDeclarationGrammar(yield, await).parent(state.incoming), state.pos));
	}
}