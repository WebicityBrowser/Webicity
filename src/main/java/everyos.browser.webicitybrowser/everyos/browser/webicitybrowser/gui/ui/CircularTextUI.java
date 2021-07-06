package everyos.browser.webicitybrowser.gui.ui;

import everyos.browser.webicitybrowser.gui.component.CircularText;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.SimpleBlockComponentUI;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;
import everyos.engine.ribbon.ui.simple.helper.StringWrapHelper;

public class CircularTextUI extends SimpleBlockComponentUI {
	private Appearence appearence;

	public CircularTextUI(Component c, ComponentUI parent) {
		super(c, parent);
		
		this.appearence = new CircularTextAppearence();
	}
	
	@Override
	protected Appearence getAppearence() {
		return this.appearence;
	}
	
	private class CircularTextAppearence implements Appearence {
		private String text;
		private int strwidth;
		private Dimension bounds;
		
		@Override
		public void render(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
			this.text = getComponent().casted(CircularText.class).getText();
			
			this.strwidth = StringWrapHelper.stringWidth(r, text);
			sizepos.move(strwidth+r.getFontPaddingHeight(), true);
			sizepos.setMinLineHeight(r.getFontHeight());
			
			this.bounds = sizepos.getSize();
		}
	
		@Override
		public void paint(Renderer r) {
			r.useBackground();
			r.drawEllipse(0, 0, bounds.getHeight(), bounds.getHeight());
			r.drawEllipse(bounds.getWidth()-bounds.getHeight(), 0, bounds.getHeight(), bounds.getHeight());
			r.drawFilledRect(bounds.getHeight()/2, 0, bounds.getWidth()-bounds.getHeight(), bounds.getHeight());
			
			r.useForeground();
			r.drawText(bounds.getWidth()/2-strwidth/2, bounds.getHeight()/2-r.getFontHeight()/2, text);
		}

		@Override
		public void directive(UIDirective directive) {
			
		}

		@Override
		public void processEvent(UIEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
}
