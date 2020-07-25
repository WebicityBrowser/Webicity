package everyos.browser.webicity.renderer.html.dom;

public interface HTMLCollection {
	long getLength();
	Element item(long index);
	Element namedItem(String name);
}
