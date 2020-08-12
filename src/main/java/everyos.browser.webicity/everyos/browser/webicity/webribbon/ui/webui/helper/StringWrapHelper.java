package everyos.browser.webicity.webribbon.ui.webui.helper;

import java.util.ArrayList;

import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.engine.ribbon.renderer.guirenderer.GUIRenderer;

public class StringWrapHelper {
	public static ArrayList<String> calculateString(String text, GUIRenderer r, SizePosGroup sizepos, boolean ew) {
		ArrayList<String> lines = new ArrayList<String>();
		
		StringBuilder line = new StringBuilder();
		StringBuilder word = new StringBuilder();
		boolean isNewLine = true;
		boolean isNewLineNLTriggered = true;
		boolean startsOnNL = true;
		boolean is = true;
		int wordLength = 0;
		
		int i = 0;
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
			
			if (sizepos.pointer.x+wordLength+r.charWidth(ch)>sizepos.preferredWidth&&(ew||ch==' ')) {
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
	
	public static int stringWidth(GUIRenderer r, String str) {
		int width = 0;
		for (String spl: str.split("\n")) {
			int mw = 0;
			for (byte ch: spl.getBytes()) mw+=r.charWidth((char) ch); 
			width=mw>width?mw:width;
		}
		return width;
	}
}
