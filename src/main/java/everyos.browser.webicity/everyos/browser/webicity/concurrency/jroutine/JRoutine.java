package everyos.browser.webicity.concurrency.jroutine;

import java.util.concurrent.Semaphore;

public class JRoutine {
	private static final ThreadLocal<JRoutine> routine = new ThreadLocal<>();

	private final Semaphore lockA = new Semaphore(0);
	private final Semaphore lockB = new Semaphore(1);
	
	private JRoutineStatus status = JRoutineStatus.IDLE;
	
	private JRoutine(Runnable r) {
		JRoutine self = this;
		
		new Thread(()->{
			routine.set(self);
			
			try {
				lockB.acquire();
				r.run();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			this.status = JRoutineStatus.DEAD;
			
			lockA.release();
		}).start();
	}
	
	// This logic may be wrong, but so far it seems to work
	// TODO: Specifically, there is a race condition between checking curThread and calling wait
	// I dunno how bad this is
	
	public void resume() {
		lockB.release();
		
		try {
			lockA.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void yield() {	
		lockA.release();
		
		try {
			lockB.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
