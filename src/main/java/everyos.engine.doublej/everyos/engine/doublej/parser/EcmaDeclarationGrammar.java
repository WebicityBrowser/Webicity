package everyos.engine.doublej.parser;

class EcmaDeclarationGrammar extends EcmaGrammar {
	@SuppressWarnings("unused")
	private boolean yield;
	@SuppressWarnings("unused")
	private boolean await;

	public EcmaDeclarationGrammar(boolean yield, boolean await) {
		this.yield = yield;
		this.await = await;
	}

	@Override public void parse(EcmaParser parser, EcmaParseState state) {
		//TODO
	}
}