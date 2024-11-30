package com.github.webicitybrowser.spec.stream.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.stream.ReadRequest;
import com.github.webicitybrowser.spec.stream.ReadableStream;
import com.github.webicitybrowser.spec.stream.ReadableStreamDefaultReader;
import com.github.webicitybrowser.spec.stream.ReadableStream.State;

public class ReadableStreamDefaultReaderImp implements ReadableStreamDefaultReader {

	private final ReadableStream stream;

	private Exception storedError;

    public ReadableStreamDefaultReaderImp(ReadableStream stream) {
        if (stream.isLocked()) {
			// TODO: TypeError instead
			throw new IllegalStateException("Stream is locked!");
		}
		this.stream = stream;
		stream.setReader(this);
		// TODO: Promise
    }

	@Override
	public void read(ReadRequest request) {
		assert stream != null;
		// TODO: Set disturbed
		request = handleReadAllRequest(request);
		if (stream.getState() == State.CLOSED) {
			request.closeSteps().run();
		} else if (stream.getState() == State.ERRORED) {
			request.errorSteps().accept(storedError);
		} else {
			assert stream.getState() == State.READABLE;
			stream.controller().performPullSteps(request);
		}
	}

	@Override
	public void readAllBytes(Consumer<byte[]> consumer, Consumer<Exception> errorConsumer) {
		List<byte[]> chunks = new ArrayList<>();
		ReadRequest request = new ReadRequest(
			chunk -> {
				assert chunk instanceof byte[];
				chunks.add((byte[]) chunk);
			},
			() -> consumer.accept(collapseBytes(chunks)),
			e -> errorConsumer.accept(e),
			true
		);

		read(request);
	}

	private byte[] collapseBytes(List<byte[]> chunks) {
		int size = 0;
		for (byte[] chunk : chunks) {
			size += chunk.length;
		}
		byte[] result = new byte[size];
		int offset = 0;
		for (byte[] chunk : chunks) {
			System.arraycopy(chunk, 0, result, offset, chunk.length);
			offset += chunk.length;
		}
		return result;
	}

	private ReadRequest handleReadAllRequest(ReadRequest request) {
		if (!request.readAll()) return request;
		
		return new ReadRequest(
			chunk -> {
				request.chunkSteps().accept(chunk);
				// TODO: Ensure we don't overflow the stack
				read(request);
			},
			request.closeSteps(),
			request.errorSteps(),
			false
		);
	}
	
}
