package everyos.engine.ribbon.renderer.skijarenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.lwjgl.glfw.GLFW;

import everyos.engine.ribbon.core.util.TimeSystem;

public class RenderingThread {
	private static final Semaphore lock = new Semaphore(0);
	private static List<Runnable> tasks = new ArrayList<>();
	
	static {
		new Thread(()->{
			try {
				lock.acquire();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
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
			lock.release();
		}
	}
}
