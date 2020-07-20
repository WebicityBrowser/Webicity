package everyos.browser.webicitybrowser.gui.ui;

import everyos.engine.ribbon.graphics.Color;
import everyos.engine.ribbon.graphics.FontStyle;
import everyos.engine.ribbon.graphics.Renderer;
import everyos.engine.ribbon.graphics.component.Component;
import everyos.engine.ribbon.graphics.ui.ComponentUI;
import everyos.engine.ribbon.graphics.ui.DrawData;
import everyos.engine.ribbon.graphics.ui.simple.SimpleBlockComponentUI;
import everyos.engine.ribbon.graphics.ui.simple.helper.StringWrapHelper;
import everyos.engine.ribbon.shape.SizePosGroup;

public class URLBarUI extends SimpleBlockComponentUI {
	protected String text = "";

	public URLBarUI() {}
	public URLBarUI(Component c) {
		super(c);
		autofill = false;
	}
	@Override public ComponentUI create(Component c) {
		return new URLBarUI(c);
	};
	@Override protected void calcInternalSize(Renderer r, SizePosGroup sizepos, DrawData data) {
		
		r.setFont(
			(String) data.attributes.getOrDefault("font", "Arial"), 
			FontStyle.PLAIN,
			(int) data.attributes.getOrDefault("font-size", 16));
		int width = StringWrapHelper.stringWidth(r, text);
		sizepos.x+=width+10;
		sizepos.minIncrease(r.getFontHeight());
		sizepos.normalize();
		
		super.calcInternalSize(r, sizepos, data);
	}

	@Override protected void drawInternal(Renderer r, DrawData data) {
		Color color = (Color) data.attributes.get("bg-color");
		if (color!=null) {
			r.setColor(color);
			r.drawEllipse(0, 0, bounds.height, bounds.height);
			r.drawEllipse(bounds.width-bounds.height, 0, bounds.height, bounds.height);
			r.drawFilledRect(bounds.height/2, 0, bounds.width-bounds.height, bounds.height);
		}
		
		r.setFont(
			(String) data.attributes.getOrDefault("font", "Arial"), 
			FontStyle.PLAIN,
			(int) data.attributes.getOrDefault("font-size", 16));
		r.setColor((Color) data.attributes.getOrDefault("fg-color", Color.BLACK));
		r.drawText(bounds.height, bounds.height/2-r.getFontHeight()/2, text);
	}
	
	@Override public void attribute(String name, Object attr) {
		super.attribute(name, attr);
		if (name=="text") text = (String) attr;
	}
}
