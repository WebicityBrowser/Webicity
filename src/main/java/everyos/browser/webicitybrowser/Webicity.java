package everyos.browser.webicitybrowser;

import everyos.engine.ribbonawt.RibbonAWTMonitor.NoMonitorAvailableException;

public class Webicity {
	public static void main(String[] args) {
		
		/*try {
			Thread.sleep(4000);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}*/
		
		/*try {
			//for (int i=0; i<10; i++) new HTMLParser().parse(ClassLoader.getSystemClassLoader().getResourceAsStream("test2.html"));
			long time = System.currentTimeMillis();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
				ClassLoader.getSystemClassLoader().getResourceAsStream("test.html")));
			StringBuilder data = new StringBuilder();
			int ch = -1;
			while ((ch=reader.read())!=-1) data.append(ch);
			System.out.println("DATA READ:"+(System.currentTimeMillis()-time));
			Reader mreader = new StringReader(data.toString());
			int l = 1;
			time = System.currentTimeMillis();
			for (int i=0; i<l; i++) new HTMLParser().parse(mreader);
			//String test = "Hello<a href=https://dom.spec.whatwg.org/#html-document id=parsing:html-documents data-x-internal=html-documents>HTML document</a>world";
			//new HTMLParser().parse(new ByteArrayInputStream(test.getBytes()));
			System.out.println("BM Time:"+(System.currentTimeMillis()-time)/l);
		} catch (IOException e1) {
			e1.printStackTrace();
		}*/
		
		
		/*EcmaParser parser = new EcmaParser("var abc = 'JASON';");	
		long time = System.currentTimeMillis();
		parser.parse();
		System.out.println(System.currentTimeMillis()-time);*/

		
		
		try {
			WebicityInstance instance = new WebicityInstance();
		} catch (NoMonitorAvailableException e) {
			e.printStackTrace();
		}
	}
}
