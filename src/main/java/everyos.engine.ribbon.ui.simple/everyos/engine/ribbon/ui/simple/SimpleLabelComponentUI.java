package everyos.engine.ribbon.ui.simple;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.component.LabelComponent;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Position;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;
import everyos.engine.ribbon.ui.simple.helper.StringWrapHelper;

public class SimpleLabelComponentUI extends SimpleComponentUI {
	private LabelAppearence appearence;
	
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
		public void render(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
			this.text = getComponent().casted(LabelComponent.class).getText();
			
			this.position = sizepos.getCurrentPointer();
			int width = StringWrapHelper.stringWidth(r, text);
			this.height = (text.split("\n").length-1)*r.getFontHeight();
			sizepos.setMinLineHeight(height);
			sizepos.move(width, true);
		}

		@Override
		public void paint(Renderer r) {
			r.useForeground();
			r.drawText(position.getX(), position.getY()+height, text);
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
