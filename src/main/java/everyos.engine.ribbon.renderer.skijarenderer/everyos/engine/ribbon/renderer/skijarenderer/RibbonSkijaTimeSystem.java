package everyos.engine.ribbon.renderer.skijarenderer;

import everyos.engine.ribbon.core.util.TimeSystem;

public class RibbonSkijaTimeSystem implements TimeSystem {
	
	private long lastTime = System.currentTimeMillis();
	private long delta = -1;
	
	@Override
	public long getDeltaMillis() {
		return delta;
	}
	
	@Override
	public float getDeltaSeconds() {
		return getDeltaMillis() / 1000f;
	}
	
	public void step() {
		long newTime = System.currentTimeMillis();
		delta = newTime - lastTime;
		lastTime = newTime;
	}

}
