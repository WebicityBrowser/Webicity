package com.github.webicitybrowser.thready.windowing.skija.imp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

import org.lwjgl.glfw.GLFW;

import com.github.webicitybrowser.thready.gui.graphical.animation.InvalidationScheduler;
import com.github.webicitybrowser.thready.windowing.core.Window;
import com.github.webicitybrowser.thready.windowing.skija.SkijaGraphicsSystem;
import com.github.webicitybrowser.thready.windowing.skija.SkijaWindow;
import com.github.webicitybrowser.thready.windowing.skija.SkijaWindowingThread;

public class SkijaWindowingThreadImp implements SkijaWindowingThread {

	// NOTE: Because MacOS does not support rendering on a non-main thread,
	//	we do not use a separate thread for windowing.
	
	private final Queue<Consumer<Window>> windowsToStart = new ConcurrentLinkedQueue<>();
	private final List<SkijaWindow> windows = Collections.synchronizedList(new ArrayList<>());
	private final SkijaInvalidationScheduler invalidationScheduler = new SkijaInvalidationScheduler();
	private final SkijaGraphicsSystem graphicsSystem;
	
	public SkijaWindowingThreadImp(SkijaGraphicsSystem graphicsSystem) {
		this.graphicsSystem = graphicsSystem;
	}
	
	@Override
	public void createWindow(Consumer<Window> callback) {
		windowsToStart.add(callback);
	}
	
	@Override
	public InvalidationScheduler getInvalidationScheduler() {
		return this.invalidationScheduler;
	}

	@Override
	public void startRenderLoop(Runnable tickHandler) {
		while (continueRunning()) {
			GLFW.glfwPollEvents();
			invalidationScheduler.tick();
			startQueuedWindows();
			continueRunningScreens();
			tickHandler.run();
		}
	}

	private boolean continueRunning() {
		return !(windowsToStart.isEmpty() && windows.isEmpty());
	}

	private void startQueuedWindows() {
		synchronized (windowsToStart) {
			while (!windowsToStart.isEmpty()) {
				Consumer<Window> callback = windowsToStart.poll();
				SkijaWindow window = SkijaWindowImp.create(graphicsSystem);
				windows.add(window);
				callback.accept(window);
			}
		}
	}
	
	private void continueRunningScreens() {
		for (SkijaWindow window: List.copyOf(windows)) {
			if (window.closed()) {
				windows.remove(window);
			} else {
				window.tick();
			}
		}
	}
	
}
