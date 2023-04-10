package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.cache.imp;

public class CacheNode<T> {

	private final T value;
	
	private CacheNode<T> next;

	public CacheNode(T value) {
		this.value = value;
	}
	
	public T getValue() {
		return this.value;
	}
	
	public void setNext(CacheNode<T> next) {
		this.next = next;
	}
	
	public CacheNode<T> getNext() {
		return this.next;
	}
	
}