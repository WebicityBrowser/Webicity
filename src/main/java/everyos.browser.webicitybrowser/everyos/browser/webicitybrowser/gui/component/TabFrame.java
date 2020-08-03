package everyos.browser.webicitybrowser.gui.component;

import everyos.browser.webicity.WebicityEngine;
import everyos.browser.webicity.WebicityFrame;
import everyos.browser.webicity.event.NavigateEvent;
import everyos.browser.webicity.handlers.NavigateHandler;
import everyos.browser.webicitybrowser.gui.Styling;
import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.BreakComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.renderer.guirenderer.directive.BackgroundDirective;
import everyos.engine.ribbon.renderer.guirenderer.directive.FontSizeDirective;
import everyos.engine.ribbon.renderer.guirenderer.directive.ForegroundDirective;
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
					getURLBar().text(ev.getURL().toString());
				});
			}
		};
		
		this
			.directive(BackgroundDirective.of(Color.WHITE))
			.children(new Component[] {
				new BlockComponent(null)
				.directive(SizeDirective.of(new Location(1, 0, 0, 32)))
					.directive(BackgroundDirective.of(new Color(30, 30, 25)))
					.directive(ForegroundDirective.of(Color.WHITE))
					.directive(FontSizeDirective.of(16))
					.children(new Component[] {
						new CircularText(null)
							.text("<")
							.directive(PositionDirective.of(new Location(0, Styling.PADDING, 0, 3)))
							.directive(SizeDirective.of(new Location(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)))
							.directive(BackgroundDirective.of(Color.DARK_GRAY))
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
							.directive(BackgroundDirective.of(Color.DARK_GRAY))
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
							.directive(BackgroundDirective.of(Color.DARK_GRAY))
							.attribute("onrelease", new MouseListener() {
								@Override public void accept(MouseEvent e) {
									if (e.getButton()==MouseEvent.LEFT_BUTTON) {
										getBrowserComponent().tasks.quit();
									}
								}
							}),
						new URLBar(null)
							.tag("id", "url-bar")
							.directive(SizeDirective.of(new Location(1, -(Styling.BUTTON_WIDTH+Styling.PADDING)*3-2*Styling.PADDING, 0, Styling.BUTTON_WIDTH)))
							.directive(PositionDirective.of(new Location(0, (Styling.BUTTON_WIDTH+Styling.PADDING)*3+Styling.PADDING, 0, 3)))
							.directive(BackgroundDirective.of(Color.DARK_GRAY))
					}),
				new BreakComponent(null),
				new BlockComponent(null)
					.directive(SizeDirective.of(new Location(1, 0, 1, -32)))
					.tag("id", "display-pane")
					.directive(BackgroundDirective.of(Color.LIGHT_GRAY))
					.children(new Component[] {
						new WebicityFrame(null, engine, navigator)
							.tag("id", "browser-component")
							.directive(SizeDirective.of(new Location(1, 0, 1, 0)))
							.casted(WebicityFrame.class)
							//.navigate("https://www.w3.org/TR/PNG/iso_8859-1.txt")
							//.navigate("https://www.google.com/")
							//.navigate("https://www.yahoo.com/")
							//.navigate("file:C:\\Users\\jason\\Downloads\\sample-2mb-text-file (1).txt")
							//.navigate("https://www.google.com/")
							//.navigate("https://html.spec.whatwg.org/")
							.navigate("https://www.khronos.org/registry/vulkan/specs/1.2-extensions/html/vkspec.html")
					})
			});
	}
	
	public URLBar getURLBar() {
		return (URLBar) getComponentByID("url-bar");
	}
	public Component getDisplayPane() {
		return getComponentByID("display-pane");
	}
	public WebicityFrame getBrowserComponent() {
		return (WebicityFrame) getComponentByID("browser-component");
	}

	public void navigate(String url) {
		/*getURLBar().text(url);*/
		getBrowserComponent().navigate(url);
	}
}
