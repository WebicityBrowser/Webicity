package everyos.browser.webicitybrowser.gui.ui;

import everyos.browser.webicitybrowser.gui.component.CircularText;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.PaintContext;
import everyos.engine.ribbon.core.graphics.RenderContext;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.rendering.RibbonFont;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
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
		public void render(RendererData rd, SizePosGroup sizepos, RenderContext context) {
			this.text = getComponent().casted(CircularText.class).getText();
			
			RibbonFont font = rd.getState().getFont();
			
			this.strwidth = StringWrapHelper.stringWidth(font, text);
			sizepos.move(strwidth+font.getPaddingHeight(), true);
			sizepos.setMinLineHeight(font.getHeight());
			
			this.bounds = sizepos.getSize();
		}
	
		@Override
		public void paint(RendererData rd, PaintContext context) {
			Renderer r = context.getRenderer();
			rd.useBackground();
			r.drawEllipse(rd, 0, 0, bounds.getHeight(), bounds.getHeight());
			r.drawEllipse(rd, bounds.getWidth()-bounds.getHeight(), 0, bounds.getHeight(), bounds.getHeight());
			r.drawFilledRect(rd, bounds.getHeight()/2, 0, bounds.getWidth()-bounds.getHeight(), bounds.getHeight());
			
			rd.useForeground();
			r.drawText(rd, bounds.getWidth()/2-strwidth/2, bounds.getHeight()/2-rd.getState().getFont().getHeight()/2, text);
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
