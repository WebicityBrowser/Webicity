package everyos.browser.webicity.webribbon.ui.webui;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.UIBox;
import everyos.browser.webicity.webribbon.gui.UIContext;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.browser.webicity.webribbon.ui.webui.appearence.Appearence;
import everyos.browser.webicity.webribbon.ui.webui.appearence.DefaultAppearence;
import everyos.browser.webicity.webribbon.ui.webui.layout.BlockLayout;
import everyos.browser.webicity.webribbon.ui.webui.layout.Layout;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Rectangle;

public class WebUIWebComponentUI implements WebComponentUI {
	private Layout layout;
	private Appearence appearence;
	private WebComponent component;
	private WebComponentUI parent;
	
	public WebUIWebComponentUI(WebComponent component, WebComponentUI parent) {
		this.component = component;
		this.parent = parent;
		
		this.layout = new BlockLayout(component, this);
		this.appearence = new DefaultAppearence();
	}
	
	@Override
	public void render(Renderer r, SizePosGroup sizepos, UIContext context) {
		getLayout().render(r, sizepos, context, getAppearence());
	}
	
	@Override
	public void paint(Renderer r, Rectangle viewport) {
		getLayout().paint(r, viewport, getAppearence());
	}
	
	@Override
	public WebComponentUI getParent() {
		return parent;
	}
	
	@Override
	public void invalidate(InvalidationLevel level) {
		
	}
	
	@Override
	public void invalidateLocal(InvalidationLevel level) {
		
	}
	
	@Override
	public void validateTo(InvalidationLevel level) {
		
	}
	
	@Override
	public boolean getValidated(InvalidationLevel level) {
		return false;
	}
	
	@Override
	public UIBox getUIBox() {
		return layout.getComputedUIBox();
	}
	
	@Override
	public void processEvent(UIEvent event) {	
		layout.processEvent(event);
		if (parent!=null) {
			parent.processEvent(event);
		}
	}
	
	protected WebComponent getComponent() {
		return this.component;
	}
	
	protected Appearence getAppearence() {
		return this.appearence;
	}
	
	protected Layout getLayout() {
		return this.layout;
	}
}
