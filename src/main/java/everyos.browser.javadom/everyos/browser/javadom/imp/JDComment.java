package everyos.browser.javadom.imp;

import everyos.browser.javadom.intf.Comment;
import everyos.browser.javadom.intf.Document;

public class JDComment extends JDCharacterData implements Comment {

	public JDComment(Document nodeDocument, String data) {
		super(nodeDocument);
	}

}
