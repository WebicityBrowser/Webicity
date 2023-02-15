package everyos.browser.webicity.core.coroutines;

import java.util.concurrent.Semaphore;

public class JRoutine implements Coroutine {
	
	private final Thread thread;
	
	private final Semaphore callerLock = new Semaphore(0);
	private final Semaphore coroutineLock = new Semaphore(0);
	
	private CoroutineStatus status = CoroutineStatus.NEW;
	private Throwable exception;

	public JRoutine(Runnable task) {
		this.thread = createThread(task);
	}

	@Override
	public void resume() {
		ensureCoroutineNotDead();
		ensureCoroutineStarted();
		transferControl();
		rethrowIfException();
		markIdleCoroutine();
	}

	@Override
	public void pause() {
		callerLock.release();
		coroutineLock.acquireUninterruptibly();
	}

	@Override
	public CoroutineStatus getStatus() {
		return status;
	}
	
	private void ensureCoroutineNotDead() {
		if (status == CoroutineStatus.DONE || status == CoroutineStatus.DEAD) {
			throw new RuntimeException("Cannot resume dead coroutine!");
		}
	}
	
	private void ensureCoroutineStarted() {
		if (status == CoroutineStatus.NEW) {
			thread.start();
		}
	}
	
	private void transferControl() {
		status = CoroutineStatus.ACTIVE;
		coroutineLock.release();
		callerLock.acquireUninterruptibly();
	}
	
	private void rethrowIfException() {
		if (exception != null) {
			throw new RuntimeException(exception);
		}
	}
	
	private void markIdleCoroutine() {
		if (status == CoroutineStatus.ACTIVE) {
			status = CoroutineStatus.IDLE;
		}
	}
	
	private Thread createThread(Runnable task) {
		return new Thread(() -> start(task));
	}

	private void start(Runnable task) {
		coroutineLock.acquireUninterruptibly();
		CoroutineRepository.setThreadCoroutine(this);
		
		try {
			task.run();
		} catch (Throwable e) {
			exception = e;
			status = CoroutineStatus.DEAD;
			callerLock.release();
			return;
		}
		
		status = CoroutineStatus.DONE;
		callerLock.release();
	}

}
