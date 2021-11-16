package everyos.engine.ribbon.renderer.skijarenderer;

import everyos.engine.ribbon.core.event.UIEventTarget;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.rendering.SharedRendererContext;
import everyos.engine.ribbon.core.util.TimeSystem;

public class RibbonSkijaSharedRendererContext implements SharedRendererContext {

	private final TimeSystem timeSystem = new TimeSystem();
	
	private UIEventTarget focusedTarget;
	
	@Override
	public TimeSystem getTimeSystem() {
		return timeSystem;
	}
	
	@Override
	public void delayInvalidate(long millis, InvalidationLevel level) {
		//TODO
	}

	@Override
	public void setFocusedEventTarget(UIEventTarget target) {
		this.focusedTarget = target;
	}
	
	@Override
	public UIEventTarget getFocusedEventTarget() {
		return this.focusedTarget;
	}

}
