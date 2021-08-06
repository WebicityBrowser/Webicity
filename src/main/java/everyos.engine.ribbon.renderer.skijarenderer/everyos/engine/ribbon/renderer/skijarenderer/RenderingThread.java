package everyos.engine.ribbon.renderer.skijarenderer;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import everyos.engine.ribbon.core.util.TimeSystem;

public class RenderingThread {
	private static List<Runnable> tasks = new ArrayList<>();
	private static volatile boolean mustWait = true;
	
	static {
		new Thread(()->{
			try {
				synchronized (tasks) {
					if (mustWait) {
						tasks.wait();
						mustWait = false;
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while (tasks.size() > 0) {
				List<Runnable> curTasks;
				synchronized (tasks) {
					curTasks = tasks;
					tasks = new ArrayList<>();
				}
				for (Runnable task: curTasks) {
					task.run();
				}
				TimeSystem.step();
				GLFW.glfwPollEvents();
			}
		}, "GLEventThread").start();
	}

	public static void run(Runnable action) {
		synchronized (tasks) {
			tasks.add(action);
			tasks.notify();
			mustWait = false;
		}
	}
}
