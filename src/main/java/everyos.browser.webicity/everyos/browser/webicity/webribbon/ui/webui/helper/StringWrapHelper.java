package everyos.browser.webicity.webribbon.ui.webui.helper;

import java.util.ArrayList;
import java.util.List;

import everyos.engine.ribbon.core.rendering.RibbonFont;
import everyos.engine.ribbon.core.shape.SizePosGroup;

public class StringWrapHelper {
	private boolean ignoreWhitespace;
	private List<String> lines;
	private StringBuilder line;
	
	public List<String> calculateString(String text, RibbonFont font, SizePosGroup sizepos, boolean forceInline) {
		if (text.isBlank()) return List.of();
		
		this.ignoreWhitespace = true;
		this.lines = new ArrayList<String>(1);
		this.line = new StringBuilder();
		
		for (int i = 0; i<text.length(); i++) {
			char ch = text.charAt(i);
			if (Character.isWhitespace(ch)) {
				computeWhitespace(font, sizepos, forceInline);
			} else {
				i+=addWord(text, font, sizepos, forceInline, i)-1;
			}
		}
		
		if (!line.toString().isEmpty()) {
			lines.add(line.toString());
		}
		
		return lines;
	}
	
	private int addWord(String text, RibbonFont font, SizePosGroup sizepos, boolean forceInline, int i) {
		// Append the next word to the string. Wrap if our text is too long.
		sizepos.setMinLineHeight(font.getHeight()+font.getPaddingHeight());
		String word = getLengthOfNextWord(text, i);
		ignoreWhitespace = false;
		if (sizepos.move(fastStringWidth(font, word), forceInline)) {
			line.append(word);
		} else {
			if (sizepos.getCurrentPointer().getX()!=0) {
				line = nextLine(lines, line, sizepos);
			}
			//TODO: Support automatic hyphens
			for (int j = 0; j<word.length(); j++) {
				char ch2 = word.charAt(j);
				if (sizepos.move(font.getCharWidth(ch2), false)) {
					line.append(ch2);
				} else {
					line = nextLine(lines, line, sizepos);
					line.append(ch2);
					sizepos.move(font.getCharWidth(ch2), true);
				}
			}
		}
		
		return word.length();
	}

	private void computeWhitespace(RibbonFont font, SizePosGroup sizepos, boolean forceInline) {
		// Append a space if the last character was not whitespace or a line start.
		// If it goes offscreen, we do wrapping
		//TODO: Ability to configure wrapping
		if (!ignoreWhitespace) {
			ignoreWhitespace = true;
			if (sizepos.move(font.getCharWidth(' '), forceInline)) {
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
	
	public static int fastStringWidth(RibbonFont font, String str) {
		int maxWidth = 0;
		for (byte ch: str.getBytes()) {
			maxWidth += font.getCharWidth((char) ch); 
		}
		
		return maxWidth;
	}
	
	public static int stringWidth(RibbonFont font, String str) {
		int width = 0;
		for (String spl: str.split("\n")) {
			int currentLineWidth = 0;
			for (byte ch: spl.getBytes()) {
				currentLineWidth += font.getCharWidth((char) ch); 
			}
			width = Math.max(currentLineWidth, width);
		}
		return width;
	}
}
