package everyos.browser.webicitybrowser.gui.window;

import everyos.browser.webicitybrowser.gui.WebicityUIManager;
import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.directive.SizeDirective;
import everyos.engine.ribbon.core.shape.Location;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.skijarenderer.RibbonSkijaMonitor;
import everyos.engine.ribbon.renderer.skijarenderer.RibbonSkijaMonitor.NoMonitorAvailableException;
import everyos.engine.ribbon.renderer.skijarenderer.RibbonSkijaWindow;

public class SkijaWindow implements RibbonWindow {
	private RibbonSkijaWindow window;
	private Component displayPane;

	private SkijaWindow(RibbonSkijaWindow window, Component displayPane) {
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
	
	public void restore() {
		window.restore();
	}
	
	public static SkijaWindow create() {
		RibbonSkijaMonitor monitor;
		try {
			monitor = new RibbonSkijaMonitor(0);
		} catch (NoMonitorAvailableException e) {
			throw new WindowCreationFailureException(e);
		}
		
		RibbonSkijaWindow window = monitor.createWindow();
		
		window.setTitle("Webicity Browser");
		window.setIcon("webicity.png");
		window.setMinSize(new Location(0, 600, 0, 400));
		//window.setDecorated(false);
	
		UIManager mgr = WebicityUIManager.createUI();
		
		BlockComponent component = new BlockComponent();
		component.directive(SizeDirective.of(new Location(1, 0, 1, 0)));
		window.bind(component, mgr);
		window.setVisible(true);
			
		return new SkijaWindow(window, component);
	}
}
