package everyos.browser.webicity.core.coroutines;

public final class CoroutineRepository {
	
	private CoroutineRepository() {}

	private static ThreadLocal<Coroutine> threadCoroutine = new ThreadLocal<>();
	
	public static void setThreadCoroutine(Coroutine coroutine) {
		threadCoroutine.set(coroutine);
	};
	
	public static void removeThreadCoroutine() {
		threadCoroutine.remove();
	}
	
	public static Coroutine getThreadCoroutine() {
		return threadCoroutine.get();
	}
	
}
