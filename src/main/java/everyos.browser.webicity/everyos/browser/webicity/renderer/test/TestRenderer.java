package everyos.browser.webicity.renderer.test;

import java.io.IOException;
import java.io.InputStream;

import everyos.browser.webicity.WebicityFrame;
import everyos.browser.webicity.renderer.Renderer;
import everyos.browser.webicity.webribbon.WebComponentWrapper;
import everyos.engine.ribbon.renderer.guirenderer.directive.SizeDirective;
import everyos.engine.ribbon.renderer.guirenderer.shape.Location;

public class TestRenderer implements Renderer {
	private String path;

	public TestRenderer(String path) {
		this.path = path;
	}

	@Override public void execute(WebicityFrame frame, InputStream stream) throws IOException {
		if (path.equals("test"))  new WebComponentWrapper(frame)
			.attribute("ui", new TestHookup().run(frame))
			.directive(SizeDirective.of(new Location(1, 0, 1, 0)));
	}
}
