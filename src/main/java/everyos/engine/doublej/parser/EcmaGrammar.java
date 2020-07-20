package everyos.engine.doublej.parser;

abstract class EcmaGrammar { //TODO: Use EcmaGrammarExit
	public EcmaGrammar parent;
	public boolean allowLineTerminator = true;
	public abstract void parse(EcmaParser parser, EcmaParseState state);
	
	public EcmaGrammar parent(EcmaGrammar parent) {
		this.parent = parent;
		return this;
	}
	public void disallowLineTerminator() {
		allowLineTerminator = false;
	}
}