package everyos.browser.webicitybrowser;

import everyos.engine.ribbon.renderer.awtrenderer.RibbonAWTMonitor.NoMonitorAvailableException;

public class Webicity {
	public static void main(String[] args) {
		try {
			WebicityInstance instance = new WebicityInstance();
		} catch (NoMonitorAvailableException e) {
			e.printStackTrace();
		}
	}
}
