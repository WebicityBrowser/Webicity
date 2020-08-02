package everyos.browser.webicity.webribbon.component;

import java.util.ArrayList;

import everyos.browser.webicity.renderer.html.dom.impl.TextNodeImpl;
import everyos.browser.webicity.webribbon.misc.DrawData;
import everyos.browser.webicity.webribbon.shape.Position;
import everyos.browser.webicity.webribbon.shape.SizePosGroup;
import everyos.browser.webicity.webribbon.ui.webui.helper.StringWrapHelper;
import everyos.engine.ribbon.renderer.guirenderer.GUIRenderer;
import everyos.engine.ribbon.renderer.guirenderer.graphics.Color;
import everyos.engine.ribbon.renderer.guirenderer.graphics.FontStyle;

public class WebTextComponent extends WebComponent {
	
	private ArrayList<String> lines;
	private Position position;

	public WebTextComponent(TextNodeImpl node) {
		super(node);
	}
	
	@Override public void render(GUIRenderer r, SizePosGroup sizepos) {
		//node.getParent().component.attributes.get("word-wrap");
		calculateCascade();
		String text = ((TextNodeImpl) node).wholeText.toString();
		
		setRenderingData(r);
		this.position = sizepos.pointer();
		this.lines = StringWrapHelper.calculateString(text, r, sizepos, false);
	}
	
	@Override public void paint(GUIRenderer r, DrawData d) {
		Color dcolor = (Color) resolveAttribute("color", Color.BLACK);
		
		setRenderingData(r);
		r.setColor(dcolor);
		for (int i=0; i<lines.size(); i++) {
			int py = i*r.getFontHeight();
			//System.out.println("py:"+py);
			r.drawText(i==0?position.x:0, position.y+py, lines.get(i));
		}
	}
	
	protected void setRenderingData(GUIRenderer r) {
		r.setFont("Arial", FontStyle.PLAIN, 12);
	}
}
