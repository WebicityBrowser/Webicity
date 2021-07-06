package everyos.browser.webicitybrowser.gui.ui;

import everyos.browser.webicitybrowser.gui.Styling;
import everyos.browser.webicitybrowser.gui.component.URLBar;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.Color;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.SimpleBlockComponentUI;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;
import everyos.engine.ribbon.ui.simple.helper.StringWrapHelper;

public class URLBarUI extends SimpleBlockComponentUI {
	private Appearence appearence;

	public URLBarUI(Component c, ComponentUI parent) {
		super(c, parent);
		
		this.appearence = new URLBarAppearence();
	}
	
	@Override
	protected Appearence getAppearence() {
		return this.appearence;
	}
	
	private class URLBarAppearence implements Appearence {
		private String text = "";
		
		private boolean active = true;
		private Color currentColor = Styling.BACKGROUND_SECONDARY;
		
		private long lastCursorOn = System.currentTimeMillis();

		private Dimension bounds;
		
		@Override
		public void render(Renderer r, SizePosGroup sizepos, UIManager mgrui) {
			this.text = getComponent().casted(URLBar.class).getText();
			
			int width = StringWrapHelper.stringWidth(r, text);
			sizepos.move(width+10, true);
			sizepos.setMinLineHeight(r.getFontHeight());
			
			this.bounds = sizepos.getSize();
		}

		@Override
		public void paint(Renderer r) {
			r.setBackground(currentColor);
			r.useBackground();
			r.drawEllipse(0, 0, bounds.getHeight(), bounds.getHeight());
			r.drawEllipse(bounds.getWidth()-bounds.getHeight(), 0, bounds.getHeight(), bounds.getHeight());
			r.drawFilledRect(bounds.getHeight()/2, 0, bounds.getWidth()-bounds.getHeight(), bounds.getHeight());
			
			r.useForeground();
			r.drawText(bounds.getHeight(), bounds.getHeight()/2-r.getFontHeight()/2, text);
			
			paintAnimation(r, bounds);
		}
		
		private void paintAnimation(Renderer r, Dimension bounds) {
			if (active) {
				if (System.currentTimeMillis()-lastCursorOn>1000) {
					lastCursorOn = System.currentTimeMillis();
				}
				if (System.currentTimeMillis()-lastCursorOn<500) {
					r.drawLine(bounds.getHeight(), bounds.getHeight()/4, 0, bounds.getHeight()/2);
				}
			}
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
