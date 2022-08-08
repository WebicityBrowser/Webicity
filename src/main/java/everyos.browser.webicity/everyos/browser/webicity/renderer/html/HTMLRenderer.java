package everyos.browser.webicity.renderer.html;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import everyos.browser.spec.javadom.intf.Document;
import everyos.browser.spec.jhtml.parser.JHTMLParser;
import everyos.browser.webicity.WebicityFrame;
import everyos.browser.webicity.renderer.Renderer;

public class HTMLRenderer implements Renderer {
	
	private static final Logger logger = LoggerFactory.getLogger(HTMLRenderer.class);
	
	private final List<Runnable> readyHooks;
	
	private Document document;
	private WebicityFrame frame;
	
	public HTMLRenderer() {
		this.readyHooks = new ArrayList<>();
	}

	@Override
	public void execute(WebicityFrame frame, InputStream stream) throws IOException {
		this.frame = frame;
		
		parsePage(stream);
		runReadyHooks();
	}

	@Override
	public void addReadyHook(Runnable hook) {
		if (this.document != null) {
			hook.run();
		} else {
			readyHooks.add(hook);
		}
	}

	@Override
	public String getTitle() {
		return document == null ?
			null :
			document.getTitle();
	}
	
	public Document getDocument() {
		return document;
	}

	public WebicityFrame getFrame() {
		return frame;
	}
	
	private void parsePage(InputStream stream) throws IOException {
		long time = System.currentTimeMillis();
		
		JHTMLParser parser = new JHTMLParser(stream);
		this.document = parser.parse();
		stream.close();
		
		long millisToParse = System.currentTimeMillis()-time;
		logger.info("Page (" + frame.getURL().toString() +") parsed in " + (millisToParse) + " millis (" + (int) (millisToParse/1000 + .5) +" seconds).");
	}
	
	private void runReadyHooks() {
		for (Runnable hook: readyHooks) {
			hook.run();
		}
		readyHooks.clear();
	}
	
}