package everyos.browser.webicity.webribbon.ui.webui.display.outer;

import everyos.browser.spec.jcss.cssom.ApplicablePropertyMap;
import everyos.browser.spec.jcss.cssom.CSSOMNode;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.gui.Content;
import everyos.browser.webicity.webribbon.gui.WebBoxContext;
import everyos.browser.webicity.webribbon.gui.box.stage.BoxingStageBox;
import everyos.browser.webicity.webribbon.gui.box.stage.MultiBox;
import everyos.browser.webicity.webribbon.ui.webui.rendering.box.InlineLevelBoxImp;

public class InlineDisplayMode implements DisplayMode {
	
	private final DisplayMode innerDisplay;
	private final Content content;
	
	//TODO: Should this be here?
	private ApplicablePropertyMap properties;
	
	public InlineDisplayMode(DisplayMode innerDisplay, Content content) {
		this.innerDisplay = innerDisplay;
		this.content = content;
	}

	@Override
	public void recalculateCSSOM(CSSOMNode cssomNode, ApplicablePropertyMap properties, WebUIManager manager) {
		this.properties = properties;
		innerDisplay.recalculateCSSOM(cssomNode, properties, manager);
	}

	@Override
	public void box(BoxingStageBox parent, WebBoxContext context) {	
		MultiBox box = new InlineLevelBoxImp(parent);
		box.setContent(content);
		box.setProperties(properties);
		innerDisplay.box(box, context);
		box.finish();
	}

	@Override
	public Content getContent() {
		return this.content;
	}

}
