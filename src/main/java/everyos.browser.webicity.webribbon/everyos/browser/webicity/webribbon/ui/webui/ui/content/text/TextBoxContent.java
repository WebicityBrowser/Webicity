package everyos.browser.webicity.webribbon.ui.webui.ui.content.text;

import everyos.browser.webicity.webribbon.gui.Content;
import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.box.stage.MultiBox;
import everyos.browser.webicity.webribbon.gui.box.stage.PaintStageBox;
import everyos.browser.webicity.webribbon.gui.box.stage.RenderStageBox;
import everyos.browser.webicity.webribbon.ui.webui.helper.BoxUtil;
import everyos.engine.ribbon.core.graphics.font.RibbonFontMetrics;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.ui.simple.shape.SizePosGroup;

public class TextBoxContent implements Content {
	
	private static final int SEGMENT_BREAK = '\n';

	private WhiteSpaceType whiteSpaceType = WhiteSpaceType.NORMAL;

	public static final int SOFT_WRAP = -2;
	public static final int SOFT_NBSP = -3;

	private final int[] codePoints;
	private final int offset;
	private final int end;
	
	private String text;

	public TextBoxContent(String text) {
		int [] codePoints = text.codePoints().toArray();
		transform(codePoints);
		
		this.codePoints = codePoints;
		this.offset = 0;
		this.end = codePoints.length;
	}
	
	//TODO
	@SuppressWarnings("unused")
	private TextBoxContent(int[] codePoints, int offset, int end) {
		this.codePoints = codePoints;
		this.offset = offset;
		this.end = end;
	}

	@Override
	public void render(RenderStageBox box, RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
		StringBuilder finalText = new StringBuilder(end - offset);
		
		RibbonFontMetrics font = rd.getState().getFont();
		
		int len = 0;
		for (int i = offset; i < end; i++) {
			int ch = codePoints[i];
			if (ch == -1 || "\r\n\t".indexOf(ch) != -1) {
				continue;
			}
			
			if (len == 0 && ch != ' ') {
				int lineHeight = font.getHeight() + font.getPaddingHeight();
				sizepos.setMinLineHeight(lineHeight);
			}
			
			len += font.getCharWidth((char) ch);
			
			finalText.appendCodePoint(ch);
		}
		
		sizepos.move(len, true);
		sizepos.nextLine();
		
		this.text = finalText.toString();
	}

	@Override
	public void paint(PaintStageBox box, RendererData rd, Rectangle viewport, WebPaintContext context) {
		Renderer r = context.getRenderer();
		
		rd.useForeground();
		r.drawText(rd, 0, 0, text);
	}

	@Override
	public MultiBox[] split(MultiBox box, RendererData rd, int width, WebRenderContext context) {
		//TODO: Actually break the text into lines
		return BoxUtil.keepWholeSplitWithContent(box, box.getContent());
	}
	
	@Override
	public String toString() {
		return text;
	}

	private void transform(int[] codePoints) {
		collapse(codePoints);
		trim(codePoints);
	}

	private void collapse(int[] codePoints) {
		if (
			whiteSpaceType == WhiteSpaceType.NORMAL ||
			whiteSpaceType == WhiteSpaceType.NOWRAP ||
			whiteSpaceType == WhiteSpaceType.PRE_LINE) {
			
			for (int i = 0; i < codePoints.length; i++) {
				if (codePoints[i] == SEGMENT_BREAK) {
					removeSpacesAndTabsAroundSegmentBreak(codePoints, i);
				}
			}	
		}
		//TODO: Collapse segment breaks and stuff
		
		// Create soft-wrap oppurtunities for other types
	}

	private void removeSpacesAndTabsAroundSegmentBreak(int[] codePoints, int j) {
		for (int i = j - 1; i > 0; i--) {
			if (!(codePoints[i] == ' ' || codePoints[i] == '\t')) {
				break;
			}
			
			codePoints[i] = -1;
		}
		
		for (int i = j + 1; i < codePoints.length; i++) {
			if (!(codePoints[i] == ' ' || codePoints[i] == '\t')) {
				break;
			}
			
			codePoints[i] = -1;
		}
	}

	private void trim(int[] codePoints) {
		// TODO: Position
		// TODO: Use a context to actually know if we are a line start
		for (int i = 0; i < codePoints.length; i++) {
			if (!(codePoints[i] == ' ' || codePoints[i] == '\t')) {
				break;
			}
			
			codePoints[i] = -1;
		}
		
		// TODO: Preserved tabs
		
		
	}
	
}