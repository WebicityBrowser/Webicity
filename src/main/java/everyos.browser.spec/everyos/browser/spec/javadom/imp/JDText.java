package everyos.browser.spec.javadom.imp;

import java.util.Collections;
import java.util.List;

import everyos.browser.spec.javadom.intf.Document;
import everyos.browser.spec.javadom.intf.Node;
import everyos.browser.spec.javadom.intf.Text;

public class JDText extends JDCharacterData implements Text {

	public JDText(Document nodeDocument) {
		super(nodeDocument);
	}

	@Override
	public String getWholeText() {
		return getData();
	}

	@Override
	protected List<Node> createChildrenList() {
		return Collections.emptyList();
	}
}
