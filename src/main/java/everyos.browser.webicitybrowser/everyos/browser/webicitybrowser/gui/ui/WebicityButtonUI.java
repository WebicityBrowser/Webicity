package everyos.browser.webicitybrowser.gui.ui;

import everyos.browser.webicitybrowser.gui.Styling;
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
    	private int strwidth;
		private Dimension bounds;
    	
	    @Override
	    public void render(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
	        this.strwidth = StringWrapHelper.stringWidth(r, Styling.PRODUCT_NAME);
	        sizepos.move(strwidth + r.getFontPaddingHeight(), true);
	        sizepos.setMinLineHeight(r.getFontHeight());
	
	       this.bounds = sizepos.getSize();
	    }
	
	    @Override
	    public void paint(Renderer r) {
	        r.useBackground();
	
	        int rectWidth = bounds.getWidth() - bounds.getHeight() / 2;
	
	        r.drawFilledRect(0, 0, rectWidth, bounds.getHeight());
	        r.drawEllipse(bounds.getWidth() - bounds.getHeight(), 0, bounds.getHeight(), bounds.getHeight());
	        r.drawFilledRect(rectWidth, 0, bounds.getHeight()/2, bounds.getHeight()/2);
	
	        r.useForeground();
	        r.drawText(bounds.getWidth() / 2 - strwidth / 2, Styling.ELEMENT_PADDING, Styling.PRODUCT_NAME);
	    }

		@Override
		public void directive(UIDirective directive) {
			
		}

		@Override
		public void processEvent(UIEvent e) {
			
		}
    }
}
