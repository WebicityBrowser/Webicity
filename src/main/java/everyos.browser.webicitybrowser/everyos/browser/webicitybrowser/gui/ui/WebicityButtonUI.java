package everyos.browser.webicitybrowser.gui.ui;

import everyos.browser.webicitybrowser.gui.Styling;
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

public class WebicityButtonUI extends SimpleBlockComponentUI {
	
    private ButtonAppearence appearence;

	public WebicityButtonUI(Component c, ComponentUI parent) {
        super(c, parent);
        
        this.appearence = new ButtonAppearence();
    }

    @Override
    protected Appearence getAppearence() {
    	return this.appearence;
    }
    
    private class ButtonAppearence implements Appearence {
    	private int strWidth;
		private Dimension bounds;
    	
	    @Override
	    public void render(RendererData rd, SizePosGroup sizepos, RenderContext context) {
			RibbonFontMetrics font = rd.getState().getFont();
			
			this.strWidth = StringWrapHelper.stringWidth(font, Styling.PRODUCT_NAME);
			sizepos.move(strWidth + font.getHeight(), true);
			sizepos.setMinLineHeight(font.getHeight());
			
			this.bounds = sizepos.getSize();
	    }
	
	    @Override
	    public void paint(RendererData rd, PaintContext context) {
	    	Renderer r = context.getRenderer();
	    	
	        rd.useBackground();
	
	        int rectWidth = bounds.getWidth() - bounds.getHeight() / 2;
	
	        r.drawFilledRect(rd, 0, 0, rectWidth, bounds.getHeight());
	        r.drawEllipse(rd, bounds.getWidth() - bounds.getHeight(), 0, bounds.getHeight(), bounds.getHeight());
	        r.drawFilledRect(rd, rectWidth, 0, bounds.getHeight()/2, bounds.getHeight()/2);
	
	        rd.useForeground();
	        r.drawText(rd, bounds.getWidth() / 2 - strWidth / 2, Styling.ELEMENT_PADDING, Styling.PRODUCT_NAME);
	    }

		@Override
		public void directive(UIDirective directive) {
			
		}

		@Override
		public void processEvent(UIEvent e) {
			
		}
    }
}
