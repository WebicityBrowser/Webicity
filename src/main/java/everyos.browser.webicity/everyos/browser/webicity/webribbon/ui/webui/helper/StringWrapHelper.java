package everyos.browser.webicity.webribbon.ui.webui.helper;

import java.util.ArrayList;

import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.engine.ribbon.core.rendering.Renderer;

public class StringWrapHelper {
	/*public static List<String> calculateString(String text, Renderer r, SizePosGroup sizepos, boolean wrapAnywhere) {
		List<String> lines = new ArrayList<String>(1);
		
		StringBuilder line = new StringBuilder();
		
		boolean ignoreWhitespace = true;
		
		for (int i = 0; i<text.length(); i++) {
			char ch = text.charAt(i);
			if (ch=='\n'||ch=='\r'||ch=='\f'||ch=='\t') {
				lines.add(line.toString());
				line = new StringBuilder();
			}
			if (text.charAt(i)==' ') {
				if (!ignoreWhitespace) {
					ignoreWhitespace = true;
				}
			}
		}
		
		return lines;
	}*/
	
	public static ArrayList<String> calculateString(String text, Renderer r, SizePosGroup sizepos, boolean wrapAnywhere) {
		ArrayList<String> lines = new ArrayList<String>(1);
		
		StringBuilder line = new StringBuilder(1);
		StringBuilder word = new StringBuilder(1);
		boolean isNewLine = true;
		boolean isNewLineNLTriggered = true;
		boolean startsOnNL = true;
		boolean is = true;
		int wordLength = 0;
		
		int i = 0;
		//System.out.println("ln: "+text.length());
		while (i<text.length()) {
			int ch = text.codePointAt(i++);
			
			//Yea...
			//Ignore any code after this referencing \n
			if (ch=='\n'||ch=='\r'||ch=='\f'||ch=='\t') ch = ' ';
			if (ch == ' ') {
				if (is) {
					continue;
				} else is = true;
			} else is = false;
			
			sizepos.minIncrease(r.getFontHeight());
			
			if (sizepos.pointer.x+wordLength+r.charWidth(ch)>sizepos.preferredWidth&&(wrapAnywhere||ch==' ')) {
				if (startsOnNL) {
					line.append(word);
					word = new StringBuilder();
					wordLength = 0;
				}
				lines.add(line.toString());
				line = new StringBuilder();
				sizepos.nextLine();
				
				isNewLine = true;
				startsOnNL = true;
			}
			if (isNewLineNLTriggered||!isNewLine||ch!=' ') {
				word.appendCodePoint(ch);
				wordLength+=r.charWidth(ch);
				isNewLine = false;
				isNewLineNLTriggered = false;
			}
			
			if (!Character.isLetterOrDigit(ch)) {
				sizepos.pointer.x+=wordLength;
				line.append(word);
				word = new StringBuilder();
				wordLength = 0;
				startsOnNL = false;
			}
		}
		sizepos.pointer.x+=wordLength;
		line.append(word);
		lines.add(line.toString());
		
		return lines;
	}
	
	public static int stringWidth(Renderer r, String str) {
		int width = 0;
		for (String spl: str.split("\n")) {
			int mw = 0;
			for (byte ch: spl.getBytes()) mw+=r.charWidth((char) ch); 
			width=mw>width?mw:width;
		}
		return width;
	}
}
