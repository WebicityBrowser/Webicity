package everyos.browser.webicitybrowser.gui.component;

import everyos.browser.webicitybrowser.gui.Styling;
import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.BreakComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.renderer.guirenderer.directive.BackgroundDirective;
import everyos.engine.ribbon.renderer.guirenderer.directive.ExternalMouseListenerDirective;
import everyos.engine.ribbon.renderer.guirenderer.directive.FontSizeDirective;
import everyos.engine.ribbon.renderer.guirenderer.directive.ForegroundDirective;
import everyos.engine.ribbon.renderer.guirenderer.directive.MouseListenerDirective;
import everyos.engine.ribbon.renderer.guirenderer.directive.PositionDirective;
import everyos.engine.ribbon.renderer.guirenderer.directive.SizeDirective;
import everyos.engine.ribbon.renderer.guirenderer.event.MouseEvent;
import everyos.engine.ribbon.renderer.guirenderer.graphics.Color;
import everyos.engine.ribbon.renderer.guirenderer.shape.Location;

public class WindowFrame extends BlockComponent{
	private Runnable onClose;
	private Runnable onMinimize;
	private Runnable onRestore;
	
	public WindowFrame(Component window) {
		super(window);
		
		final int BUTTON_WIDTH = 20;
		
		this
			.directive(BackgroundDirective.of(new Color(50, 50, 45)))
			.directive(SizeDirective.of(new Location(1, 0, 1, 0)))
			.children(new Component[] {
				new BlockComponent(null)
					.directive(PositionDirective.of(new Location(0, 1, 0, 1)))
					.directive(SizeDirective.of(new Location(1, -2, 1, -2)))
					.directive(BackgroundDirective.of(Color.WHITE))
					.children(new Component[] {
						new BlockComponent(null)
							.directive(SizeDirective.of(new Location(1, 0, 0, 32)))
							.directive(BackgroundDirective.of(new Color(30, 30, 25)))
							.directive(ForegroundDirective.of(Color.WHITE))
							.directive(FontSizeDirective.of(16))
							.children(new Component[] {
								new BlockComponent(null)
									.directive(SizeDirective.of(new Location(1, -32, 1, 0)))
									.tag("id", "tab-bar")
									.children(new Component[] {
										new CircularText(null)
											.text("Webicity")
											.directive(BackgroundDirective.of(Color.DARK_GRAY))
											.directive(PositionDirective.of(new Location(0, 5, 0, 5)))
											.directive(SizeDirective.of(new Location(0, (Styling.BUTTON_WIDTH+Styling.PADDING)*3-Styling.PADDING, 1, -10)))
									}),
								new CircularText(null)
									.text("-")
									.directive(PositionDirective.of(new Location(1, (-(Styling.BUTTON_WIDTH)-Styling.PADDING)*3, 0, 5)))
									.directive(SizeDirective.of(new Location(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)))
									.directive(BackgroundDirective.of(Color.DARK_GRAY))
									.directive(ForegroundDirective.of(Color.BLACK))
									.directive(MouseListenerDirective.of(e->{
										if (e.getAction()==MouseEvent.MOVE) {
											e.getComponent().directive(BackgroundDirective.of(Color.LIGHT_GRAY));
										}
										if (e.getAction()==MouseEvent.PRESS||e.getAction()==MouseEvent.DRAG) {
											e.getComponent().directive(BackgroundDirective.of(Color.WHITE));
										}
										if (e.getButton()!=MouseEvent.LEFT_BUTTON) return;
										if (e.getAction()==MouseEvent.RELEASE) {
											if (onMinimize!=null) onMinimize.run();
											e.getComponent().directive(BackgroundDirective.of(Color.DARK_GRAY));
										}
									}))
									.directive(ExternalMouseListenerDirective.of(e->{
										e.getComponent().directive(BackgroundDirective.of(Color.DARK_GRAY));
									})),
								new CircularText(null)
									.text("+")
									.directive(PositionDirective.of(new Location(1, (-(Styling.BUTTON_WIDTH)-Styling.PADDING)*2, 0, 5)))
									.directive(SizeDirective.of(new Location(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)))
									.directive(BackgroundDirective.of(Color.DARK_GRAY))
									.directive(ForegroundDirective.of(Color.BLACK))
									.directive(MouseListenerDirective.of(e->{
										if (e.getAction()==MouseEvent.MOVE) {
											e.getComponent().directive(BackgroundDirective.of(Color.LIGHT_GRAY));
										}
										if (e.getAction()==MouseEvent.PRESS||e.getAction()==MouseEvent.DRAG) {
											e.getComponent().directive(BackgroundDirective.of(Color.WHITE));
										}
										if (e.getButton()!=MouseEvent.LEFT_BUTTON) return;
										if (e.getAction()==MouseEvent.RELEASE) {
											if (onRestore!=null) onRestore.run();
											e.getComponent().directive(BackgroundDirective.of(Color.DARK_GRAY));
										}
									}))
									.directive(ExternalMouseListenerDirective.of(e->{
										e.getComponent().directive(BackgroundDirective.of(Color.DARK_GRAY));
									})),
								new CircularText(null)
									.text("X")
									.directive(PositionDirective.of(new Location(1, -(Styling.BUTTON_WIDTH)-Styling.PADDING, 0, 5)))
									.directive(SizeDirective.of(new Location(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)))
									.directive(BackgroundDirective.of(Color.RED))
									.directive(ForegroundDirective.of(Color.BLACK))
									.directive(MouseListenerDirective.of(e->{
										if (e.getAction()==MouseEvent.MOVE) {
											e.getComponent().directive(BackgroundDirective.of(new Color(255, 100, 100)));
										}
										if (e.getAction()==MouseEvent.PRESS||e.getAction()==MouseEvent.DRAG) {
											e.getComponent().directive(BackgroundDirective.of(new Color(255, 50, 0)));
										}
										if (e.getButton()!=MouseEvent.LEFT_BUTTON) return;
										if (e.getAction()==MouseEvent.RELEASE) {
											if (onClose!=null) onClose.run();
										}
									}))
									.directive(ExternalMouseListenerDirective.of(e->{
										e.getComponent().directive(BackgroundDirective.of(Color.RED));
									}))
							}),
						new BreakComponent(null),
						new BlockComponent(null)
							.directive(SizeDirective.of(new Location(1, 0, 1, -32)))
							.tag("id", "content-pane")
							.directive(BackgroundDirective.of(Color.LIGHT_GRAY))
					}),
			});
	}

	public Component getTabBar() {
		return getComponentByID("tab-bar");
	}
	public Component getDisplayPane() {
		return getComponentByID("content-pane");
	}

	public void onClose(Runnable listener) {
		this.onClose = listener;
	}
	public void onMinimize(Runnable listener) {
		this.onMinimize = listener;
	}
	public void onRestore(Runnable listener) {
		this.onRestore = listener;
	}
}
