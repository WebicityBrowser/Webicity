package everyos.engine.ribbon.renderer.skijarenderer;

import everyos.engine.ribbon.core.graphics.GUIState;
import everyos.engine.ribbon.core.graphics.paintfill.PaintFill;
import everyos.engine.ribbon.core.rendering.SharedRendererContext;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;

public class RendererDataImp implements RendererData {
	
	private final int[] bounds;
	private final int[] clip;
	private final SharedRendererContext rendererContext;
	
	private GUIState state;
	private PaintFill currentPaint;
	private int[] translate;
	
	public RendererDataImp(GUIState state, int[] bounds, int[] clip, SharedRendererContext context) {
		this.bounds = bounds;
		this.translate = new int[] { 0, 0 };
		this.clip = clip;
		this.rendererContext = context;
		
		restoreState(state);
	}

	@Override
	public RendererData getSubcontext(int x, int y, int width, int height) {
		int[] bounds = new int[] { this.bounds[0]+x, this.bounds[1]+y+translate[1], width, height };
		int[] clip = Rectangle.intersectRaw(this.clip, bounds);
		
		return new RendererDataImp(state, bounds, clip, rendererContext);
	}

	@Override
	public SharedRendererContext getSharedContext() {
		return rendererContext;
	}
	
	@Override
	public GUIState getState() {
		return state;
	}
	
	@Override
	public void restoreState(GUIState state) {
		this.state = state;
	}
	
	@Override
	public void useBackground() {
		currentPaint = state.getBackground();
	}
	
	@Override
	public void useForeground() {
		currentPaint = state.getForeground();
	}
	
	@Override
	public PaintFill getCurrentPaintFill() {
		return currentPaint;
	}
	
	@Override
	public void translate(int x, int y) {
		translate[0] += x;
		translate[1] += y;
	}
	
	@Override
	public int[] getBounds() {
		return bounds;
	}
	
	@Override
	public int[] getTranslate() {
		return translate;
	}
	
	@Override
	public int[] getClip() {
		return clip;
	}
	
}
