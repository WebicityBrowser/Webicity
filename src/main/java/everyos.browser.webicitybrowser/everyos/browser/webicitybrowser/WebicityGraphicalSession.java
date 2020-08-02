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
import everyos.engine.ribbon.renderer.awtrenderer.RibbonAWTWindow;
import everyos.engine.ribbon.renderer.guirenderer.GUIComponentUI;
import everyos.engine.ribbon.renderer.guirenderer.directive.SizeDirective;
import everyos.engine.ribbon.renderer.guirenderer.shape.Location;

public class WebicityGraphicalSession {
	public WebicityGraphicalSession(RibbonAWTWindow window, RenderingEngine rengine) {
		/*window
			.attribute("title", "Webicity Browser")
			.attribute("min-size", new Location(0, 400, 0, 400))
			.attribute("decorated", false)
			;*/
		
		UIManager<GUIComponentUI> mgr = WebicityUIManager.createUI();
		
		WindowFrame wframe = new WindowFrame(null);
		wframe.getDisplayPane();
		
		window.bind(wframe, mgr);
		
		WebicityEngine engine = new WebicityEngine() {
			@Override public Response processRequest(Request request) throws IOException {
				return request.send();
			}

			@Override public RenderingEngine getRenderingEngine() {
				return rengine;
			}
		};
		
		engine.registerDefaultProtocols();
		
		/*Runnable oldClose = (Runnable) window.getAttribute("onclose");
		window.attribute("onclose", ()->{
			engine.quit();
			if (oldClose!=null) oldClose.run();
		});
		
		contentPane.attribute("onclose", ()->{
			engine.quit();
			if (oldClose!=null) oldClose.run();
		});
		contentPane.attribute("onminimize", ()->{
			window.minimize();
		});
		contentPane.attribute("onrestore", ()->{
			window.restore();
		});*/
		
		TabFrame tab = new TabFrame(wframe.getDisplayPane(), engine);
		tab.directive(SizeDirective.of(new Location(1, 0, 1, 0)));
		
		window.setVisible(true);
	}
}
