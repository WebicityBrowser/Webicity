package everyos.browser.webicity.concurrency.jroutine;

import java.util.concurrent.Semaphore;

public class JRoutine {
	
	private static final ThreadLocal<JRoutine> routine = new ThreadLocal<>();

	private final Semaphore lockA = new Semaphore(0);
	private final Semaphore lockB = new Semaphore(1);
	
	private JRoutineStatus status = JRoutineStatus.IDLE;
	
	private JRoutine(Runnable r) {
		JRoutine self = this;
		
		new Thread(() -> {
			routine.set(self);
			
			try {
				lockB.acquireUninterruptibly();
				r.run();
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			this.status = JRoutineStatus.DEAD;
			
			lockA.release();
		}).start();
	}
	
	public void resume() {
		if (status == JRoutineStatus.DEAD) {
			throw new RuntimeException("Attempt to resume a dead coroutine");
		}
		
		status = JRoutineStatus.ACTIVE;
		lockB.release();
		lockA.acquireUninterruptibly();
	}
	
	public void yield() {
		status = JRoutineStatus.IDLE;
		lockA.release();
		lockB.acquireUninterruptibly();
	}
	
	public JRoutineStatus getStatus() {
		return this.status;
	}

	public static JRoutine getJRoutine() {
		return routine.get();
	}
	
	public static JRoutine create(Runnable r) {
		return new JRoutine(r);
	}
	
}
