package everyos.browser.webicitybrowser.gui.window;

import everyos.browser.webicitybrowser.gui.WebicityUIManager;
import everyos.engine.ribbon.components.component.BlockComponent;
import everyos.engine.ribbon.components.directive.SizeDirective;
import everyos.engine.ribbon.core.graphics.Component;
import everyos.engine.ribbon.core.shape.Location;
import everyos.engine.ribbon.core.shape.Position;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.skijarenderer.RibbonSkijaMonitor;
import everyos.engine.ribbon.renderer.skijarenderer.RibbonSkijaMonitor.NoMonitorAvailableException;
import everyos.engine.ribbon.renderer.skijarenderer.RibbonSkijaWindow;
import everyos.engine.ribbon.renderer.skijarenderer.RibbonSkijaWindowBuilder;

public class SkijaWindow implements RibbonWindow {
	private RibbonSkijaWindow window;
	private Component displayPane;

	private SkijaWindow(RibbonSkijaWindow window, Component displayPane) {
		this.window = window;	
		this.displayPane = displayPane;
	}
	
	@Override
	public Component getDisplayPane() {
		return displayPane;
	}
	
	@Override
	public void close() {
		window.close();
	}
	
	@Override
	public void minimize() {
		window.minimize();
	}
	
	@Override
	public void restore() {
		window.restore();
	}
	
	@Override
	public Position getPosition() {
		return window.getPosition();
	}

	@Override
	public void setPosition(int x, int y) {
		window.setPosition(x, y);
	}
	
	public static SkijaWindow create() {
		RibbonSkijaMonitor monitor;
		try {
			monitor = new RibbonSkijaMonitor(0);
		} catch (NoMonitorAvailableException e) {
			throw new WindowCreationFailureException(e);
		}
		
		RibbonSkijaWindowBuilder windowBuilder = monitor.createWindowBuilder();
		windowBuilder.setDecorated(false);
		
		RibbonSkijaWindow window = windowBuilder.build();
		
		window.setTitle("Webicity Browser");
		window.setIcon("webicity.png");
		window.setMinSize(new Location(0, 600, 0, 400));
		
		UIManager mgr = WebicityUIManager.createUI();
		BlockComponent component = new BlockComponent();
		component.directive(SizeDirective.of(new Location(1, 0, 1, 0)));
		window.bind(component, mgr);
		
		window.start();
		window.setVisible(true);
			
		return new SkijaWindow(window, component);
	}
	
}
