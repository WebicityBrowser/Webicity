package everyos.engine.ribbon.ui.simple;

import java.util.ArrayList;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.component.TextBoxComponent;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.PaintContext;
import everyos.engine.ribbon.core.graphics.RenderContext;
import everyos.engine.ribbon.core.graphics.font.RibbonFontMetrics;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;
import everyos.engine.ribbon.ui.simple.helper.StringWrapHelper;

public class SimpleTextBoxComponentUI extends SimpleBlockComponentUI {
	
	private final Appearence appearence;
	
	public SimpleTextBoxComponentUI(Component c, ComponentUI parent) {
		super(c, parent);
		
		this.appearence = new TextBoxAppearence();
	}
	
	@Override
	protected Appearence getAppearence() {
		return this.appearence;
	}

	private class TextBoxAppearence implements Appearence {
		private ArrayList<String> lines;
		private String align = "left";
		private Dimension size;
		
		@Override
		public void render(RendererData rd, SizePosGroup sizepos, RenderContext context) {
			String text = getComponent().casted(TextBoxComponent.class).getText();
			
			lines = StringWrapHelper.calculateString(text, rd.getState().getFont(), sizepos);
			
			this.size = sizepos.getSize();
		}

		@Override
		public void paint(RendererData rd, PaintContext context) {
			RibbonFontMetrics font = rd.getState().getFont();
			
			rd.useForeground();
			for (int i=0; i<lines.size(); i++) {
				int py = i*font.getHeight();
				if (py > size.getHeight()) {
					break;
				}
				
				int width = StringWrapHelper.stringWidth(font, lines.get(i));
				int x = 0;
				if (align.equals("right")) {
					x = size.getWidth()-width;
				} else if (align.equals("center")) {
					x = size.getWidth()/2-width/2;
				}
				
				context.getRenderer().drawText(rd, x, py, lines.get(i));
			}
		}

		@Override
		public void directive(UIDirective directive) {
			
		}

		@Override
		public void processEvent(UIEvent e) {
			// TODO
		}
	}
	
}
