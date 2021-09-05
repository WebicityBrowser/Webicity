package everyos.browser.spec.javadom.intf;

public interface NodeList extends Iterable<Node> {
	long getLength();
	Node item(long index);
}
