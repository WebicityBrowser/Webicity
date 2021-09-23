package everyos.engine.ribbon.renderer.skijarenderer;

public class RibbonSkijaMonitor {
	
	private final int id;
	
	public RibbonSkijaMonitor(int id) throws NoMonitorAvailableException {
		if (id < 0) {
			throw new IllegalArgumentException();
		}
		this.id = id;
	}
	
	public RibbonSkijaMonitor() throws NoMonitorAvailableException {
		this.id = 0;
	}
	
	public RibbonSkijaWindow createWindow() {
		return new RibbonSkijaWindow(id);
	}
	
	public static class NoMonitorAvailableException extends Exception {
		private static final long serialVersionUID = -3568179825324762957L;
	}
}
