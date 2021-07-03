package everyos.engine.ribbon.ui.simple.helper;

import java.util.ArrayList;

import everyos.browser.webicitybrowser.gui.Styling;
import everyos.browser.webicitybrowser.gui.component.TabButton;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.SizePosGroup;

public class StringWrapHelper {
	public static ArrayList<String> calculateString(String text, Renderer r, SizePosGroup sizepos) {
		ArrayList<String> lines = new ArrayList<String>(1);
		
		StringBuilder line = new StringBuilder();
		StringBuilder word = new StringBuilder();
		boolean isNewLine = true;
		boolean isNewLineNLTriggered = true;
		boolean startsOnNL = true;
		int wordLength = 0;
		
		int i = 0;
		while (i<text.length()) {
			if (sizepos.getCurrentPointer().getY()>sizepos.getMaxSize().getHeight() && sizepos.getMaxSize().getHeight()!=-1) break;
			
			int ch = text.codePointAt(i);
			sizepos.setMinLineHeight(r.getFontHeight());
			
			if (ch=='\n') {
				sizepos.move(wordLength, true);
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
				if (sizepos.getCurrentPointer().getX()+wordLength+r.charWidth(ch)>+sizepos.getMaxSize().getWidth()) {
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
				sizepos.move(wordLength, true);
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
		for (String spl : str.split("\n")) {
			int mw = 0;
			for (byte ch : spl.getBytes()) mw += r.charWidth((char) ch);
			width = mw > width ? mw : width;
		}
		return width;
	}

	public static String trim(Renderer r, String text, int fixedWidth) {
		int curWidth = stringWidth(r, text);

		// If it's already a small string, return it as it is.
		if (curWidth <= fixedWidth) return text;

		// Else keep trimming it by one character to fit it under fixedWidth
		while (curWidth > fixedWidth && text.length() > 0) {
			text = text.substring(0, text.length() - 1);
			curWidth = StringWrapHelper.stringWidth(r, text);
		}

		// If the text is still sufficiently long, replace last three char with ...
		if (text.length() > 8)
			text = text.substring(0, text.length() - 3) + "...";

		return text;
	}
}
