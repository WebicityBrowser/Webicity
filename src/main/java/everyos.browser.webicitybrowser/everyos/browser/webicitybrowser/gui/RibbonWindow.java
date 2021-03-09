package everyos.browser.webicitybrowser.gui;

import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.awtrenderer.RibbonAWTMonitor;
import everyos.engine.ribbon.renderer.awtrenderer.RibbonAWTMonitor.NoMonitorAvailableException;
import everyos.engine.ribbon.renderer.awtrenderer.RibbonAWTWindow;
import everyos.engine.ribbon.renderer.guirenderer.directive.SizeDirective;
import everyos.engine.ribbon.renderer.guirenderer.shape.Location;

public class RibbonWindow {
	public static WindowGrip create() throws NoMonitorAvailableException {
		RibbonAWTMonitor monitor = new RibbonAWTMonitor(0);
		
		RibbonAWTWindow window = monitor.createWindow();
		
		window.setTitle("Webicity Browser");
		window.setIcon("webicity.png");
		window.setMinSize(new Location(0, 600, 0, 400));
		window.setDecorated(false);
	
		UIManager mgr = WebicityUIManager.createUI();
		
		BlockComponent component = new BlockComponent(null);		
		window.bind(component, mgr);
		
		component.directive(SizeDirective.of(new Location(1, 0, 1, 0)));
		
		window.setVisible(true);
		
		Component displayPane = new BlockComponent(component).directive(SizeDirective.of(new Location(1, 0, 1, 0)));
			
		return new WindowGrip(window, displayPane);
	}
	
	public static class WindowGrip {
		private RibbonAWTWindow window;
		private Component displayPane;

		public WindowGrip(RibbonAWTWindow window, Component displayPane) {
			this.window = window;
			this.displayPane = displayPane;
		}
		
		public Component getDisplayPane() {
			return displayPane;
		}
		
		public void close() {
			window.close();
		}
		
		public void minimize() {
			window.minimize();
		}
	}
}
