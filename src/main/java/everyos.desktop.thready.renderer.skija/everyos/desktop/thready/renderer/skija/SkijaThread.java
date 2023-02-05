package everyos.desktop.thready.renderer.skija;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.lwjgl.glfw.GLFW;

public final class SkijaThread {
	
	private static final List<Supplier<SkijaScreen>> startQueue = new ArrayList<>();
	private static final List<SkijaScreen> screens = new ArrayList<>();

	private SkijaThread() {}
	
	public static void addScreen(Supplier<SkijaScreen> screenSupplier) {
		synchronized (startQueue) {
			startQueue.add(screenSupplier);
			if (startQueue.size() == 1) {
				startRunnerThread();
			}
		}
	}
	
	private static void startRunnerThread() {
		new Thread(() -> {
			while (continueRunning()) {
				GLFW.glfwPollEvents();
				startQueuedScreens();
				continueRunningScreens();
			}
		}).start();
	}

	private static boolean continueRunning() {
		return !(startQueue.isEmpty() && screens.isEmpty());
	}

	private static void startQueuedScreens() {
		synchronized (startQueue) {
			for (Supplier<SkijaScreen> screenStarter: startQueue) {
				screens.add(screenStarter.get());
			}
			startQueue.clear();
		}
	}
	
	private static void continueRunningScreens() {
		for (SkijaScreen screen: List.copyOf(screens)) {
			if (screen.isDone()) {
				screen.close();
				screens.remove(screen);
				continue;
			}
			
			screen.tick();
		}
	}
	
}
