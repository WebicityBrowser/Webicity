package everyos.browser.webicity.renderer.html;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import everyos.browser.javadom.intf.Document;
import everyos.browser.jhtml.parser.JHTMLParser;
import everyos.browser.webicity.WebicityFrame;
import everyos.browser.webicity.renderer.Renderer;

public class HTMLRenderer implements Renderer {
	private Document document;
	private List<Runnable> readyHooks = new ArrayList<>();
	private WebicityFrame frame;

	@Override public void execute(WebicityFrame frame, InputStream stream) throws IOException {
		/*try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		
		this.frame = frame;
		
		long time = System.currentTimeMillis();
		JHTMLParser parser = new JHTMLParser(stream);
		parser.parse();
		System.out.println("TIME: "+(System.currentTimeMillis()-time));
		stream.close();
		
		Document doc = parser.getDocument();
		
		this.document = doc;
		
		for (Runnable hook: readyHooks) {
			hook.run();
		}
		readyHooks.clear();
	}

	@Override
	public void addReadyHook(Runnable hook) {
		if (this.document!=null) {
			hook.run();
		} else {
			readyHooks.add(hook);
		}
	}
	
	public Document getDocument() {
		return document;
	}

	@Override
	public String getTitle() {
		if (document==null) return null;
		return document.getTitle();
	}

	public WebicityFrame getFrame() {
		return frame;
	}
}