package everyos.browser.webicity.renderer.html;

import java.io.IOException;
import java.io.InputStream;

import everyos.browser.javadom.intf.Document;
import everyos.browser.javadom.intf.Element;
import everyos.browser.javadom.intf.Node;
import everyos.browser.javadom.intf.NodeList;
import everyos.browser.javadom.intf.Text;
import everyos.browser.jhtml.JHTMLParser;
import everyos.browser.webicity.WebicityFrame;
import everyos.browser.webicity.renderer.Renderer;

public class HTMLRenderer implements Renderer {
	@Override public void execute(WebicityFrame frame, InputStream stream) throws IOException {
		/*try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		
		System.out.println("HTML");
		long time = System.currentTimeMillis();
		JHTMLParser parser = new JHTMLParser(stream);
		parser.parse();
		System.out.println();
		System.out.println("TIME: "+(System.currentTimeMillis()-time));
		stream.close();
		
		Document doc = parser.getDocument();
		
		//recurse(doc, 0);
		/*HTMLScreen screen = new HTMLScreen();
		doc.bind(screen);
		
		new WebComponentWrapper(frame)
			.ui(screen.getComponent())
			.directive(SizeDirective.of(new Location(1, 0, 1, 0)));*/
	}

	private void recurse(Node e, int j) {
		for (int i=0; i<j; i++) {
			if (i==j-1) {
				System.out.print("|");
			} else {
				System.out.print(" ");
			}
		}
		System.out.print("#");
		if (e instanceof Text) {
			System.out.println(((Text)e).getWholeText());
		} else if (e instanceof Element) {
			System.out.println(((Element)e).getTagName());
		} else {
			System.out.println(e.getNodeName());
		}
		NodeList children = e.getChildNodes();
		int l = (int) children.getLength();
		for (int i=0; i<l; i++) {
			recurse(children.item(i), j+1);
		}
	}
}