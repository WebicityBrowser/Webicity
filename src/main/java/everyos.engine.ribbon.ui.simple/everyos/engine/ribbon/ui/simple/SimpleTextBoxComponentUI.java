package everyos.engine.ribbon.ui.simple;

import java.util.ArrayList;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.component.TextBoxComponent;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.guirenderer.GUIComponentUI;
import everyos.engine.ribbon.renderer.guirenderer.GUIRenderer;
import everyos.engine.ribbon.renderer.guirenderer.shape.SizePosGroup;
import everyos.engine.ribbon.ui.simple.helper.StringWrapHelper;

public class SimpleTextBoxComponentUI extends SimpleBlockComponentUI {
	private String text = "";
	private ArrayList<String> lines;
	private String align = "left";
	
	public SimpleTextBoxComponentUI() {}
	public SimpleTextBoxComponentUI(Component c, GUIComponentUI parent) {
		super(c, parent);
	}
	@Override public ComponentUI create(Component c, ComponentUI parent) {
		return new SimpleTextBoxComponentUI(c, (GUIComponentUI) parent);
	};
	
	@Override protected void renderUI(GUIRenderer r, SizePosGroup sizepos, UIManager<GUIComponentUI> uimgr) {
		text = this.<TextBoxComponent>getComponent().getText();
		
		lines = StringWrapHelper.calculateString(text, r, sizepos);
		super.renderUI(r, sizepos, uimgr);
	}

	@Override protected void paintUI(GUIRenderer r) {
		super.paintUI(r);
		r.useForeground();
		for (int i=0; i<lines.size(); i++) {
			int py = i*r.getFontHeight();
			if (py>bounds.height) break;
			
			int width = StringWrapHelper.stringWidth(r, lines.get(i));
			int x = 0;
			if (align.equals("right")) {
				x = bounds.width-width;
			} else if (align.equals("center")) {
				x = bounds.width/2-width/2;
			}
			
			r.drawText(x, py, lines.get(i));
		}
	}
}
