package everyos.browser.webicity.renderer.html.dom;

import java.util.ArrayList;

public class Document extends Node {
	//Sidenote: https://github.com/whatwg/html/pull/3917 is a thing
	public static enum DocumentReadyState {LOADING, INTERACTIVE, COMPLETE}
	
	private ArrayList<MutationListener> mutationListeners = new ArrayList<>(); 
	
	public Document() {
		this.nodeDocument = this;
	}
	
	public void bind(MutationListener listener) {
		mutationListeners.add(listener);
		listener.onBind(this);
	}
	public void unbind(MutationListener listener) {
		mutationListeners.remove(listener);
	}
}
