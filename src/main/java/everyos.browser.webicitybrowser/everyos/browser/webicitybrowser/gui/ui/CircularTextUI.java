package everyos.browser.webicitybrowser.gui.ui;

import everyos.browser.webicitybrowser.gui.component.CircularText;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.guirenderer.GUIComponentUI;
import everyos.engine.ribbon.renderer.guirenderer.GUIRenderer;
import everyos.engine.ribbon.renderer.guirenderer.shape.SizePosGroup;
import everyos.engine.ribbon.ui.simple.SimpleBlockComponentUI;
import everyos.engine.ribbon.ui.simple.helper.StringWrapHelper;

public class CircularTextUI extends SimpleBlockComponentUI {
	protected String text;
	private int strwidth;

	public CircularTextUI() {}
	public CircularTextUI(Component c, GUIComponentUI parent) {
		super(c, parent);
		autofill = false;
	}
	@Override public ComponentUI create(Component c, ComponentUI parent) {
		return new CircularTextUI(c, (GUIComponentUI) parent);
	};
	@Override protected void renderUI(GUIRenderer r, SizePosGroup sizepos, UIManager<GUIComponentUI> uimgr) {
		this.text = this.<CircularText>getComponent().getText();
		
		/*r.setFont(
			(String) data.attributes.getOrDefault("font", "Arial"), 
			FontStyle.PLAIN,
			(int) data.attributes.getOrDefault("font-size", 16));*/
		this.strwidth = StringWrapHelper.stringWidth(r, text);
		sizepos.x+=strwidth+r.getFontPaddingHeight();
		sizepos.minIncrease(r.getFontHeight());
		sizepos.normalize();
		
		//super.calcInternalSize(r, sizepos, data);
	}

	@Override protected void paintUI(GUIRenderer r) {
		/*Color color = (Color) data.attributes.get("bg-color");
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
		r.setColor((Color) data.attributes.getOrDefault("fg-color", Color.BLACK));*/
		r.drawText(bounds.width/2-strwidth/2, bounds.height/2-r.getFontHeight()/2, text);
	}
}
