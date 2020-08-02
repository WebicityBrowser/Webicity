package everyos.browser.webicity.renderer.html.dom;

public interface NodeList {
	Node item(long index);
	long getLength();
	//TODO: Iterator?
}
