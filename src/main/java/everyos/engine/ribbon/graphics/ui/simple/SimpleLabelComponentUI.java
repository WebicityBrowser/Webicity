package everyos.engine.ribbon.graphics.ui.simple;

import java.awt.Point;

import everyos.engine.ribbon.graphics.Color;
import everyos.engine.ribbon.graphics.FontStyle;
import everyos.engine.ribbon.graphics.Renderer;
import everyos.engine.ribbon.graphics.component.Component;
import everyos.engine.ribbon.graphics.ui.ComponentUI;
import everyos.engine.ribbon.graphics.ui.DrawData;
import everyos.engine.ribbon.graphics.ui.simple.helper.StringWrapHelper;
import everyos.engine.ribbon.shape.SizePosGroup;

public class SimpleLabelComponentUI extends SimpleComponentUI {
	private String text = "";
	private Point position;
	private int height;
	
	public SimpleLabelComponentUI() {}
	public SimpleLabelComponentUI(Component c) {
		this.c = c;
	}
	@Override public ComponentUI create(Component c) {
		return new SimpleLabelComponentUI(c);
	};
	@Override public void calcSize(Renderer r, SizePosGroup sizepos, DrawData data) {
		r.setFont(
			(String) data.attributes.getOrDefault("font", "Arial"), 
			FontStyle.PLAIN,
			(int) data.attributes.getOrDefault("font-size", 16));
		this.position = new Point(sizepos.x, sizepos.y);
		int width = StringWrapHelper.stringWidth(r, text);
		this.height = text.split("\n").length*r.getFontHeight();
		sizepos.x+=width;
		sizepos.minIncrease(height);
		sizepos.normalize();
		super.calcSize(r, sizepos, data);
	}
	@Override public void draw(Renderer r, DrawData data) {
		super.draw(r, data);
		r.setFont(
			(String) data.attributes.getOrDefault("font", "Arial"), 
			FontStyle.PLAIN,
			(int) data.attributes.getOrDefault("font-size", 16));
		r.setColor((Color) data.attributes.getOrDefault("fg-color", Color.BLACK));
		r.drawText(position.x, position.y+height, text);
	}
	@Override public void attribute(String name, Object attr) {
		super.attribute(name, attr);
		if (name=="text") this.text = (String) attr;
	}
}
