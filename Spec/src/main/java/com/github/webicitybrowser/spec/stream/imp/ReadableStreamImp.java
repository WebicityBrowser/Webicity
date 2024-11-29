package com.github.webicitybrowser.spec.stream.imp;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.github.webicitybrowser.spec.stream.ReadRequest;
import com.github.webicitybrowser.spec.stream.ReadableStream;
import com.github.webicitybrowser.spec.stream.ReadableStreamController;
import com.github.webicitybrowser.spec.stream.ReadableStreamDefaultController;
import com.github.webicitybrowser.spec.stream.ReadableStreamReader;

public class ReadableStreamImp implements ReadableStream {

	private final ReadableStreamController controller;

	private final List<ReadRequest> readRequests = new ArrayList<>();
	private final Deque<Object> queue = new ArrayDeque<>();

	private State state;
	private ReadableStreamReader reader;
	private boolean closeRequested = false;

	public ReadableStreamImp() {
		this.state = State.READABLE;
		this.controller = new ReadableStreamDefaultControllerImp();
	}

	@Override
	public void enqueue(Object chunk) {
		if (controller instanceof ReadableStreamDefaultController readableController) {
			readableController.enqueue(chunk);
		} else {
			// TODO: Byte stream controller
			throw new UnsupportedOperationException("Don't support this type of controller yet!");
		}
	}

	@Override
	public ReadableStreamController controller() {
		return this.controller;
	}

	@Override
	public void close() {
		// TODO: Byte stream controller
		((ReadableStreamDefaultControllerImp) controller).close();
	}

	@Override
	public boolean isLocked() {
		return reader != null;
	}

	@Override
	public void setReader(ReadableStreamReader reader) {
		this.reader = reader;
	}

	public State getState() {
		return this.state;
	}

	private void fulfillReadRequest(Object chunk, boolean done) {
		assert reader != null;
		assert !readRequests.isEmpty();
		ReadRequest request = readRequests.remove(0);
		if (done) {
			request.closeSteps().run();
		} else {
			request.chunkSteps().accept(chunk);
		}
	}

	private void addReadRequest(ReadRequest request) {
		assert controller instanceof ReadableStreamDefaultController;
		assert state == State.READABLE;
		readRequests.add(request);
	}

	private void internalClose() {
		assert state == State.READABLE;
		this.state = State.CLOSED;
		if (reader == null) return;
		// Resolve promise
		if (reader instanceof ReadableStreamDefaultReaderImp) {
			List<ReadRequest> requests = new ArrayList<>(readRequests);
			readRequests.clear();
			for (ReadRequest request : requests) {
				request.closeSteps().run();
			}
		}
	}

	private class ReadableStreamDefaultControllerImp implements ReadableStreamDefaultController {

		@Override
		public void enqueue(Object chunk) {
			if (!canCloseOrEnqueue()) return;
			if (!isLocked() && !readRequests.isEmpty()) {
				fulfillReadRequest(chunk, false);
			} else {
				// TODO: Track backpressure
				queue.add(chunk);
			}
		}

		@Override
		public void performPullSteps(ReadRequest readRequest) {
			if (!queue.isEmpty()) {
				Object chunk = queue.remove();
				if (closeRequested && queue.isEmpty()) {
					internalClose();
				} else {
					// TODO: Call pull if needed
				}
				readRequest.chunkSteps().accept(chunk);
			} else {
				addReadRequest(readRequest);
				// TODO: Call pull if needed
			}
		}

		private void close() {
			if (!canCloseOrEnqueue()) return;
			closeRequested = true;
			if (queue.isEmpty()) {
				// TODO: Remove algorithms
				internalClose();
			}
		}

		private boolean canCloseOrEnqueue() {
			return state == State.READABLE;
		}

	}
	
}
