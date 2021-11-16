package everyos.browser.webicity.webribbon.ui.webui.ui;

import java.util.function.Consumer;

import everyos.browser.spec.jcss.cssom.ApplicablePropertyMap;
import everyos.browser.spec.jcss.cssom.CSSOMNode;
import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.core.ui.WebWindowUI;
import everyos.browser.webicity.webribbon.gui.WebBoxContext;
import everyos.browser.webicity.webribbon.gui.box.MutableBlockLevelBox;
import everyos.browser.webicity.webribbon.gui.box.MutableBox;
import everyos.browser.webicity.webribbon.ui.webui.display.inner.flow.FlowContent;
import everyos.browser.webicity.webribbon.ui.webui.display.outer.ContentsDisplayMode;
import everyos.browser.webicity.webribbon.ui.webui.display.outer.DisplayMode;
import everyos.browser.webicity.webribbon.ui.webui.psuedo.ScrollBarContent;
import everyos.browser.webicity.webribbon.ui.webui.rendering.box.BlockLevelBoxImp;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;

public class WebUIWebWindowUI extends WebUIWebComponentUIBase implements WebWindowUI {

	private final DisplayMode displayMode;

	private Consumer<InvalidationLevel> invalidationFunc;

	public WebUIWebWindowUI(WebComponent component, WebComponentUI parent) {
		super(component, parent);
		
		displayMode = new ContentsDisplayMode(component, this);
	}
	
	@Override
	public void recalculateCSSOM(CSSOMNode cssomNode, ApplicablePropertyMap parent, WebUIManager manager) {
		displayMode.recalculateCSSOM(cssomNode, parent, manager);
	}
	
	@Override
	public void box(MutableBox parent, WebBoxContext context) {
		displayMode.box(parent, context);
		parent.finish();
	}

	@Override
	public MutableBlockLevelBox createBox() {
		//TODO: Does the scrollbar go here?
		return new BlockLevelBoxImp(null, new ScrollBarContent(this, new FlowContent()));
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
