package everyos.browser.webicitybrowser;

import java.net.MalformedURLException;

import everyos.browser.webicity.net.URL;
import everyos.browser.webicitybrowser.gui.InstanceGUI;

public class Webicity {
	public static void main(String[] args) {
		WebicityInstance instance = new WebicityInstance();
		
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
				instance.open(new URL("https://www.yahoo.com/"));
				instance.open(new URL("https://www.whatismybrowser.com/"));
				//instance.open(new URL("https://www.example.com/"));
				instance.open(new URL("https://khronos.org/registry/vulkan/specs/1.2-extensions/html/vkspec.html"));
				//instance.open(new URL("https://www.target.com/"));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		instance.start();
		new InstanceGUI(instance).start();
	}
}
