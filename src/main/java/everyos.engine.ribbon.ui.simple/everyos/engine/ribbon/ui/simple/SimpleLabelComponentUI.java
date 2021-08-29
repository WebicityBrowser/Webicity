package everyos.engine.ribbon.ui.simple;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.component.LabelComponent;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.PaintContext;
import everyos.engine.ribbon.core.graphics.RenderContext;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.rendering.RibbonFont;
import everyos.engine.ribbon.core.shape.Position;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;
import everyos.engine.ribbon.ui.simple.helper.StringWrapHelper;

public class SimpleLabelComponentUI extends SimpleComponentUI {
	private final LabelAppearence appearence;
	
	public SimpleLabelComponentUI(Component c, ComponentUI parent) {
		super(c, parent);
		
		this.appearence = new LabelAppearence();
	}
	
	@Override
	protected Appearence getAppearence() {
		return appearence;
	}
	
	private class LabelAppearence implements Appearence {
		private String text = "";
		private Position position;
		private int height;

		@Override
		public void render(RendererData rd, SizePosGroup sizepos, RenderContext context) {
			this.text = getComponent().casted(LabelComponent.class).getText();
			
			RibbonFont font = rd.getState().getFont();
			
			this.position = sizepos.getCurrentPointer();
			int width = StringWrapHelper.stringWidth(font, text);
			this.height = font.getHeight() + font.getPaddingHeight();
			sizepos.setMinLineHeight(height);
			sizepos.move(width, true);
		}

		@Override
		public void paint(RendererData rd, PaintContext context) {
			rd.useForeground();
			context.getRenderer().drawText(rd, position.getX(), position.getY(), text);
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
