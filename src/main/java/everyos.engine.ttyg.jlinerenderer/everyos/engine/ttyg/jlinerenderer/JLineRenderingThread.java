package everyos.engine.ttyg.jlinerenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class JLineRenderingThread {

	private static final Semaphore lock = new Semaphore(0);
	private static final Thread thread;
	
	private static List<Runnable> tasks = new ArrayList<>();
	
	static {
		thread = new Thread(()->{
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
			}
		}, "JLineEventThread");
		thread.start();
	}

	public static void run(Runnable action) {
		synchronized(tasks) {
			tasks.add(action);
			lock.release();
		}
	}

	public static boolean isCurrent() {
		return thread == Thread.currentThread();
	}
	
}
