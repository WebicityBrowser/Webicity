package everyos.browser.webicitybrowser.gui.ui;

import everyos.browser.webicitybrowser.gui.component.CircularText;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIManager;
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
		this.strwidth = StringWrapHelper.stringWidth(r, text);
		sizepos.move(strwidth+r.getFontPaddingHeight(), true);
		sizepos.setMinLineHeight(r.getFontHeight());
		
		//super.calcInternalSize(r, sizepos, data);
	}

	@Override
	protected void paintUI(Renderer r) {
		paintMouse(r);
		
		r.useBackground();
		r.drawEllipse(0, 0, bounds.getHeight(), bounds.getHeight());
		r.drawEllipse(bounds.getWidth()-bounds.getHeight(), 0, bounds.getHeight(), bounds.getHeight());
		r.drawFilledRect(bounds.getHeight()/2, 0, bounds.getWidth()-bounds.getHeight(), bounds.getHeight());
		
		r.useForeground();
		r.drawText(bounds.getWidth()/2-strwidth/2, bounds.getHeight()/2-r.getFontHeight()/2, text);
	}
}
