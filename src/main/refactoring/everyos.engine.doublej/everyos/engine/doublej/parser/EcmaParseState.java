package everyos.engine.doublej.parser;

class EcmaParseState {
	public EcmaGrammar finished;
	public EcmaGrammar incoming;
	public int pos;

	public EcmaParseState(EcmaGrammar finished, EcmaGrammar incoming, int pos) {
		this.finished = finished;
		this.incoming = incoming;
		this.pos = pos;
	}
}