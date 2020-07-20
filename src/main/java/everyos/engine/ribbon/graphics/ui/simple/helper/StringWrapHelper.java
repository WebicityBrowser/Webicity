package everyos.engine.ribbon.graphics.ui.simple.helper;

import java.util.ArrayList;

import everyos.engine.ribbon.graphics.Renderer;
import everyos.engine.ribbon.shape.SizePosGroup;

public class StringWrapHelper {
	public static ArrayList<String> calculateString(String text, Renderer r, SizePosGroup sizepos) {
		ArrayList<String> lines = new ArrayList<String>();
		
		StringBuilder line = new StringBuilder();
		StringBuilder word = new StringBuilder();
		boolean isNewLine = true;
		boolean isNewLineNLTriggered = true;
		boolean startsOnNL = true;
		int wordLength = 0;
		
		int i = 0;
		while (i<text.length()) {
			if (sizepos.y>sizepos.maxSize.height && sizepos.maxSize.height!=-1) break;
			
			int ch = text.codePointAt(i);
			sizepos.minIncrease(r.getFontHeight());
			
			if (ch=='\n') {
				sizepos.x+=wordLength;
				line.append(word);
				word = new StringBuilder();
				wordLength = 0;
				
				lines.add(line.toString());
				line = new StringBuilder();
				sizepos.nextLine();
				isNewLine = true;
				isNewLineNLTriggered = true;
				startsOnNL = true;
			}
			
			if (ch!='\n') {
				if (sizepos.x+wordLength+r.charWidth(ch)>+sizepos.maxSize.width) {
					if (startsOnNL) {
						line.append(word);
						word = new StringBuilder();
						wordLength = 0;
					}
					lines.add(line.toString());
					line = new StringBuilder();
					sizepos.nextLine();
					
					startsOnNL = true;
				}
				if (isNewLineNLTriggered||!isNewLine||ch!=' ') {
					word.appendCodePoint(ch);
					wordLength+=r.charWidth(ch);
					isNewLine = false;
					isNewLineNLTriggered = false;
				}
			}
			
			if (!Character.isLetterOrDigit(ch)) {
				sizepos.x+=wordLength;
				line.append(word);
				word = new StringBuilder();
				wordLength = 0;
				startsOnNL = false;
			}
			
			i++;
		}
		line.append(word);
		
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
