package everyos.engine.ribbon.core.rendering;

import everyos.engine.ribbon.core.graphics.Color;
import everyos.engine.ribbon.core.graphics.GUIState;
import everyos.engine.ribbon.core.shape.Rectangle;

public class RendererData {
	private final int[] bounds;
	private final int[] clip;
	
	private GUIState state;
	private Color currentColor;
	private int[] translate;
	
	public RendererData(GUIState state, int[] bounds, int[] clip) {
		this.bounds = bounds;
		this.translate = new int[] {0, 0};
		this.clip = clip;
		
		restoreState(state);
	}

	public void restoreState(GUIState state) {
		this.state = state;
	}
	
	public void translate(int x, int y) {
		translate[0] += x;
		translate[1] += y;
	}
	
	public void useBackground() {
		currentColor = state.getBackground();
	}
	
	public void useForeground() {
		currentColor = state.getForeground();
	}
	
	public GUIState getState() {
		return state;
	}
	
	public int[] getBounds() {
		// Not a Rectangle because this is more lightweight
		return bounds;
	}
	
	public Color getCurrentColor() {
		return currentColor;
	}
	
	public int getTranslateX() {
		return translate[0];
	}
	
	public int getTranslateY() {
		return translate[1];
	}
	
	public int[] getClip() {
		return clip;
	}
	
	public RendererData getSubcontext(int x, int y, int width, int height) {
		int[] bounds = new int[] {this.bounds[0]+x, this.bounds[1]+y+translate[1], width, height};
		int[] clip = Rectangle.intersectRaw(this.clip, bounds);
		
		return new RendererData(state, bounds, clip);
	}
}
