package everyos.engine.ribbon.core.util;

// TODO: Better name, and not TimeUtil, because everyos.browser.javadom.imp.TimeUtil
public class TimeSystem {

    private static long lastTime = System.currentTimeMillis();
    private static long delta = -1;

    public static void step() {
        long newTime = System.currentTimeMillis();
        delta = newTime - lastTime;
        lastTime = newTime;
    }

    public static long getDeltaMillis() {
        return delta;
    }

    public static float getDeltaSeconds() {
        return getDeltaMillis()/1000f;
    }
}
