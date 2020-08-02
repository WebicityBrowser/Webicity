package everyos.browser.webicitybrowser;

import everyos.engine.ribbon.core.rendering.RenderingEngine;
import everyos.engine.ribbon.renderer.awtrenderer.RibbonAWTMonitor;
import everyos.engine.ribbon.renderer.awtrenderer.RibbonAWTMonitor.NoMonitorAvailableException;
import everyos.engine.ribbon.renderer.awtrenderer.RibbonAWTRenderingEngine;
import everyos.engine.ribbon.renderer.awtrenderer.RibbonAWTWindow;

public class WebicityInstance {
	public WebicityInstance() throws NoMonitorAvailableException {
		RenderingEngine engine = new RibbonAWTRenderingEngine();
		RibbonAWTMonitor monitor = new RibbonAWTMonitor(0);
		
		engine.queueRenderingTask(()->{
			System.out.println(1);
			RibbonAWTWindow window = monitor.createWindow();
			System.out.println(2);
			//window.attribute("onclose", ()->window.close());
			
			new WebicityGraphicalSession(window, engine);
		});
	}
}
