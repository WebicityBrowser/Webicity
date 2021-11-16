package everyos.browser.webicitybrowser.gui.ui;

import everyos.browser.webicitybrowser.gui.Styling;
import everyos.browser.webicitybrowser.gui.component.TabButton;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.PaintContext;
import everyos.engine.ribbon.core.graphics.RenderContext;
import everyos.engine.ribbon.core.graphics.font.RibbonFontMetrics;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.ui.simple.SimpleBlockComponentUI;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;
import everyos.engine.ribbon.ui.simple.helper.StringWrapHelper;

public class TabButtonUI extends SimpleBlockComponentUI {
	private final Appearence appearence;

	public TabButtonUI(Component c, ComponentUI parent) {
		super(c, parent);
		
		this.appearence = new TabButtonAppearence();
	}
	
	
	@Override
	protected Appearence getAppearence() {
		return this.appearence;
	}
	
	private class TabButtonAppearence implements Appearence {
		private String text;
		private int strWidth;
		private Dimension bounds;
		
		@Override
		public void render(RendererData rd, SizePosGroup sizepos, RenderContext context) {
			this.bounds = sizepos.getSize();
			
			int maxTextWidth = bounds.getWidth() - Styling.BUTTON_WIDTH - 2 * Styling.ELEMENT_PADDING;

			RibbonFontMetrics font = rd.getState().getFont();
			
			this.text = StringWrapHelper.trim(font, getComponent().casted(TabButton.class).getText(), maxTextWidth);
			this.strWidth = StringWrapHelper.stringWidth(font, text);
			sizepos.move(strWidth+font.getPaddingHeight(), true);
			sizepos.setMinLineHeight(font.getHeight());
		}

		@Override
		public void paint(RendererData rd, PaintContext context) {
			Renderer r = context.getRenderer();
			
			rd.useBackground();
			r.drawEllipse(rd, 0, 0, bounds.getHeight(), bounds.getHeight());
			r.drawEllipse(rd, bounds.getWidth() - bounds.getHeight(), 0, bounds.getHeight(), bounds.getHeight());
			r.drawFilledRect(rd, bounds.getHeight() / 2, 0, bounds.getWidth() - bounds.getHeight(), bounds.getHeight());
			r.drawFilledRect(rd, 0, 0, bounds.getWidth(), bounds.getHeight() / 2);

			rd.useForeground();
			r.drawText(rd, Styling.BUTTON_WIDTH + Styling.ELEMENT_PADDING, Styling.ELEMENT_PADDING, text);
		}

		@Override
		public void directive(UIDirective directive) {
			
		}

		@Override
		public void processEvent(UIEvent e) {
			
		}
	}
}
