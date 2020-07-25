package everyos.browser.webicity.renderer.html.dom.impl;

import java.util.ArrayList;

public class DocumentImpl extends NodeImpl {
	//Sidenote: https://github.com/whatwg/html/pull/3917 is a thing
	public static enum DocumentReadyState {LOADING, INTERACTIVE, COMPLETE}
	
	private ArrayList<MutationListener> mutationListeners = new ArrayList<>(); 
	
	public DocumentImpl() {
		super(null);
	}
	
	//Implementation Specific
	public void bind(MutationListener listener) {
		mutationListeners.add(listener);
		listener.onBind(this);
	}
	public void unbind(MutationListener listener) {
		mutationListeners.remove(listener);
	}
}
