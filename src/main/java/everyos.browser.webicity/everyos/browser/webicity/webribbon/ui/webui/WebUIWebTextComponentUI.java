package everyos.browser.webicity.webribbon.ui.webui;

import java.util.ArrayList;

import everyos.browser.javadom.intf.Text;
import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.gui.shape.Position;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.browser.webicity.webribbon.ui.webui.helper.StringWrapHelper;
import everyos.engine.ribbon.core.rendering.Renderer;

public class WebUIWebTextComponentUI extends WebUIWebComponentUI {
	private ArrayList<String> lines;
	private Position position;
	
	public WebUIWebTextComponentUI(WebComponent component, WebComponentUI parent) {
		super(component, parent);
	}
	
	@Override public void render(Renderer r, SizePosGroup sizepos, WebUIManager uimgr) {
		//node.getParent().component.attributes.get("word-wrap");
		//calculateCascade();
		String text = ((Text) getComponent().getNode()).getWholeText();
		
		//setRenderingData(r);
		this.position = sizepos.pointer();
		this.lines = StringWrapHelper.calculateString(text, r, sizepos, false);
	}
	
	@Override public void paintUI(Renderer r) {
		//setRenderingData(r);
		r.useForeground();
		for (int i=0; i<lines.size(); i++) {
			int py = i*r.getFontHeight();
			//System.out.println("py:"+py);
			r.drawText(i==0?position.x:0, position.y+py, lines.get(i));
		}
	}
}
