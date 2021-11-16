package everyos.browser.spec.jhtml.imp;

import everyos.browser.spec.javadom.intf.inf.ActivationBehavior;
import everyos.browser.spec.jhtml.parser.ElementFactory;

public class JHTMLAnchorElement extends JHTMLElement {

	public JHTMLAnchorElement(ElementFactory factory) {
		super(factory);
	}

	@Override
	public ActivationBehavior getActivationBehavior() {
		return context -> {
			String href = getAttribute("href");
			if (href == null) {
				return;
			}
			
			//TODO: Follow the hyperlink steps; also image processing
			context.navigate(href);
		};
	}
	
}
