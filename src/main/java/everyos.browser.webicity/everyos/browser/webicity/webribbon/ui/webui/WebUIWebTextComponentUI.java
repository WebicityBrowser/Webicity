package everyos.browser.webicity.webribbon.ui.webui;

import java.util.ArrayList;

import everyos.browser.javadom.intf.Text;
import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.gui.GUIWebComponentUI;
import everyos.browser.webicity.webribbon.gui.shape.Position;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.browser.webicity.webribbon.ui.webui.helper.StringWrapHelper;
import everyos.engine.ribbon.renderer.guirenderer.GUIRenderer;

public class WebUIWebTextComponentUI extends WebUIWebComponentUI {
	public WebUIWebTextComponentUI() {}
	@Override public WebComponentUI create(WebComponent component, WebComponentUI parent) {
		return new WebUIWebTextComponentUI(component, (GUIWebComponentUI) parent);
	}
	
	private ArrayList<String> lines;
	private Position position;
	
	public WebUIWebTextComponentUI(WebComponent component, GUIWebComponentUI parent) {
		super(component, parent);
	}
	
	@Override public void render(GUIRenderer r, SizePosGroup sizepos, WebUIManager<GUIWebComponentUI> uimgr) {
		//node.getParent().component.attributes.get("word-wrap");
		//calculateCascade();
		String text = ((Text) getComponent().getNode()).getWholeText();
		
		//setRenderingData(r);
		this.position = sizepos.pointer();
		this.lines = StringWrapHelper.calculateString(text, r, sizepos, false);
	}
	
	@Override public void paintUI(GUIRenderer r) {
		//setRenderingData(r);
		r.useForeground();
		for (int i=0; i<lines.size(); i++) {
			int py = i*r.getFontHeight();
			//System.out.println("py:"+py);
			r.drawText(i==0?position.x:0, position.y+py, lines.get(i));
		}
	}
}
