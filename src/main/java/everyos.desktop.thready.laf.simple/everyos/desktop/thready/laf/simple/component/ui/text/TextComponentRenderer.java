package everyos.desktop.thready.laf.simple.component.ui.text;

import everyos.desktop.thready.basic.component.TextComponent;
import everyos.desktop.thready.core.graphics.text.FontInfo;
import everyos.desktop.thready.core.graphics.text.FontMetrics;
import everyos.desktop.thready.core.graphics.text.LoadedFont;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.render.FluidRenderer;
import everyos.desktop.thready.core.gui.stage.render.RenderContext;
import everyos.desktop.thready.core.gui.stage.render.unit.UnitGenerator;
import everyos.desktop.thready.laf.simple.util.DirectiveUtil;

public class TextComponentRenderer implements FluidRenderer {

	private final Box box;

	public TextComponentRenderer(Box box) {
		this.box = box;
	}

	@Override
	public UnitGenerator render(RenderContext renderContext) {
		FontInfo fontInfo = getFontInfo();
		LoadedFont font = renderContext.getResourceGenerator().loadFont(fontInfo);
		
		String text = ((TextComponent) box.getOwningComponent()).getText();
		float[] charWidths = calculateCharWidths(text, font.getMetrics());
		
		return new TextUnitGenerator(box, text, font, charWidths);
	}

	private FontInfo getFontInfo() {
		return DirectiveUtil.getFont(box.getDirectivePool());
	}
	
	private float[] calculateCharWidths(String text, FontMetrics metrics) {
		float[] sizes = new float[text.length()];
		for (int i = 0; i < sizes.length; i++) {
			sizes[i] = metrics.getCharacterWidth(text.codePointAt(i));
		}
		
		return sizes;
	}

}
