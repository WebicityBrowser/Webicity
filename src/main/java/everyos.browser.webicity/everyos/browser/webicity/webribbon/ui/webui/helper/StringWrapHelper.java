package everyos.browser.webicity.webribbon.ui.webui.helper;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.engine.ribbon.core.rendering.Renderer;

public class StringWrapHelper {
	public static List<String> calculateString(String text, Renderer r, SizePosGroup sizepos, boolean forceInline) {
		if (text.isBlank()) return List.of();
		
		List<String> lines = new ArrayList<String>(1);
		StringBuilder line = new StringBuilder();
		
		boolean ignoreWhitespace = true;
		
		for (int i = 0; i<text.length(); i++) {
			char ch = text.charAt(i);
			if (ch=='\n'||ch=='\r'||ch=='\f'||ch=='\t') {
				//line = nextLine(lines, line, r, sizepos);
				// Newlines are ignored
			} else if (Character.isWhitespace(ch)) {
				if (!ignoreWhitespace) {
					ignoreWhitespace = true;
					if (sizepos.move(r.charWidth(' '), forceInline)) {
						line.append(' ');
					} else {
						line = nextLine(lines, line, r, sizepos);
					}
				}
			} else {
				sizepos.setMinLineHeight(r.getFontHeight()+r.getFontPaddingHeight());
				String word = nextWord(text, i);
				i+=word.length()-1;
				ignoreWhitespace = false;
				if (sizepos.move(fastStringWidth(r, text), forceInline)) {
					line.append(word);
				} else {
					if (sizepos.getCurrentPointer().getX()!=0) {
						line = nextLine(lines, line, r, sizepos);
					}
					//TODO: Support automatic hyphens
					for (int j = 0; j<word.length(); j++) {
						char ch2 = text.charAt(j);
						if (sizepos.move(r.charWidth(ch2), false)) {
							line.append(ch2);
						} else {
							line = nextLine(lines, line, r, sizepos);
							line.append(ch2);
						}
					}
				}
			}
		}
		
		if (!line.toString().isEmpty()) {
			lines.add(line.toString());
		}
		
		return lines;
	}
	
	private static String nextWord(String text, int offset) {
		StringBuilder word = new StringBuilder(8);
		
		for (int i = offset; i<text.length(); i++) {
			char ch = text.charAt(i);
			if (ch=='\n'||ch=='\r'||ch=='\f'||ch=='\t'||Character.isWhitespace(ch)) {
				break;
			}
			word.append(ch);
		}
		
		return word.toString();
	}

	private static StringBuilder nextLine(List<String> lines, StringBuilder line, Renderer r, SizePosGroup sizepos) {
		lines.add(line.toString());
		sizepos.setMinLineHeight(r.getFontHeight()+r.getFontPaddingHeight());
		sizepos.nextLine();
		return new StringBuilder();
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
	
	private static int fastStringWidth(Renderer r, String str) {
		int mw = 0;
		for (byte ch: str.getBytes()) mw+=r.charWidth((char) ch); 
		return mw;
	}
}
