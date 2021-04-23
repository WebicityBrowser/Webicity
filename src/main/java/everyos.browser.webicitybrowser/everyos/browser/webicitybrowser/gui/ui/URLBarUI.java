package everyos.browser.webicitybrowser.gui.ui;

import everyos.browser.webicitybrowser.gui.component.URLBar;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIManager;
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
		sizepos.move(width+10, true);
		sizepos.setMinLineHeight(r.getFontHeight());
		
		//super.renderUI(r, sizepos, mgrui);
	}

	@Override
	protected void paintUI(Renderer r) {
		
		r.useBackground();
		r.drawEllipse(0, 0, bounds.getHeight(), bounds.getHeight());
		r.drawEllipse(bounds.getWidth()-bounds.getHeight(), 0, bounds.getHeight(), bounds.getHeight());
		r.drawFilledRect(bounds.getHeight()/2, 0, bounds.getWidth()-bounds.getHeight(), bounds.getHeight());
		
		/*r.setFont(
			(String) data.attributes.getOrDefault("font", "Arial"), 
			FontStyle.PLAIN,
			(int) data.attributes.getOrDefault("font-size", 16));*/
		r.useForeground();
		r.drawText(bounds.getHeight(), bounds.getHeight()/2-r.getFontHeight()/2, text);
	}
}
