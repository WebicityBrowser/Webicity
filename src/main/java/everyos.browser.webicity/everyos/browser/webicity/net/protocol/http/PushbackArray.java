package everyos.browser.webicity.net.protocol.http;

public class PushbackArray implements Cloneable {
	private int[] buffer;
	private int pos = 0;

	public PushbackArray(int size) {
		this.buffer = new int[size];
	}
	
	public void push(int ch) {
		buffer[pos++] = ch;
	}
	
	public int pop() {
		return buffer[--pos];
	}
	
	public void clear() {
		pos = 0;
	}
	
	public PushbackArray clone() {
		PushbackArray copy = new PushbackArray(buffer.length);
		copy.pos = pos;
		for (int i=0; i<pos; i++) {
			copy.buffer[i] = buffer[i];
		}
		
		return copy;
	}

	public int length() {
		return pos;
	}
}
