package everyos.browser.jinfra;

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;

public class NIOReader implements Closeable {
	private Reader underlyingStream;
	private PushbackArray pushback;
	private PushbackArray oldPushback;
	private PushbackArray backup;
	private boolean endFound = false;
	
	public NIOReader(Reader stream, int length) {
		this.underlyingStream = stream;
		
		this.pushback = new PushbackArray(length);
		this.oldPushback = pushback.clone();
	}

	public int read() throws IOException {
		try {
			if (pushback.length()>0) {
				return pushback.pop();
			}
			if (backup.length()>0) {
				//TODO
			}
			if (endFound) return -1;
			int ch = underlyingStream.read();
			backup.push(ch);
			if (ch==-1) endFound = true;
			return ch;
		} catch (IOPendingException e) {
			fallback();
			throw e;
		}
	}
	
	public void unread(int ch) {
		pushback.push(ch);
	}
	
	public void flush() {
		
		//this.pos = 0;
	}
	
	private void fallback() {
		pushback = oldPushback.clone();
	}
	
	public boolean finished() {
		return endFound & pushback.length()==0 ;
	}
	
	public char[] peek(int l) throws IOException {
		char[] ch = new char[l];
		for (int i=0; i<l; i++) {
			int cha = read();
			if (cha == -1) break;
			ch[i] = (char) cha;
		}
		for (int i=l-1; i>=0; i--) {
			unread(ch[i]);
		}
		return ch;
	}
	
	public String peekstr(int l) throws IOException {
		return String.valueOf(peek(l));
	}

	@Override
	public void close() throws IOException {
		underlyingStream.close();
	}
}
