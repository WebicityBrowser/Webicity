package everyos.browser.webicity.webribbon.ui.webui;

import java.util.List;

import everyos.browser.javadom.intf.Text;
import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.UIContext;
import everyos.browser.webicity.webribbon.gui.shape.Position;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.browser.webicity.webribbon.ui.webui.helper.StringWrapHelper;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.renderer.guirenderer.shape.Rectangle;

public class WebUIWebTextComponentUI extends WebUIWebComponentUI {
	private List<String> lines;
	private Position position;
	
	public WebUIWebTextComponentUI(WebComponent component, WebComponentUI parent) {
		super(component, parent);
	}
	
	@Override
	public void render(Renderer r, SizePosGroup sizepos, UIContext context) {
		//node.getParent().component.attributes.get("word-wrap");
		//calculateCascade();
		String text = ((Text) getComponent().getNode()).getWholeText();
		
		//setRenderingData(r);
		this.position = sizepos.pointer();
		long time = System.currentTimeMillis();
		this.lines = StringWrapHelper.calculateString(text, r, sizepos, false);
		long fin = System.currentTimeMillis()-time;
		if (fin>100) {
			System.out.println(fin);
			System.out.println(text);
		}
		
		Rectangle bounds = new Rectangle(
			sizepos.pointer.x, sizepos.pointer.y,
			sizepos.size.width, sizepos.pointer.y+lines.size()*r.getFontHeight()+r.getFontPaddingHeight());
		
		setUIBox(viewport->viewport.intersects(bounds));
	}
	
	@Override
	protected void paintUI(Renderer r, Rectangle viewport) {
		//setRenderingData(r);
		r.useForeground();
		for (int i=0; i<lines.size(); i++) {
			int py = i*r.getFontHeight();
			int width = r.drawText(i==0?position.x:0, position.y+py, lines.get(i));
			r.paintMouseListener(getComponent(), position.x, position.y+py, width, r.getFontHeight(), e->{
				if (e.isExternal()) return;
				processEvent(e);
			});
		}
	}
}
