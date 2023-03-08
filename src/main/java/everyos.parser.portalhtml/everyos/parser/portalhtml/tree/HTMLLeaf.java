package everyos.parser.portalhtml.tree;

public interface HTMLLeaf {
	
	int getNumChildren();

	void append(HTMLLeaf leaf);

	void insert(int index, HTMLLeaf leaf);

	HTMLDocumentLeaf getNodeDocument();

	boolean canInsert(HTMLLeaf leaf);

	HTMLLeaf getLeaf(int i);

}
