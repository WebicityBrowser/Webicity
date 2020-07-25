package everyos.browser.webicity.renderer.html.dom.impl;

import everyos.browser.webicity.renderer.html.dom.Node;

public interface MutationListener {
	public void onBind(Node el);
	public void onChildrenMutate(Node el, MutationType type);
	public void onNodeMutate(Node el);
	public void onUnbind(Node el);
	
	public static enum MutationType {INSERT, DELETE, SWAP}
}
