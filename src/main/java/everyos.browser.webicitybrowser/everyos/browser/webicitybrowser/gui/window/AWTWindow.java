package everyos.browser.webicitybrowser.gui.window;

import everyos.browser.webicitybrowser.gui.WebicityUIManager;
import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.directive.SizeDirective;
import everyos.engine.ribbon.core.shape.Location;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.awtrenderer.RibbonAWTMonitor;
import everyos.engine.ribbon.renderer.awtrenderer.RibbonAWTMonitor.NoMonitorAvailableException;
import everyos.engine.ribbon.renderer.awtrenderer.RibbonAWTWindow;

public class AWTWindow implements RibbonWindow {
	private RibbonAWTWindow window;
	private Component displayPane;

	private AWTWindow(RibbonAWTWindow window, Component displayPane) {
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
	
	public static AWTWindow create() {
		RibbonAWTMonitor monitor;
		try {
			monitor = new RibbonAWTMonitor(0);
		} catch (NoMonitorAvailableException e) {
			throw new WindowCreationFailureException(e);
		}
		
		RibbonAWTWindow window = monitor.createWindow();
		
		window.setTitle("Webicity Browser");
		window.setIcon("webicity.png");
		window.setMinSize(new Location(0, 800, 0, 600));
		//window.setDecorated(false);
	
		UIManager mgr = WebicityUIManager.createUI();
		
		BlockComponent component = new BlockComponent();	
		component.directive(SizeDirective.of(new Location(1, 0, 1, 0)));
		window.bind(component, mgr);
		
		Component displayPane = new BlockComponent().directive(SizeDirective.of(new Location(1, 0, 1, 0)));
		component.addChild(displayPane);
		
		window.setVisible(true);
			
		return new AWTWindow(window, displayPane);
	}
}
