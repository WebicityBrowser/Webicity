package com.github.webicitybrowser.spec.stream;

import java.util.function.Consumer;

public record ReadRequest(Consumer<Object> chunkSteps, Runnable closeSteps, Consumer<Exception> errorSteps, boolean readAll) {
	
	public ReadRequest(Consumer<Object> chunkSteps, Runnable closeSteps, Consumer<Exception> errorSteps) {
		this(chunkSteps, closeSteps, errorSteps, false);
	}

}
