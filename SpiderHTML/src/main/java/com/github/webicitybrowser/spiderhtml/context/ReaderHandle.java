package com.github.webicitybrowser.spiderhtml.context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReaderHandle {
	
	private final int[] pushback;
	private final List<int[]> availableBuffers;
	private final int lookaheadSize;
	private int bufferPointer;
	private int pushbackLength;
	private boolean isFinished;

	public ReaderHandle(int pushback, int lookaheadSize) {
		this.pushback = new int[pushback];
		this.availableBuffers = new ArrayList<>();
		this.lookaheadSize = lookaheadSize;
		this.bufferPointer = 0;
		this.pushbackLength = 0;
	}

	public void unread(int ch) throws IOException {
		if (pushbackLength == pushback.length) {
			throw new IOException("Pushback buffer is full");
		}
		pushback[pushback.length - pushbackLength++ - 1] = ch;
	}
	
	public String lookahead(int num) throws IOException {
		clearDeadBuffers();
		if (num < 0) {
			throw new IllegalArgumentException("num must be non-negative");
		} else if (num == 0) {
			return "";
		} else if (num <= pushbackLength) {
			return new String(pushback, pushback.length - pushbackLength, num);
		} else if (num <= lookaheadSize) {
			StringBuilder lookaheadBuilder = new StringBuilder();
			lookaheadBuilder.append(new String(pushback, pushback.length - pushbackLength, pushbackLength));
			int remaining = num - pushbackLength;
			for (int i = 0; i < availableBuffers.size(); i++) {
				int[] buffer = availableBuffers.get(i);
				int thisBufferPointer = i == 0 ? bufferPointer : 0;
				int remainingInBuffer = buffer.length - thisBufferPointer;
				if (remaining < remainingInBuffer) {
					lookaheadBuilder.append(new String(buffer, thisBufferPointer, remaining));
					return lookaheadBuilder.toString();
				} else {
					lookaheadBuilder.append(new String(buffer, thisBufferPointer, remainingInBuffer));
					remaining -= remainingInBuffer;
				}
			}
			if (isFinished) {
				return lookaheadBuilder.toString();
			}
		}
		
		throw new IOException("Not enough data to lookahead " + num + " characters");
	}
	
	public void eat(int num) throws IOException {
		if (pushbackLength > 0 && num <= pushbackLength) {
			pushbackLength -= num;
			return;
		} else if (pushbackLength > 0) {
			num -= pushbackLength;
			pushbackLength = 0;
		}
		clearDeadBuffers();
		while (num > 0) {
			if (availableBuffers.isEmpty()) {
				if (!isFinished) {
					throw new IOException("Not enough data to eat " + num + " characters");
				}
				return;
			}
			int[] buffer = availableBuffers.get(0);
			int remaining = buffer.length - bufferPointer;
			if (num < remaining) {
				bufferPointer += num;
				return;
			} else {
				num -= remaining;
				bufferPointer = 0;
				availableBuffers.remove(0);
			}
		}
	}

	public int peek() throws IOException {
		if (pushbackLength > 0) {
			return pushback[pushback.length - pushbackLength];
		}
		clearDeadBuffers();
		if (availableBuffers.isEmpty()) {
			if (!isFinished) {
				throw new IOException("No buffered data available!");
			}
			return -1;
		}
		int[] buffer = availableBuffers.get(0);
		return buffer[bufferPointer];
	}

	public int read() throws IOException {
		if (pushbackLength > 0) {
			return pushback[pushback.length - pushbackLength--];
		}
		clearDeadBuffers();
		if (availableBuffers.isEmpty()) {
			if (!isFinished) {
				throw new IOException("No buffered data available!");
			}
			return -1;
		}
		int[] buffer = availableBuffers.get(0);
		int ch = buffer[bufferPointer++];
		return ch;
	}

	public void pushBuffer(int[] buffer) {
		availableBuffers.add(buffer);
	}

	public void finalizeBuffers() {
		isFinished = true;
	}

	public boolean isLookaheadReady() {
		if (isFinished) {
			return true;
		}

		int totalLookaheadSize = pushbackLength;
		for (int i = 0; i < availableBuffers.size(); i++) {
			int[] buffer = availableBuffers.get(i);
			int thisBufferPointer = i == 0 ? bufferPointer : 0;
			totalLookaheadSize += buffer.length - thisBufferPointer;

			if (totalLookaheadSize >= lookaheadSize) {
				return true;
			}
		}

		return false;
	}

	private void clearDeadBuffers() {
		for (int i = 0; i < availableBuffers.size(); i++) {
			int[] buffer = availableBuffers.get(i);
			if (bufferPointer == buffer.length) {
				availableBuffers.remove(i);
				bufferPointer = 0;
			} else {
				break;
			}
		}
	}
	
}
