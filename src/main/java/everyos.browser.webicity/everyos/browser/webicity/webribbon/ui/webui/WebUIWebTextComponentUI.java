package everyos.browser.webicity.webribbon.ui.webui;

import java.util.List;

import everyos.browser.javadom.intf.Text;
import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.UIContext;
import everyos.browser.webicity.webribbon.gui.shape.Position;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.browser.webicity.webribbon.ui.webui.appearence.Appearence;
import everyos.browser.webicity.webribbon.ui.webui.helper.StringWrapHelper;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Rectangle;

public class WebUIWebTextComponentUI extends WebUIWebComponentUI {
	private List<String> lines;
	private Position position;
	private TextAppearence appearence;
	
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
		public void render(Renderer r, SizePosGroup sizepos, UIContext context) {
			String text = ((Text) getComponent().getNode()).getWholeText();
			//TODO: http://finance.yahoo.com/news/study-reveals-city-worst-traffic-223420982.html cuts off first letter (at parser level)

			position = sizepos.getCurrentPointer();
			lines = new StringWrapHelper().calculateString(text, r, sizepos, false);
		}
		
		@Override
		public void paint(Renderer r, Rectangle viewport) {
			r.useForeground();
			for (int i=0; i<lines.size(); i++) {
				int py = i*(r.getFontHeight()+r.getFontPaddingHeight());
				int width = r.drawText(i==0?position.getX():0, position.getY()+py, lines.get(i));
				r.paintMouseListener(getComponent(), position.getX(), position.getY()+py, width, r.getFontHeight(), e->{
					processEvent(e);
				});
			}
		}
	}
}
