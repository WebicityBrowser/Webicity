package everyos.browser.webicitybrowser;

import java.net.MalformedURLException;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import everyos.browser.webicity.net.URL;
import everyos.browser.webicitybrowser.gui.binding.InstanceGUI;
import everyos.browser.webicitybrowser.gui.window.SkijaWindow;

public class Webicity {
	public static void main(String[] args) {
		WebicityInstance instance = new WebicityInstance();
		
		Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		logger.setLevel(Level.INFO);
		for (int i=0; i<args.length; i++) {
			if (args[i].equals("-v")||args[i].equals("--verbose")) {
				logger.setLevel(Level.ALL);
				return;
			}
		}
		
		for (String arg: args) {
			try {
				instance.open(new URL(arg));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		if (args.length==0) {
			try {
				//TODO: Configuration
				//instance.open(new URL("https://www.yahoo.com/"));
				//instance.open(new URL("https://www.whatismybrowser.com/"));
				//instance.open(new URL("https://www.example.com/"));
				instance.open(new URL("https://khronos.org/registry/vulkan/specs/1.2-extensions/html/vkspec.html"));
				//instance.open(new URL("https://html.spec.whatwg.org/multipage/parsing.html#named-character-reference-state"));
				//instance.open(new URL("https://www.target.com/"));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		instance.start();
		
		new InstanceGUI(instance, ()->{
			try {
				return SkijaWindow.create();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).start();
	}
}
