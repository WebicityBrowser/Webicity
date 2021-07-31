package everyos.browser.webicity.webribbon.ui.webui;

import java.util.function.Consumer;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.UIContext;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.browser.webicity.webribbon.ui.webui.appearence.Appearence;
import everyos.browser.webicity.webribbon.ui.webui.layout.InlineBlockLayout;
import everyos.browser.webicity.webribbon.ui.webui.layout.Layout;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Dimension;

public class WebUIWebWindowUI extends WebUIWebComponentUI {
	private WindowLayout layout;
	private Dimension windowSize;
	private Consumer<InvalidationLevel> onInvalidate;

	public WebUIWebWindowUI(WebComponent component, WebComponentUI parent) {
		super(component, parent);
		
		this.windowSize = new Dimension(0, 0);
		this.layout = new WindowLayout(component, this);
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
	
	private class WindowLayout extends InlineBlockLayout {
		public WindowLayout(WebComponent component, WebComponentUI ui) {
			super(component, ui);
		}
		
		@Override
		public Dimension getMaxBlockSize(SizePosGroup sizepos) {
			return windowSize;
		}
		
		@Override
		public void render(Renderer r, SizePosGroup sizepos, UIContext context, Appearence appearence) {
			if (sizepos.getCurrentPointer().getX()!=0) sizepos.nextLine();
			super.render(r, sizepos, context, appearence);
			if (sizepos.getCurrentPointer().getX()!=0) sizepos.nextLine();
		}
	}
}
