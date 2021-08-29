package everyos.browser.webicity.webribbon.ui.webui;

import java.util.List;

import everyos.browser.javadom.intf.Text;
import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.ui.webui.appearence.Appearence;
import everyos.browser.webicity.webribbon.ui.webui.helper.StringWrapHelper;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.rendering.RibbonFont;
import everyos.engine.ribbon.core.shape.Position;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;

public class WebUIWebTextComponentUI extends WebUIWebComponentUI {
	private final Appearence appearence;
	
	private List<String> lines;
	private Position position;
	
	public WebUIWebTextComponentUI(WebComponent component, WebComponentUI parent) {
		super(component, parent);
		
		this.appearence = new TextAppearence();
	}
	
	@Override
	protected Appearence getAppearence() {
		return this.appearence;
	}
	
	private class TextAppearence implements Appearence {
		@Override
		public void render(RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
			String text = ((Text) getComponent().getNode()).getWholeText();

			position = sizepos.getCurrentPointer();
			lines = new StringWrapHelper().calculateString(text, rd.getState().getFont(), sizepos, false);
		}
		
		@Override
		public void paint(RendererData rd, Rectangle viewport, WebPaintContext context) {
			RibbonFont font = rd.getState().getFont();
			Renderer r = context.getRenderer();
			
			rd.useForeground();
			for (int i=0; i < lines.size(); i++) {
				int py = i*(font.getHeight()+font.getPaddingHeight());
				int width = r.drawText(rd, i==0?position.getX():0, position.getY()+py, lines.get(i));
				r.paintMouseListener(rd, getComponent(), position.getX(), position.getY()+py, width, font.getHeight(), e->{
					processEvent(e);
				});
			}
		}
	}
}
