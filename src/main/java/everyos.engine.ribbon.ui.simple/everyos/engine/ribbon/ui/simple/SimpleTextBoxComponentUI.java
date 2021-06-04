package everyos.engine.ribbon.ui.simple;

import java.util.ArrayList;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.component.TextBoxComponent;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.helper.StringWrapHelper;

public class SimpleTextBoxComponentUI extends SimpleBlockComponentUI {
	private ArrayList<String> lines;
	private String align = "left";
	
	public SimpleTextBoxComponentUI(Component c, ComponentUI parent) {
		super(c, parent);
	}
	
	@Override
	protected void renderUI(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
		String text = this.<TextBoxComponent>getComponent().getText();
		
		lines = StringWrapHelper.calculateString(text, r, sizepos);
		super.renderUI(r, sizepos, uimgr);
	}

	@Override
	protected void paintUI(Renderer r) {
		super.paintUI(r);
		r.useForeground();
		for (int i=0; i<lines.size(); i++) {
			int py = i*r.getFontHeight();
			if (py>getBounds().getHeight()) break;
			
			int width = StringWrapHelper.stringWidth(r, lines.get(i));
			int x = 0;
			if (align.equals("right")) {
				x = getBounds().getWidth()-width;
			} else if (align.equals("center")) {
				x = getBounds().getWidth()/2-width/2;
			}
			
			r.drawText(x, py, lines.get(i));
		}
	}
}
