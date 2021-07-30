package everyos.browser.webicitybrowser.gui.animation;

import everyos.browser.webicitybrowser.gui.Animation;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.util.TimeSystem;

public class SlideInAnimation implements Animation {
	private static final double animationTime = 500;
  	private float progress = 0;
	
	@Override
	public Renderer step(Renderer r, boolean visible, Dimension bounds) {
		if (visible) {
			if (progress < 1) {
				progress += TimeSystem.getDeltaMillis()/animationTime;
			}
		} else {
			if (progress > 0) {
				progress -= TimeSystem.getDeltaMillis()/animationTime;
			}
		}
		
		if (progress > 1) {
			progress = 1;
		}
		
		int slideHeight = (int) (bounds.getHeight() * progress);
		Renderer r2 = r.getSubcontext(0, 0, bounds.getWidth(), slideHeight);
		r2.translate(0, -(bounds.getHeight()-slideHeight));
		
		return r2;
	}
}
