package everyos.browser.webicity.webribbon.ui.webui;

import java.util.function.Consumer;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.ui.webui.appearence.Appearence;
import everyos.browser.webicity.webribbon.ui.webui.layout.InlineBlockLayout;
import everyos.browser.webicity.webribbon.ui.webui.layout.Layout;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.SizePosGroup;

public class WebUIWebWindowUI extends WebUIWebComponentUI {
	private final WindowLayout layout;
	
	private Dimension windowSize;
	private Consumer<InvalidationLevel> onInvalidate;

	public WebUIWebWindowUI(WebComponent component, WebComponentUI parent) {
		super(component, parent);
		
		this.layout = new WindowLayout(component, this);
		this.windowSize = new Dimension(0, 0);
	}
	
	public void setWindowSize(Dimension windowSize) {
		this.windowSize = windowSize;
		invalidate(InvalidationLevel.RENDER);
	}
	
	public void onInvalidation(Consumer<InvalidationLevel> onInvalidate) {
		this.onInvalidate = onInvalidate;
	}
	
	@Override
	public void invalidateLocal(InvalidationLevel level) {
		super.invalidateLocal(level);
		if (onInvalidate != null) {
			onInvalidate.accept(level);
		}
	}
	
	@Override
	protected Layout getLayout() {
		return this.layout;
	}
	
	//TODO: Should not be inline
	private class WindowLayout extends InlineBlockLayout {
		public WindowLayout(WebComponent component, WebComponentUI ui) {
			super(component, ui);
		}
		
		@Override
		public Dimension getMaxBlockSize(SizePosGroup sizepos) {
			return windowSize;
		}
		
		@Override
		public void render(RendererData rd, SizePosGroup sizepos, WebRenderContext context, Appearence appearence) {
			if (sizepos.getCurrentPointer().getX()!=0) {
				sizepos.nextLine();
			}
			super.render(rd, sizepos, context, appearence);
			if (sizepos.getCurrentPointer().getX()!=0) {
				sizepos.nextLine();
			}
		}
	}
}
