package everyos.browser.webicity.webribbon.ui.webui.ui;

import java.util.function.Consumer;

import everyos.browser.spec.jcss.cssom.ApplicablePropertyMap;
import everyos.browser.spec.jcss.cssom.CSSOMNode;
import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.core.ui.WebWindowUI;
import everyos.browser.webicity.webribbon.gui.WebBoxContext;
import everyos.browser.webicity.webribbon.gui.box.stage.BoxingStageBox;
import everyos.browser.webicity.webribbon.gui.box.stage.MultiBox;
import everyos.browser.webicity.webribbon.ui.webui.display.inner.flow.FlowContent;
import everyos.browser.webicity.webribbon.ui.webui.display.inner.flow.FlowDisplayMode;
import everyos.browser.webicity.webribbon.ui.webui.display.outer.DisplayMode;
import everyos.browser.webicity.webribbon.ui.webui.psuedo.ScrollBarContent;
import everyos.browser.webicity.webribbon.ui.webui.rendering.box.BlockLevelBoxImp;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;

public class WebUIWebWindowUI extends WebUIWebComponentUIBase implements WebWindowUI {

	private final DisplayMode displayMode;

	private Consumer<InvalidationLevel> invalidationFunc;

	public WebUIWebWindowUI(WebComponent component, WebComponentUI parent) {
		super(component, parent);
		
		displayMode = new FlowDisplayMode(component, this);
	}
	
	@Override
	public void recalculateCSSOM(CSSOMNode cssomNode, ApplicablePropertyMap parent, WebUIManager manager) {
		displayMode.recalculateCSSOM(cssomNode, parent, manager);
	}
	
	@Override
	public void box(BoxingStageBox parent, WebBoxContext context) {
		displayMode.box(parent, context);
		parent.finish();
	}

	@Override
	public MultiBox createBox() {
		//TODO: Does the scrollbar go here?
		MultiBox box = new BlockLevelBoxImp(null);
		box.setContent(new ScrollBarContent(this, new FlowContent()));
		return box;
	}
	
	@Override
	public void invalidateLocal(InvalidationLevel level) {
		super.invalidateLocal(level);
		
		invalidationFunc.accept(level);
	}

	@Override
	public void onInvalidation(Consumer<InvalidationLevel> func) {
		this.invalidationFunc = func;
	}
	
}
