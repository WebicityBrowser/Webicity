package everyos.browser.webicity.webribbon.ui.webui.ui.text;

import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.box.CullingFilter;
import everyos.browser.webicity.webribbon.gui.box.InlineLevelBox;
import everyos.browser.webicity.webribbon.ui.webui.rendering.box.InlineLevelBoxBase;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;

class TextBox extends InlineLevelBoxBase {
	
	private static final int SEGMENT_BREAK = '\n';

	private final String text;
	
	private WhiteSpaceType whiteSpaceType = WhiteSpaceType.NORMAL;

	public TextBox(String text) {
		this.text = text;
	}

	@Override
	public void render(RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
		// Not actually supposed to be called
	}

	@Override
	public void paint(RendererData rd, Rectangle viewport, WebPaintContext context) {
		// Not actually supposed to be called
	}

	@Override
	public CullingFilter getPaintCullingFilter() {
		return vp -> true;
	}
	
	@Override
	public InlineLevelBox[] split(RendererData rd, int width, WebRenderContext context, boolean first) {
		int [] codePoints = text.codePoints().toArray();
		transform(codePoints);
		
		return new PartialTextBox(codePoints, 0, codePoints.length)
			.split(rd, width, context, first);
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