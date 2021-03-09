package everyos.browser.javadom.imp;

import everyos.browser.javadom.intf.Document;
import everyos.browser.javadom.intf.Text;

public class JDText extends JDCharacterData implements Text {

	public JDText(Document nodeDocument) {
		super(nodeDocument);
	}

	@Override
	public String getWholeText() {
		return getData();
	}

}
