package everyos.engine.ribbon.util;

public class SecureStringBuilder {
	private char[] characters;
	int len = 0;
	
	public SecureStringBuilder() {
		this(16);
	}
	public SecureStringBuilder(int size) {
		this.characters = new char[size];
	}
	public SecureStringBuilder(char[] c, int off, int len) {
		this(len);
		for (int i=0; i<len; i++) {
			characters[i] = c[off+i];
		}
	};
	
	public void append(char c) {
		characters[len] = c;
		len++;
	}
	
	public int length() {
		return len;
	}
	
	public char getChar(int i) {
		if (i>=len) return 0;
		return characters[i];
	}
	
	public char[] getCharacters() {
		return characters;
	}
	
	public void destroy() {
		clear(characters);
	}
	
	@Override
	@Deprecated
	public void finalize() {
		destroy();
	}
	
	@Override
	public String toString() {
		return new String(characters, 0, len);
	}
	
	private void clear(char[] characters) {
		for (int i=0; i<characters.length; i++) {
			characters[i] = 0;
		}
	}
}
