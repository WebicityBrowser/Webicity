package everyos.browser.webicitybrowser.gui.ui;

import everyos.browser.webicitybrowser.gui.component.CircularText;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.guirenderer.shape.SizePosGroup;
import everyos.engine.ribbon.ui.simple.SimpleBlockComponentUI;
import everyos.engine.ribbon.ui.simple.helper.StringWrapHelper;

public class CircularTextUI extends SimpleBlockComponentUI {
	protected String text;
	private int strwidth;

	public CircularTextUI(Component c, ComponentUI parent) {
		super(c, parent);
	}
	
	@Override
	protected void renderUI(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
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

	@Override
	protected void paintUI(Renderer r) {
		paintMouse(r);
		
		r.useBackground();
		r.drawEllipse(0, 0, bounds.height, bounds.height);
		r.drawEllipse(bounds.width-bounds.height, 0, bounds.height, bounds.height);
		r.drawFilledRect(bounds.height/2, 0, bounds.width-bounds.height, bounds.height);
		
		/*r.setFont(
			(String) data.attributes.getOrDefault("font", "Arial"), 
			FontStyle.PLAIN,
			(int) data.attributes.getOrDefault("font-size", 16));*/
		r.useForeground();
		r.drawText(bounds.width/2-strwidth/2, bounds.height/2-r.getFontHeight()/2, text);
	}
}
