package everyos.browser.webicitybrowser.gui.component;

import everyos.browser.webicity.WebicityEngine;
import everyos.browser.webicity.WebicityFrame;
import everyos.browser.webicity.event.NavigateEvent;
import everyos.browser.webicity.handlers.NavigateHandler;
import everyos.browser.webicitybrowser.gui.Styling;
import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.BreakComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.renderer.guirenderer.directive.PositionDirective;
import everyos.engine.ribbon.renderer.guirenderer.directive.SizeDirective;
import everyos.engine.ribbon.renderer.guirenderer.event.MouseEvent;
import everyos.engine.ribbon.renderer.guirenderer.event.MouseListener;
import everyos.engine.ribbon.renderer.guirenderer.graphics.Color;
import everyos.engine.ribbon.renderer.guirenderer.shape.Location;

public class TabFrame extends BlockComponent {
	public TabFrame(Component parent, WebicityEngine engine) {
		super(parent);
		
		NavigateHandler navigator = new NavigateHandler() {
			@Override public void navigate(NavigateEvent ev) {
				engine.getRenderingEngine().queueRenderingTask(()->{
					getURLBar().attribute("text", ev.getURL().toString());
				});
			}
		};
		
		this
			.attribute("bg-color", Color.WHITE)
			.children(new Component[] {
				new BlockComponent(null)
				.directive(SizeDirective.of(new Location(1, 0, 0, 32)))
					.attribute("bg-color", new Color(30, 30, 25))
					.attribute("fg-color", Color.WHITE)
					.attribute("font-size", 16)
					.children(new Component[] {
						new CircularText(null)
							.text("<")
							.directive(PositionDirective.of(new Location(0, Styling.PADDING, 0, 3)))
							.directive(SizeDirective.of(new Location(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)))
							.attribute("bg-color", Color.DARK_GRAY)
							.attribute("onrelease", new MouseListener() {
								@Override public void accept(MouseEvent e) {
									if (e.getButton()==MouseEvent.LEFT_BUTTON) {
										
									}
								}
							}),
						new CircularText(null)
							.text(">")
							.directive(PositionDirective.of(new Location(0, Styling.BUTTON_WIDTH+Styling.PADDING*2, 0, 3)))
							.directive(SizeDirective.of(new Location(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)))
							.attribute("bg-color", Color.DARK_GRAY)
							.attribute("onrelease", new MouseListener() {
								@Override public void accept(MouseEvent e) {
									if (e.getButton()==MouseEvent.LEFT_BUTTON) {
										
									}
								}
							}),
						new CircularText(null)
							.text("x")
							.directive(PositionDirective.of(new Location(0, (Styling.BUTTON_WIDTH+Styling.PADDING)*2+Styling.PADDING, 0, 3)))
							.directive(SizeDirective.of(new Location(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)))
							.attribute("bg-color", Color.DARK_GRAY)
							.attribute("onrelease", new MouseListener() {
								@Override public void accept(MouseEvent e) {
									if (e.getButton()==MouseEvent.LEFT_BUTTON) {
										getBrowserComponent().tasks.quit();
									}
								}
							}),
						new URLBar(null)
							.attribute("id", "url-bar")
							.directive(SizeDirective.of(new Location(1, -(Styling.BUTTON_WIDTH+Styling.PADDING)*3-2*Styling.PADDING, 0, Styling.BUTTON_WIDTH)))
							.directive(PositionDirective.of(new Location(0, (Styling.BUTTON_WIDTH+Styling.PADDING)*3+Styling.PADDING, 0, 3)))
							.attribute("bg-color", Color.DARK_GRAY)
					}),
				new BreakComponent(null),
				new BlockComponent(null)
					.directive(SizeDirective.of(new Location(1, 0, 1, -32)))
					.attribute("id", "display-pane")
					.attribute("bg-color", Color.LIGHT_GRAY)
					.children(new Component[] {
						new WebicityFrame(null, engine, navigator)
							.attribute("id", "browser-component")
							.directive(SizeDirective.of(new Location(1, 0, 1, 0)))
							//.attribute("url", "file:C:\\Users\\jason\\Documents\\notes.txt")
							//.attribute("url", "https://www.w3.org/TR/PNG/iso_8859-1.txt")
							//.attribute("url", "https://www.google.com/")
							//.attribute("url", "https://www.yahoo.com/")
							//.attribute("url", "file:C:\\Users\\jason\\Downloads\\sample-2mb-text-file (1).txt")
							//.attribute("url", "file:C:\\Users\\jason\\Downloads\\test.txt")
							//.attribute("url", "test://test")
							//.attribute("url", "file:C:\\Users\\jason\\Downloads\\test4.html")
							.attribute("url", "https://www.google.com/")
							//.attribute("url", "https://www.wikipedia.org/")
							//.attribute("url", "https://discord.com/new")
					})
			});
	}
	
	public Component getURLBar() {
		return getComponentByID("url-bar");
	}
	public Component getDisplayPane() {
		return getComponentByID("display-pane");
	}
	public WebicityFrame getBrowserComponent() {
		return (WebicityFrame) getComponentByID("browser-component");
	}

	public void navigate(String url) {
		/*getURLBar().attribute("text", " "+url);*/
		getBrowserComponent().attribute("url", url);
	}
}
