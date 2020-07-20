package everyos.engine.ribbon.graphics.ui.simple;

import java.util.ArrayList;

import everyos.engine.ribbon.graphics.Color;
import everyos.engine.ribbon.graphics.FontStyle;
import everyos.engine.ribbon.graphics.Renderer;
import everyos.engine.ribbon.graphics.component.Component;
import everyos.engine.ribbon.graphics.ui.ComponentUI;
import everyos.engine.ribbon.graphics.ui.DrawData;
import everyos.engine.ribbon.graphics.ui.simple.helper.StringWrapHelper;
import everyos.engine.ribbon.shape.SizePosGroup;

public class SimpleTextBoxComponentUI extends SimpleBlockComponentUI {
	private String text = "";
	private ArrayList<String> lines;
	private String align = "left";
	
	public SimpleTextBoxComponentUI() {}
	public SimpleTextBoxComponentUI(Component c) {
		super(c);
	}
	@Override public ComponentUI create(Component c) {
		return new SimpleTextBoxComponentUI(c);
	};
	@Override protected void calcInternalSize(Renderer r, SizePosGroup sizepos, DrawData data) {
		setRenderingData(r, data);
		lines = StringWrapHelper.calculateString(text, r, sizepos);
		super.calcInternalSize(r, sizepos, data);
	}

	@Override protected void drawInternal(Renderer r, DrawData data) {
		super.drawInternal(r, data);
		setRenderingData(r, data);
		for (int i=0; i<lines.size(); i++) {
			int py = i*r.getFontHeight();
			if (py>bounds.height) break;
			
			int width = StringWrapHelper.stringWidth(r, lines.get(i));
			int x = 0;
			if (align.equals("right")) {
				x = bounds.width-width;
			} else if (align.equals("center")) {
				x = bounds.width/2-width/2;
			}
			
			r.drawText(x, py, lines.get(i));
		}
	}
	
	protected void setRenderingData(Renderer r, DrawData data) {
		r.setFont(
			(String) data.attributes.getOrDefault("font", "Arial"),
			FontStyle.PLAIN,
			(int) data.attributes.getOrDefault("font-size", 12));
		r.setColor((Color) data.attributes.getOrDefault("fg-color", Color.BLACK));
	}
	
	@Override public void attribute(String name, Object attr) {
		super.attribute(name, attr);
		if (name=="text") this.text = (String) attr;
		if (name=="align") this.align = ((String) attr).toLowerCase();
	}
}
