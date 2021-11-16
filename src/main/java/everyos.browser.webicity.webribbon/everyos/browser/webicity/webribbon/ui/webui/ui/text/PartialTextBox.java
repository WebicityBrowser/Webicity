package everyos.browser.webicity.webribbon.ui.webui.ui.text;

import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.box.CullingFilter;
import everyos.browser.webicity.webribbon.ui.webui.rendering.box.InlineLevelBoxBase;
import everyos.engine.ribbon.core.graphics.font.RibbonFontMetrics;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;

public class PartialTextBox extends InlineLevelBoxBase {
	
	public static final int SOFT_WRAP = -2;
	public static final int SOFT_NBSP = -3;

	private final int[] codePoints;
	private final int offset;
	private final int end;
	
	private String text;

	public PartialTextBox(int[] codePoints, int offset, int end) {
		this.codePoints = codePoints;
		this.offset = offset;
		this.end = end;
	}

	@Override
	public void render(RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
		StringBuilder finalText = new StringBuilder(end - offset);
		
		RibbonFontMetrics font = rd.getState().getFont();
		
		int len = 0;
		for (int i = offset; i < end; i++) {
			int ch = codePoints[i];
			if (ch == -1 || "\r\n\t".indexOf(ch) != -1) {
				continue;
			}
			
			if (len == 0) {
				int lineHeight = font.getHeight()+font.getPaddingHeight();
				sizepos.setMinLineHeight(lineHeight);
			}
			
			len += font.getCharWidth((char) ch);
			
			finalText.appendCodePoint(ch);
		}
		
		sizepos.move(len, true);
		
		this.text = finalText.toString();
	}

	@Override
	public void paint(RendererData rd, Rectangle viewport, WebPaintContext context) {
		Renderer r = context.getRenderer();
		
		rd.useForeground();
		r.drawText(rd, 0, 0, text);
	}

	@Override
	public CullingFilter getPaintCullingFilter() {
		return vp -> true;
	}

	@Override
	public String toString() {
		return text;
	}
}
