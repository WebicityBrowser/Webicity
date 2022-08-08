package everyos.browser.webicitybrowser.gui.renderer;

import com.github.webicity.lace.basics.component.ContainerComponent;
import com.github.webicity.lace.basics.layout.auto.ChildrenDirective;
import com.github.webicity.lace.basics.layout.auto.SizeDirective;
import com.github.webicity.lace.core.component.Component;
import com.github.webicity.lace.imputils.shape.RelativeSizeImp;

import everyos.browser.webicity.lacewebextensions.core.component.WebDocumentComponent;
import everyos.browser.webicity.renderer.html.HTMLRenderer;
import everyos.browser.webicitybrowser.gui.colors.Colors;

public class HTMLRendererGUI implements RendererGUI {

	private final HTMLRenderer renderer;
	@SuppressWarnings("unused")
	private final Colors colors;
	private final Component displayPane;

	public HTMLRendererGUI(HTMLRenderer renderer, Colors colors) {
		this.renderer = renderer;
		this.colors = colors;
		
		this.displayPane = new ContainerComponent();
	}
	
	public void start() {
		renderer.addReadyHook(()->{
			Component innerPane = new WebDocumentComponent(renderer.getDocument());
			innerPane.directive(SizeDirective.of(RelativeSizeImp.of(1, 0, 1, 0)));
			displayPane.directive(ChildrenDirective.of(new Component[] { innerPane }));
		});
	}
	
	public void cleanup() {
		
	}

	public Component getDisplayPane() {
		return displayPane;
	}

}
