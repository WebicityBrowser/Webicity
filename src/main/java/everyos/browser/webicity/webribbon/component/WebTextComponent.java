package everyos.browser.webicity.webribbon.component;

import java.util.ArrayList;

import everyos.browser.webicity.renderer.html.dom.TextNode;
import everyos.browser.webicity.webribbon.misc.DrawData;
import everyos.browser.webicity.webribbon.shape.Position;
import everyos.browser.webicity.webribbon.shape.SizePosGroup;
import everyos.browser.webicity.webribbon.ui.webui.helper.StringWrapHelper;
import everyos.engine.ribbon.graphics.Color;
import everyos.engine.ribbon.graphics.FontStyle;
import everyos.engine.ribbon.graphics.Renderer;

public class WebTextComponent extends WebComponent {
	
	private ArrayList<String> lines;
	private Position position;

	public WebTextComponent(TextNode node) {
		super(node);
	}
	
	@Override public void render(Renderer r, SizePosGroup sizepos) {
		//node.getParent().component.attributes.get("word-wrap");
		calculateCascade();
		String text = ((TextNode) node).wholeText.toString();
		
		setRenderingData(r);
		this.position = sizepos.pointer();
		this.lines = StringWrapHelper.calculateString(text, r, sizepos, false);
	}
	
	@Override public void paint(Renderer r, DrawData d) {
		Color dcolor = (Color) resolveAttribute("color", Color.BLACK);
		
		setRenderingData(r);
		r.setColor(dcolor);
		for (int i=0; i<lines.size(); i++) {
			int py = i*r.getFontHeight();
			//System.out.println("py:"+py);
			r.drawText(i==0?position.x:0, position.y+py, lines.get(i));
		}
	}
	
	protected void setRenderingData(Renderer r) {
		r.setFont("Arial", FontStyle.PLAIN, 12);
	}
}
