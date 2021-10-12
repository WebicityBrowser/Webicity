package everyos.browser.webicitybrowser.gui.binding;

import everyos.browser.webicity.renderer.Renderer;
import everyos.browser.webicity.renderer.html.HTMLRenderer;
import everyos.browser.webicity.renderer.plaintext.PlainTextRenderer;
import everyos.browser.webicitybrowser.gui.colors.Colors;
import everyos.browser.webicitybrowser.gui.renderer.HTMLRendererGUI;
import everyos.browser.webicitybrowser.gui.renderer.TextRendererGUI;
import everyos.browser.webicitybrowser.ui.Frame;
import everyos.browser.webicitybrowser.ui.event.FrameMutationEventListener;
import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.directive.SizeDirective;
import everyos.engine.ribbon.core.shape.Location;

public class FrameGUI {
	private final Frame frame;
	private final Component outerPane;
	private final FrameMutationListener mutationListener;

	private Colors colors;

	public FrameGUI(Frame frame) {
		this.frame = frame;
		this.outerPane = new BlockComponent();
		this.mutationListener = new FrameMutationListener();
	}
	
	public void start(Colors colors) {
		this.colors = colors;
		
		frame.addFrameMutationListener(mutationListener);
		
		if (frame.getCurrentRenderer() != null) {
			mutationListener.onRendererCreated(frame.getCurrentRenderer());
		}
	}
	
	public void cleanup() {
		frame.removeFrameMutationListener(mutationListener);
	}

	public Component getDisplayPane() {
		return outerPane;
	}

	private class FrameMutationListener implements FrameMutationEventListener {
		
		@Override
		public void onRendererCreated(Renderer r) {
			if (r.getClass() == HTMLRenderer.class) {
				//TODO: Store "cleanup" to runnable for later
				HTMLRendererGUI rendererGUI = new HTMLRendererGUI((HTMLRenderer) r, colors);
				rendererGUI.start();
				outerPane.children(new Component[] {
					rendererGUI.getDisplayPane()
						.directive(SizeDirective.of(new Location(1, 0, 1, 0)))
				});
			} else if (r.getClass() == PlainTextRenderer.class) {
				TextRendererGUI rendererGUI = new TextRendererGUI((PlainTextRenderer) r, colors);
				rendererGUI.start();
				outerPane.children(new Component[] {
					rendererGUI.getDisplayPane()
						.directive(SizeDirective.of(new Location(1, 0, 1, 0)))
				});
			}
		}
		
	}
}
