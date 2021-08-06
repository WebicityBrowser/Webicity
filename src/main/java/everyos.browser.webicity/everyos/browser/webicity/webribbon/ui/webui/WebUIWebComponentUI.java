package everyos.browser.webicity.webribbon.ui.webui;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.UIBox;
import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.browser.webicity.webribbon.ui.webui.appearence.Appearence;
import everyos.browser.webicity.webribbon.ui.webui.appearence.DefaultAppearence;
import everyos.browser.webicity.webribbon.ui.webui.layout.InlineLayout;
import everyos.browser.webicity.webribbon.ui.webui.layout.Layout;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;

public class WebUIWebComponentUI implements WebComponentUI {
	private Layout layout;
	private Appearence appearence;
	private WebComponent component;
	private WebComponentUI parent;
	private InvalidationLevel invalidated = InvalidationLevel.IGNORE;
	
	public WebUIWebComponentUI(WebComponent component, WebComponentUI parent) {
		this.component = component;
		this.parent = parent;
		
		this.layout = new InlineLayout(component, this);
		this.appearence = new DefaultAppearence();
	}
	
	@Override
	public void render(RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
		getLayout().render(rd, sizepos, context, getAppearence());
	}
	
	@Override
	public void paint(RendererData rd, Rectangle viewport, WebPaintContext context) {
		getLayout().paint(rd, viewport, context, getAppearence());
	}
	
	@Override
	public WebComponentUI getParent() {
		return parent;
	}
	
	@Override
	public void invalidate(InvalidationLevel level) {
		WebComponentUI cui = this;
		while (cui!=null) {
			if (!cui.getValidated(level)) return;
			cui.invalidateLocal(level);
			cui = cui.getParent();
		}
	}
	
	@Override
	public void invalidateLocal(InvalidationLevel level) {
		if (this.invalidated.lessThan(level)) {
			this.invalidated = level;
		}
	}
	
	@Override
	public void validateTo(InvalidationLevel level) {
		this.invalidated = level;
	}
	
	@Override
	public boolean getValidated(InvalidationLevel reference) {
		return reference.lessThan(this.invalidated);
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
	
	@Override
	public WebComponent getComponent() {
		return this.component;
	}
	
	protected Appearence getAppearence() {
		return this.appearence;
	}
	
	protected Layout getLayout() {
		return this.layout;
	}
}
