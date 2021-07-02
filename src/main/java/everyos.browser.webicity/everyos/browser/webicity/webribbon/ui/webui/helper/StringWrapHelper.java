package everyos.browser.webicity.webribbon.ui.webui.helper;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.engine.ribbon.core.rendering.Renderer;

public class StringWrapHelper {
	private boolean ignoreWhitespace;
	private List<String> lines;
	private StringBuilder line;
	
	public List<String> calculateString(String text, Renderer r, SizePosGroup sizepos, boolean forceInline) {
		if (text.isBlank()) return List.of();
		
		this.ignoreWhitespace = true;
		this.lines = new ArrayList<String>(1);
		this.line = new StringBuilder();
		
		for (int i = 0; i<text.length(); i++) {
			char ch = text.charAt(i);
			if (Character.isWhitespace(ch)) {
				computeWhitespace(r, sizepos, forceInline);
			} else {
				i+=addWord(text, r, sizepos, forceInline, i)-1;
			}
		}
		
		if (!line.toString().isEmpty()) {
			lines.add(line.toString());
		}
		
		return lines;
	}
	
	private int addWord(String text, Renderer r, SizePosGroup sizepos, boolean forceInline, int i) {
		// Append the next word to the string. Wrap if our text is too long.
		sizepos.setMinLineHeight(r.getFontHeight()+r.getFontPaddingHeight());
		String word = getLengthOfNextWord(text, i);
		ignoreWhitespace = false;
		if (sizepos.move(fastStringWidth(r, word), forceInline)) {
			line.append(word);
		} else {
			if (sizepos.getCurrentPointer().getX()!=0) {
				line = nextLine(lines, line, sizepos);
			}
			//TODO: Support automatic hyphens
			for (int j = 0; j<word.length(); j++) {
				char ch2 = word.charAt(j);
				if (sizepos.move(r.charWidth(ch2), false)) {
					line.append(ch2);
				} else {
					line = nextLine(lines, line, sizepos);
					line.append(ch2);
					sizepos.move(r.charWidth(ch2), true);
				}
			}
		}
		
		return word.length();
	}

	private void computeWhitespace(Renderer r, SizePosGroup sizepos, boolean forceInline) {
		// Append a space if the last character was not whitespace or a line start.
		// If it goes offscreen, we do wrapping
		//TODO: Ability to configure wrapping
		if (!ignoreWhitespace) {
			ignoreWhitespace = true;
			if (sizepos.move(r.charWidth(' '), forceInline)) {
				line.append(' ');
			} else {
				line = nextLine(lines, line, sizepos);
			}
		}
	}

	private static String getLengthOfNextWord(String text, int offset) {
		StringBuilder word = new StringBuilder(8);
		
		for (int i = offset; i<text.length(); i++) {
			char ch = text.charAt(i);
			if (Character.isWhitespace(ch)) {
				break;
			}
			word.append(ch);
		}
		
		return word.toString();
	}

	private static StringBuilder nextLine(List<String> lines, StringBuilder line, SizePosGroup sizepos) {
		lines.add(line.toString());
		sizepos.nextLine();
		return new StringBuilder();
	}
	
	private static int fastStringWidth(Renderer r, String str) {
		int mw = 0;
		for (byte ch: str.getBytes()) {
			mw+=r.charWidth((char) ch); 
		}
		
		return mw;
	}
	
	public static int stringWidth(Renderer r, String str) {
		int width = 0;
		for (String spl: str.split("\n")) {
			int mw = 0;
			for (byte ch: spl.getBytes()) {
				mw+=r.charWidth((char) ch); 
			}
			width=mw>width?mw:width;
		}
		return width;
	}
}
