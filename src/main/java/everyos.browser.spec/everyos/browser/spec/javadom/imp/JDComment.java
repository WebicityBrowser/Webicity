package everyos.browser.spec.javadom.imp;

import everyos.browser.spec.javadom.intf.Comment;
import everyos.browser.spec.javadom.intf.Document;

public class JDComment extends JDCharacterData implements Comment {

	public JDComment(Document nodeDocument, String data) {
		super(nodeDocument);
	}

}
