package everyos.browser.webicitybrowser;

import java.io.IOException;

import everyos.browser.webicity.WebicityEngine;
import everyos.browser.webicity.net.request.Request;
import everyos.browser.webicity.net.response.Response;
import everyos.browser.webicitybrowser.gui.WebicityUIManager;
import everyos.browser.webicitybrowser.gui.component.TabFrame;
import everyos.browser.webicitybrowser.gui.component.WindowFrame;
import everyos.engine.ribbon.core.rendering.RenderingEngine;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.awtrenderer.RibbonAWTMonitor;
import everyos.engine.ribbon.renderer.awtrenderer.RibbonAWTMonitor.NoMonitorAvailableException;
import everyos.engine.ribbon.renderer.guirenderer.GUIComponentUI;
import everyos.engine.ribbon.renderer.guirenderer.directive.SizeDirective;
import everyos.engine.ribbon.renderer.guirenderer.shape.Location;
import everyos.engine.ribbon.renderer.awtrenderer.RibbonAWTRenderingEngine;
import everyos.engine.ribbon.renderer.awtrenderer.RibbonAWTWindow;

public class WebicityInstance {
	public WebicityInstance() throws NoMonitorAvailableException {
		RenderingEngine renderingEngine = new RibbonAWTRenderingEngine();
		RibbonAWTMonitor monitor = new RibbonAWTMonitor(0);
		
		renderingEngine.queueRenderingTask(()->{
			RibbonAWTWindow window = monitor.createWindow();
			
			window.setTitle("Webicity Browser");
			window.setIcon("webicity.png");
			window.setMinSize(new Location(0, 600, 0, 400));
			window.setDecorated(false);
		
			UIManager<GUIComponentUI> mgr = WebicityUIManager.createUI();
			
			WindowFrame wframe = new WindowFrame(null);		
			window.bind(wframe, mgr);
			
			WebicityEngine engine = new WebicityEngine() {
				@Override public Response processRequest(Request request) throws IOException {
					return request.send();
				}
	
				@Override public RenderingEngine getRenderingEngine() {
					return renderingEngine;
				}
			};
			
			engine.registerDefaultProtocols();
			
			wframe.onClose(()->{
				engine.quit();
				window.close();
			});
			wframe.onMinimize(()->{
				window.minimize();
			});
			wframe.onRestore(()->{
				window.restore();
			});
			
			TabFrame tab = new TabFrame(wframe.getDisplayPane(), engine);
			tab.directive(SizeDirective.of(new Location(1, 0, 1, 0)));
			
			window.setVisible(true);
		});
	}
}
