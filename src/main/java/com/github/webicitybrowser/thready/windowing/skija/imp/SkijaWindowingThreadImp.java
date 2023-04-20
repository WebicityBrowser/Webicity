package com.github.webicitybrowser.thready.windowing.skija.imp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

import org.lwjgl.glfw.GLFW;

import com.github.webicitybrowser.thready.windowing.core.Window;
import com.github.webicitybrowser.thready.windowing.skija.SkijaGraphicsSystem;
import com.github.webicitybrowser.thready.windowing.skija.SkijaWindow;
import com.github.webicitybrowser.thready.windowing.skija.SkijaWindowingThread;

public class SkijaWindowingThreadImp implements SkijaWindowingThread {
	
	private final Queue<Consumer<Window>> windowsToStart = new ConcurrentLinkedQueue<>();
	private final List<SkijaWindow> windows = Collections.synchronizedList(new ArrayList<>());
	private final SkijaGraphicsSystem graphicsSystem;
	
	private Thread runnerThread;
	
	public SkijaWindowingThreadImp(SkijaGraphicsSystem graphicsSystem) {
		this.graphicsSystem = graphicsSystem;
	}
	
	@Override
	public void createWindow(Consumer<Window> callback) {
		windowsToStart.add(callback);
		startRunnerThreadIfNotActive();
	}

	private void startRunnerThreadIfNotActive() {
		if (runnerThread == null) {
			startRunnerThread();
		}
	}

	private void startRunnerThread() {
		new Thread(() -> {
			while (continueRunning()) {
				GLFW.glfwPollEvents();
				startQueuedWindows();
				continueRunningScreens();
			}
		}).start();
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
