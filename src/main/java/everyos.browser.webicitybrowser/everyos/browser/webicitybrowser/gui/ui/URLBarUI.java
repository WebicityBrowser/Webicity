package everyos.browser.webicitybrowser.gui.ui;

import everyos.browser.webicitybrowser.gui.component.URLBar;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.guirenderer.shape.SizePosGroup;
import everyos.engine.ribbon.ui.simple.SimpleBlockComponentUI;
import everyos.engine.ribbon.ui.simple.helper.StringWrapHelper;

public class URLBarUI extends SimpleBlockComponentUI {
	protected String text = "";

	public URLBarUI(Component c, ComponentUI parent) {
		super(c, parent);
	}
	@Override
	protected void renderUI(Renderer r, SizePosGroup sizepos, UIManager mgrui) {
		this.text = this.<URLBar>getComponent().getText();
		
		/*r.setFont(
			(String) data.attributes.getOrDefault("font", "Arial"), 
			FontStyle.PLAIN,
			(int) data.attributes.getOrDefault("font-size", 16));*/
		int width = StringWrapHelper.stringWidth(r, text);
		sizepos.x+=width+10;
		sizepos.minIncrease(r.getFontHeight());
		sizepos.normalize();
		
		//super.renderUI(r, sizepos, mgrui);
	}

	@Override
	protected void paintUI(Renderer r) {
		
		r.useBackground();
		r.drawEllipse(0, 0, bounds.height, bounds.height);
		r.drawEllipse(bounds.width-bounds.height, 0, bounds.height, bounds.height);
		r.drawFilledRect(bounds.height/2, 0, bounds.width-bounds.height, bounds.height);
		
		/*r.setFont(
			(String) data.attributes.getOrDefault("font", "Arial"), 
			FontStyle.PLAIN,
			(int) data.attributes.getOrDefault("font-size", 16));*/
		r.useForeground();
		r.drawText(bounds.height, bounds.height/2-r.getFontHeight()/2, text);
	}
}
