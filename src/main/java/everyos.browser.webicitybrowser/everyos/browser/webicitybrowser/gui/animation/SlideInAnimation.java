package everyos.browser.webicitybrowser.gui.animation;

import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Dimension;

public class SlideInAnimation implements Animation {
	
	private static final double animationTime = 500;
	
  	private float progress = 0;
	
	@Override
	public RendererData step(RendererData rd, boolean visible, Dimension bounds) {
		long deltaMillis = rd.getSharedContext().getTimeSystem().getDeltaMillis();
		if (visible) {
			if (progress < 1) {
				progress += deltaMillis / animationTime;
			}
		} else {
			if (progress > 0) {
				progress -= deltaMillis / animationTime;
			}
		}
		
		if (progress > 1) {
			progress = 1;
		}
		
		int slideHeight = (int) (bounds.getHeight() * progress);
		RendererData r2 = rd.getSubcontext(0, 0, bounds.getWidth(), slideHeight);
		r2.translate(0, -(bounds.getHeight()-slideHeight));
		
		return r2;
	}
	
}
