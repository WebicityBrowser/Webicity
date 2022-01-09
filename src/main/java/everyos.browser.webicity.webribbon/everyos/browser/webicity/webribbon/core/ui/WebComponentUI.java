package everyos.browser.webicity.webribbon.core.ui;

import everyos.browser.spec.jcss.cssom.ApplicablePropertyMap;
import everyos.browser.spec.jcss.cssom.CSSOMNode;
import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.gui.WebBoxContext;
import everyos.browser.webicity.webribbon.gui.box.stage.BoxingStageBox;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;

public interface WebComponentUI {
	
	//TODO: Avoid StackOverflow exceptions if too many UIs are nested
	// (The exception, not the site)
	
	void recalculateCSSOM(CSSOMNode cssomNode, ApplicablePropertyMap parent, WebUIManager manager);
	void box(BoxingStageBox parent, WebBoxContext context);
	
	WebComponentUI getParent();
	
	void invalidate(InvalidationLevel level);
	void invalidateLocal(InvalidationLevel level);
	void validateTo(InvalidationLevel level);
	boolean getValidated(InvalidationLevel level);
	
	WebComponent getComponent();

}
