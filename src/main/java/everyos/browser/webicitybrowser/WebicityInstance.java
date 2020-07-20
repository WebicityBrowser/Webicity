package everyos.browser.webicitybrowser;

import everyos.engine.ribbon.graphics.RenderingEngine;
import everyos.engine.ribbonawt.RibbonAWTMonitor;
import everyos.engine.ribbonawt.RibbonAWTMonitor.NoMonitorAvailableException;
import everyos.engine.ribbonawt.RibbonAWTRenderingEngine;
import everyos.engine.ribbonawt.RibbonAWTWindowComponent;

public class WebicityInstance {
	public WebicityInstance() throws NoMonitorAvailableException {
		RenderingEngine engine = new RibbonAWTRenderingEngine();
		RibbonAWTMonitor monitor = new RibbonAWTMonitor(0);
		
		engine.queueRenderingTask(()->{
			RibbonAWTWindowComponent window = monitor.createWindow();
			window.attribute("onclose", ()->window.close());
			
			new WebicityGraphicalSession(window, engine);
		});
	}
}
