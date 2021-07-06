package everyos.browser.webicitybrowser.util;

// TODO: Better name, and not TimeUtil, because everyos.browser.javadom.imp.TimeUtil
public class TimeSystem {

    private static long lastTime = -1;
    private static long delta = -1;

    public static void reset() {
        lastTime = System.currentTimeMillis();
    }

    public static void step() {
        long newTime = System.currentTimeMillis();
        delta = newTime - lastTime;
        lastTime = newTime;
    }

    public static long getDeltaMillis() {
        return delta;
    }

    public static float getDeltaSeconds() {
        return delta/1000f;
    }
}
