package everyos.engine.ribbon.core.util;

public class TimeSystem {
	
    private long lastTime = System.currentTimeMillis();
    private long delta = -1;

    public void step() {
        long newTime = System.currentTimeMillis();
        delta = newTime - lastTime;
        lastTime = newTime;
    }

    public long getDeltaMillis() {
        return delta;
    }

    public float getDeltaSeconds() {
        return getDeltaMillis() / 1000f;
    }
    
}
