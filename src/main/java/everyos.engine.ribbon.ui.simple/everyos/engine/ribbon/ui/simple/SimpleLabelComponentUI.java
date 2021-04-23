package everyos.engine.ribbon.ui.simple;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Position;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.helper.StringWrapHelper;

public class SimpleLabelComponentUI extends SimpleComponentUI {
	private String text = "";
	private Position position;
	private int height;
	
	public SimpleLabelComponentUI(Component c, ComponentUI parent) {
		super(c, parent);
	}
	
	@Override
	public void renderUI(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
		this.position = sizepos.getCurrentPointer();
		int width = StringWrapHelper.stringWidth(r, text);
		this.height = text.split("\n").length*r.getFontHeight();
		sizepos.setMinLineHeight(height);
		sizepos.move(width, true);
		super.renderUI(r, sizepos, uimgr);
	}
	
	@Override
	public void paint(Renderer r) {
		super.paint(r);
		r.useForeground();
		r.drawText(position.getX(), position.getY()+height, text);
	}
}
