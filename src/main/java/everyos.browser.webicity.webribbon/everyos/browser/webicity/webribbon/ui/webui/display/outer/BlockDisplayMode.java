package everyos.browser.webicity.webribbon.ui.webui.display.outer;

import everyos.browser.spec.jcss.cssom.ApplicablePropertyMap;
import everyos.browser.spec.jcss.cssom.CSSOMNode;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.gui.Content;
import everyos.browser.webicity.webribbon.gui.WebBoxContext;
import everyos.browser.webicity.webribbon.gui.box.stage.BoxingStageBox;
import everyos.browser.webicity.webribbon.gui.box.stage.MultiBox;
import everyos.browser.webicity.webribbon.ui.webui.psuedo.ScrollBarContent;
import everyos.browser.webicity.webribbon.ui.webui.rendering.box.BlockLevelBoxImp;

public class BlockDisplayMode implements DisplayMode {
	
	private final WebComponentUI ui;
	private final DisplayMode innerDisplay;
	private final Content content;
	
	//TODO: Should this be here?
	private ApplicablePropertyMap properties;

	public BlockDisplayMode(WebComponentUI ui, DisplayMode innerDisplay, Content content) {
		this.ui = ui;
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
		MultiBox box = new BlockLevelBoxImp(parent);
		box.setContent(new ScrollBarContent(ui, this.content));
		box.setProperties(properties);
		innerDisplay.box(box, context);
		box.finish();
	}

	@Override
	public Content getContent() {
		//TODO
		return this.content;
	}

}
