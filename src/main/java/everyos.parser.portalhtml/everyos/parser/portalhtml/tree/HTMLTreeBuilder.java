package everyos.parser.portalhtml.tree;

public interface HTMLTreeBuilder {

	HTMLDocumentLeaf getDocument();

	HTMLDocumentTypeLeaf createDocumentType(HTMLDocumentLeaf nodeDocument, String name, String publicId, String systemId);
	
	HTMLTextLeaf createText(HTMLDocumentLeaf nodeDocument, int ch);

	HTMLElementLeaf createElement(
		HTMLDocumentLeaf document, String localName, String namespace,
		String prefix, String is, boolean synchornousCustomElementsFlag);

	HTMLCommentLeaf createComment(HTMLDocumentLeaf nodeDocument, String value);
	
}
