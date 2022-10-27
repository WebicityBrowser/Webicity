package everyos.browser.webicity.coroutines;

public interface Coroutine {

	void resume();
	
	void pause();
	
	CoroutineStatus getStatus();
	
	public static void yield() {
		CoroutineRepository.getThreadCoroutine().pause();
	}
	
	public static enum CoroutineStatus {
		NEW, IDLE, ACTIVE, DONE, DEAD
	}
	
}
