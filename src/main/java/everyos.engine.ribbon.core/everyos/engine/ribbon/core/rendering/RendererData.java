package everyos.engine.ribbon.core.rendering;

import everyos.engine.ribbon.core.graphics.GUIState;
import everyos.engine.ribbon.core.graphics.paintfill.PaintFill;

public interface RendererData {

	RendererData getSubcontext(int x, int y, int l, int h);
	SharedRendererContext getSharedContext();
	
	GUIState getState();
	void restoreState(GUIState state);
	
	void useBackground();
	void useForeground();
	PaintFill getCurrentPaintFill();
	
	void translate(int x, int y);
	
	//TODO: Should we get rid of these?
	// Not Rectangle-s because these are more lightweight
	int[] getBounds();
	int[] getTranslate();
	int[] getClip();

}
