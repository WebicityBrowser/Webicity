package everyos.browser.webicitybrowser.gui.ui;

import everyos.browser.webicitybrowser.gui.component.CircularText;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.Component;
import everyos.engine.ribbon.core.graphics.PaintContext;
import everyos.engine.ribbon.core.graphics.RenderContext;
import everyos.engine.ribbon.core.graphics.font.RibbonFontMetrics;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.ui.simple.SimpleBlockComponentUI;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;
import everyos.engine.ribbon.ui.simple.helper.StringWrapHelper;
import everyos.engine.ribbon.ui.simple.shape.SizePosGroup;

public class CircularTextUI extends SimpleBlockComponentUI {
	private final Appearence appearence;

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
		private int strWidth;
		private Dimension bounds;
		
		@Override
		public void render(RendererData rd, SizePosGroup sizepos, RenderContext context) {
			this.text = getComponent().<CircularText>casted().getText();
			
			RibbonFontMetrics font = rd.getState().getFont();
			
			this.strWidth = StringWrapHelper.stringWidth(font, text);
			sizepos.move(strWidth+font.getPaddingHeight(), true);
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
			r.drawText(rd, bounds.getWidth()/2-strWidth/2, bounds.getHeight()/2-rd.getState().getFont().getHeight()/2, text);
		}

		@Override
		public void directive(UIDirective directive) {
			
		}

		@Override
		public void processEvent(UIEvent e) {
			
		}
	}
}
