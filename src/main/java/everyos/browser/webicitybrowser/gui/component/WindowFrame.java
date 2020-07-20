package everyos.browser.webicitybrowser.gui.component;

import everyos.browser.webicitybrowser.gui.Styling;
import everyos.engine.ribbon.event.MouseEvent;
import everyos.engine.ribbon.event.MouseListener;
import everyos.engine.ribbon.graphics.Color;
import everyos.engine.ribbon.graphics.component.BlockComponent;
import everyos.engine.ribbon.graphics.component.BreakComponent;
import everyos.engine.ribbon.graphics.component.Component;
import everyos.engine.ribbon.shape.Location;

public class WindowFrame extends BlockComponent{

	public WindowFrame(Component window) {
		super(window);
		
		final int BUTTON_WIDTH = 20;
		
		this
			.attribute("bg-color", new Color(50, 50, 45))
			.attribute("size", new Location(1, 0, 1, 0))
			.attribute("children", new Component[] {
				new BlockComponent(null)
					.attribute("position", new Location(0, 1, 0, 1))
					.attribute("size", new Location(1, -2, 1, -2))
					.attribute("bg-color", Color.WHITE)
					.attribute("children", new Component[] {
						new BlockComponent(null)
							.attribute("size", new Location(1, 0, 0, 32))
							.attribute("bg-color", new Color(30, 30, 25))
							.attribute("fg-color", Color.WHITE)
							.attribute("font-size", 16)
							.attribute("children", new Component[] {
								new BlockComponent(null)
									.attribute("size", new Location(1, -32, 1, 0))
									.attribute("id", "tab-bar")
									.attribute("children", new Component[] {
										new CircularText(null)
											.attribute("text", "Webicity")
											.attribute("bg-color", Color.DARK_GRAY)
											.attribute("position", new Location(0, 5, 0, 5))
											.attribute("size", new Location(0, (Styling.BUTTON_WIDTH+Styling.PADDING)*3-Styling.PADDING, 1, -10))
									}),
								new CircularText(null)
									.attribute("text", "-")
									.attribute("position", new Location(1, (-(Styling.BUTTON_WIDTH)-Styling.PADDING)*3, 0, 5))
									.attribute("size", new Location(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH))
									.attribute("bg-color", Color.DARK_GRAY)
									.attribute("fg-color", Color.BLACK)
									.attribute("onrelease", new MouseListener() {
										@Override public void accept(MouseEvent e) {
											if (parent.getAttribute("onminimize")!=null&&e.getButton()==MouseEvent.LEFT_BUTTON) {
												((Runnable) parent.getAttribute("onminimize")).run();
											}
										}
									}),
								new CircularText(null)
									.attribute("text", "+")
									.attribute("position", new Location(1, (-(Styling.BUTTON_WIDTH)-Styling.PADDING)*2, 0, 5))
									.attribute("size", new Location(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH))
									.attribute("bg-color", Color.DARK_GRAY)
									.attribute("fg-color", Color.BLACK)
									.attribute("onrelease", new MouseListener() {
										@Override public void accept(MouseEvent e) {
											if (parent.getAttribute("onrestore")!=null&&e.getButton()==MouseEvent.LEFT_BUTTON) {
												((Runnable) parent.getAttribute("onrestore")).run();
											}
										}
									}),
								new CircularText(null)
									.attribute("text", "X")
									.attribute("position", new Location(1, -(Styling.BUTTON_WIDTH)-Styling.PADDING, 0, 5))
									.attribute("size", new Location(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH))
									.attribute("bg-color", Color.RED)
									.attribute("fg-color", Color.BLACK)
									.attribute("onrelease", new MouseListener() {
										@Override public void accept(MouseEvent e) {
											if (parent.getAttribute("onclose")!=null&&e.getButton()==MouseEvent.LEFT_BUTTON) {
												((Runnable) parent.getAttribute("onclose")).run();
											}
										}
									})
							}),
						new BreakComponent(null),
						new BlockComponent(null)
							.attribute("size", new Location(1, 0, 1, -32))
							.attribute("id", "content-pane")
							.attribute("bg-color", Color.LIGHT_GRAY)
					}),
			});
	}

	public Component getTabBar() {
		return getComponentByID("tab-bar");
	}
	public Component getDisplayPane() {
		return getComponentByID("content-pane");
	}
}
