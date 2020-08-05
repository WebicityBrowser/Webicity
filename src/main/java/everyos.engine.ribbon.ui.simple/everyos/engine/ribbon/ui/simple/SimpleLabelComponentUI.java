package everyos.engine.ribbon.ui.simple;

import java.awt.Point;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.guirenderer.GUIComponentUI;
import everyos.engine.ribbon.renderer.guirenderer.GUIRenderer;
import everyos.engine.ribbon.renderer.guirenderer.shape.SizePosGroup;
import everyos.engine.ribbon.ui.simple.helper.StringWrapHelper;

public class SimpleLabelComponentUI extends SimpleComponentUI {
	private String text = "";
	private Point position;
	private int height;
	
	public SimpleLabelComponentUI() {}
	public SimpleLabelComponentUI(Component c, GUIComponentUI parent) {
		super(c, parent);
	}
	@Override public ComponentUI create(Component c, ComponentUI parent) {
		return new SimpleLabelComponentUI(c, (GUIComponentUI) parent);
	};
	@Override public void renderUI(GUIRenderer r, SizePosGroup sizepos, UIManager<GUIComponentUI> uimgr) {
		/*r.setFont(
			(String) data.attributes.getOrDefault("font", "Arial"), 
			FontStyle.PLAIN,
			(int) data.attributes.getOrDefault("font-size", 16));*/
		this.position = new Point(sizepos.x, sizepos.y);
		int width = StringWrapHelper.stringWidth(r, text);
		this.height = text.split("\n").length*r.getFontHeight();
		sizepos.x+=width;
		sizepos.minIncrease(height);
		sizepos.normalize();
		super.renderUI(r, sizepos, uimgr);
	}
	@Override public void paint(GUIRenderer r) {
		super.paint(r);
		/*r.setFont(
			(String) data.attributes.getOrDefault("font", "Arial"), 
			FontStyle.PLAIN,
			(int) data.attributes.getOrDefault("font-size", 16));*/
		r.useForeground();
		r.drawText(position.x, position.y+height, text);
	}
}
