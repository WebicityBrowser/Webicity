package everyos.parser.portalhtml.tree;

public interface HTMLElementLeaf extends HTMLLeaf {

	void appendAttribute(HTMLAttribute htmlAttribute);

	String getNamespace();

	String getLocalName();

}
