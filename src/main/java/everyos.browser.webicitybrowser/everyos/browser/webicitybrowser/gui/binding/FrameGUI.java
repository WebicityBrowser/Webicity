package everyos.browser.webicitybrowser.gui.binding;

import com.github.webicity.lace.basics.component.ContainerComponent;
import com.github.webicity.lace.basics.layout.auto.ChildrenDirective;
import com.github.webicity.lace.basics.layout.auto.SizeDirective;
import com.github.webicity.lace.core.component.Component;
import com.github.webicity.lace.imputils.shape.RelativeSizeImp;

import everyos.browser.webicity.renderer.Renderer;
import everyos.browser.webicity.renderer.html.HTMLRenderer;
import everyos.browser.webicity.renderer.plaintext.PlainTextRenderer;
import everyos.browser.webicitybrowser.gui.colors.Colors;
import everyos.browser.webicitybrowser.gui.renderer.HTMLRendererGUI;
import everyos.browser.webicitybrowser.gui.renderer.PlainTextRendererGUI;
import everyos.browser.webicitybrowser.gui.renderer.RendererGUI;
import everyos.browser.webicitybrowser.ui.Frame;
import everyos.browser.webicitybrowser.ui.event.FrameMutationEventListener;

public class FrameGUI {

	private final Frame frame;
	private final Component outerPane;
	private final FrameMutationListener mutationListener;

	private Colors colors;

	public FrameGUI(Frame frame) {
		this.frame = frame;
		this.outerPane = new ContainerComponent();
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
	
	//TODO: Create a Map-based registry
	private void configureHTMLRendererGUI(HTMLRenderer r) {
		//TODO: Store "cleanup" to runnable for later
		configureRendererGUI(new HTMLRendererGUI((HTMLRenderer) r, colors));
	}
	
	private void configurePlainTextRendererGUI(PlainTextRenderer r) {
		configureRendererGUI(new PlainTextRendererGUI((PlainTextRenderer) r, colors));
	}
	
	private void configureRendererGUI(RendererGUI rendererGUI) {
		rendererGUI.start();
		
		Component displayPane = rendererGUI.getDisplayPane();
		displayPane.directive(SizeDirective.of(RelativeSizeImp.of(1, 0, 1, 0)));
		
		outerPane.directive(ChildrenDirective.of(displayPane));
	}

	private class FrameMutationListener implements FrameMutationEventListener {
		
		@Override
		public void onRendererCreated(Renderer r) {
			if (r.getClass() == HTMLRenderer.class) {
				configureHTMLRendererGUI((HTMLRenderer) r);
			} else if (r.getClass() == PlainTextRenderer.class) {
				configurePlainTextRendererGUI((PlainTextRenderer) r);
			}
		}
		
	}
	
}
