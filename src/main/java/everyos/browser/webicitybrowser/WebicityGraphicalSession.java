package everyos.browser.webicitybrowser;

import java.io.IOException;

import everyos.browser.webicity.WebicityEngine;
import everyos.browser.webicity.net.request.Request;
import everyos.browser.webicity.net.response.Response;
import everyos.browser.webicitybrowser.gui.WebicityUIManager;
import everyos.browser.webicitybrowser.gui.component.TabFrame;
import everyos.browser.webicitybrowser.gui.component.WindowFrame;
import everyos.engine.ribbon.graphics.RenderingEngine;
import everyos.engine.ribbon.graphics.component.Component;
import everyos.engine.ribbon.graphics.ui.UIManager;
import everyos.engine.ribbon.shape.Location;
import everyos.engine.ribbonawt.RibbonAWTWindowComponent;

public class WebicityGraphicalSession {
	public WebicityGraphicalSession(RibbonAWTWindowComponent window, RenderingEngine rengine) {
		window
			.attribute("title", "Webicity Browser")
			.attribute("min-size", new Location(0, 400, 0, 400))
			.attribute("decorated", false)
			;
		
		UIManager mgr = WebicityUIManager.createUI();
		window.setUIManager(mgr);
		
		Component contentPane = window.createDefaultComponent();
		WindowFrame wframe = new WindowFrame(contentPane);
		wframe.getDisplayPane();
		
		WebicityEngine engine = new WebicityEngine() {
			@Override public Response processRequest(Request request) throws IOException {
				return request.send();
			}

			@Override public RenderingEngine getRenderingEngine() {
				return rengine;
			}
		};
		
		engine.registerDefaultProtocols();
		
		Runnable oldClose = (Runnable) window.getAttribute("onclose");
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
		});
		
		TabFrame tab = new TabFrame(wframe.getDisplayPane(), engine);
		tab.attribute("size", new Location(1, 0, 1, 0));
		
		window.attribute("visible", true);
	}
}
