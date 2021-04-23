package everyos.engine.ribbon.renderer.awtrenderer;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;

public class RibbonAWTMonitor {
	private GraphicsDevice screen;
	private void setScreen(GraphicsDevice screen) {
		this.screen = screen;
	}
	public RibbonAWTMonitor(int id) throws NoMonitorAvailableException {
		if (id<0) throw new IllegalArgumentException();
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] devices = env.getScreenDevices();
		if (id>=devices.length) throw new NoMonitorAvailableException();
		setScreen(devices[id]);
	}
	public RibbonAWTMonitor() throws NoMonitorAvailableException {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			setScreen(env.getDefaultScreenDevice());
		} catch(HeadlessException e) {
			throw new NoMonitorAvailableException();
		}
	}
	
	public RibbonAWTWindow createWindow() {
		return new RibbonAWTWindow(screen)
			/*.attribute("monitor", this)
			.attribute("size", new Dimension(800, 500))*/;
	}
	
	public static class NoMonitorAvailableException extends Exception {
		private static final long serialVersionUID = -3568179825324762957L;
	}
	
	
}
