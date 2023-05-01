package com.github.webicitybrowser.webicitybrowser.event.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.github.webicitybrowser.webicitybrowser.event.EventDispatcher;
import com.github.webicitybrowser.webicitybrowser.event.EventListener;

public class EventDispatcherImp<T extends EventListener> implements EventDispatcher<T> {
	
	private List<T> listeners = new ArrayList<>();

	@Override
	public void addListener(T listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(T listener) {
		listeners.remove(listener);
	}

	@Override
	public void fire(Consumer<T> eventFunc) {
		for (T listener: List.copyOf(listeners)) {
			eventFunc.accept(listener);
		}
	}

}
