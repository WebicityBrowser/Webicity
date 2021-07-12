package everyos.browser.webicity.concurrency.jroutine;

public class JRoutine {
	private static ThreadLocal<JRoutine> routine = new ThreadLocal<>();
	
	public static JRoutine create(Runnable r) {
		return new JRoutine(r);
	}

	private JRoutineStatus status = JRoutineStatus.IDLE;
	private Object lockA = new Object();
	private Object lockB = new Object();
	private int curThread = 0;
	
	private JRoutine(Runnable r) {
		JRoutine self = this;
		
		new Thread(()->{
			routine.set(self);
			
			try {
				synchronized (lockB) {
					if (curThread == 0) {
						lockB.wait();
					}
				}
				r.run();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			this.status = JRoutineStatus.DEAD;
			
			synchronized(lockA) {
				lockA.notify();
			}
		}).start();
	}
	
	public JRoutineStatus getStatus() {
		return this.status;
	}
	
	// This logic may be wrong, but so far it seems to work
	// TODO: Specifically, there is a race condition between checking curThread and calling wait
	// I dunno how bad this is
	
	public void resume() {
		synchronized (lockB) {
			curThread = 1;
			lockB.notify();
		}
		synchronized (lockA) {
			try {
				if (curThread==1) {
					lockA.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void yield() {	
		synchronized (lockA) {
			curThread = 0;
			lockA.notify();
		}
		synchronized (lockB) {
			try {
				if (curThread == 0) {
					lockB.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static JRoutine getJRoutine() {
		return routine.get();
	}
}
