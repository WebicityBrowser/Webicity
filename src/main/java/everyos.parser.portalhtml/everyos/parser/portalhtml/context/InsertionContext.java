package everyos.parser.portalhtml.context;

import java.util.Stack;

import everyos.parser.portalhtml.emit.InsertionMode;
import everyos.parser.portalhtml.tokenize.TokenizeState;
import everyos.parser.portalhtml.tree.HTMLLeaf;

public class InsertionContext {


	private final Stack<HTMLLeaf> openElementStack = new Stack<>();
	
	private HTMLLeaf headPointer;
	private TokenizeState nextTokenizeStateOverride;
	private InsertionMode originalInsertionMode;
	private boolean framesetOk;
	
	public void setHeadElementPointer(HTMLLeaf leave) {
		this.headPointer = leave;
	}
	
	public HTMLLeaf getHeadElementPointer() {
		return this.headPointer;
	}
	
	public void setNextTokenizeStateOverride(TokenizeState state) {
		this.nextTokenizeStateOverride = state;
	}
	
	public TokenizeState getNextTokenizeStateOverride() {
		return this.nextTokenizeStateOverride;
	}
	
	public void setOriginalInsertionMode(InsertionMode mode) {
		this.originalInsertionMode = mode;
	}
	
	public InsertionMode getOriginalInsertionMode() {
		return this.originalInsertionMode;
	}
	
	public void setFramesetOk(boolean ok) {
		this.framesetOk = ok;
	}
	
	public boolean getFramesetOk() {
		return this.framesetOk;
	}
	
	public Stack<HTMLLeaf> getOpenElementStack() {
		return this.openElementStack;
	}
	
	public boolean fosterParentingEnabled() {
		return false;
	}
	
}
