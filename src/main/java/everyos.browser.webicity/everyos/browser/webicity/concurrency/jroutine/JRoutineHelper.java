package everyos.browser.webicity.concurrency.jroutine;

public final class JRoutineHelper {
	private JRoutineHelper() {}
	
	public static void finish(JRoutine routine) {
		while (routine.getStatus() != JRoutineStatus.DEAD) {
			routine.resume();
		}
	}
}
