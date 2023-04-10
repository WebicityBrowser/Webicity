package com.github.webicitybrowser.thready.gui.graphical.cache.imp;

import java.util.function.Function;

import com.github.webicitybrowser.thready.gui.graphical.cache.MappingCache;

public class MappingCacheImp<T, U> implements MappingCache<T, U> {
	
	// TODO: Create relevant tests

	private final Function<Integer, U[]> arrayFactory;
	private final Function<U, T> keyFinder;
	
	private U[] mappingCacheAsArray;
	
	// Built-in linked lists do not give us access to the node
	private final CacheNode<U> mappingCache = new CacheNode<>(null);
	
	public MappingCacheImp(Function<Integer, U[]> arrayFactory, Function<U, T> keyFinder) {
		this.arrayFactory = arrayFactory;
		this.keyFinder = keyFinder;
		
		mappingCacheAsArray = arrayFactory.apply(0);
	}
	
	@Override
	public void recompute(T[] children, Function<T, U> mappingGenerator) {
		ensureCacheContainsCurrentMappings(children, mappingGenerator);
		this.mappingCacheAsArray = saveCacheToArray(arrayFactory.apply(children.length));
	}

	@Override
	public U[] getComputedMappings() {
		return mappingCacheAsArray;
	}
	
	private void ensureCacheContainsCurrentMappings(T[] children, Function<T, U> mappingGenerator) {
		CacheNode<U> lastNode = mappingCache;
		
		for (T child: children) {
			lastNode = ensureCacheContainsComponentMapping(child, mappingGenerator, lastNode);
		}
		
		lastNode.setNext(null);
	}

	private CacheNode<U> ensureCacheContainsComponentMapping(
		T child, Function<T, U> mappingGenerator, CacheNode<U> lastNode
	) {
		CacheNode<U> nextNode = searchForMapping(child, lastNode);
		return nextNode != null ?
			removeCachedMappingsBetween(lastNode, nextNode) :
			generateNextMapping(child, mappingGenerator, lastNode);
	}

	private CacheNode<U> removeCachedMappingsBetween(CacheNode<U> lastNode, CacheNode<U> nextNode) {
		CacheNode<U> currentNode = lastNode;
		while (currentNode.getNext() != null && currentNode.getNext() != nextNode) {
			currentNode = currentNode.getNext();
		}
			
		lastNode.setNext(nextNode);
		
		return nextNode;
	}
	
	private CacheNode<U> generateNextMapping(
		T child, Function<T, U> mappingGenerator, CacheNode<U> lastNode
	) {
		U mapping = mappingGenerator.apply(child);
		lastNode.setNext(new CacheNode<>(mapping));
		
		return lastNode.getNext();
	}

	private CacheNode<U> searchForMapping(T mappingComponent, CacheNode<U> startNode) {
		CacheNode<U> currentNode = startNode.getNext();
		while (currentNode != null) {
			if (keyFinder.apply(currentNode.getValue()).equals(mappingComponent)) {
				return currentNode;
			}
			currentNode = currentNode.getNext();
		}
		
		return null;
	}
	
	private U[] saveCacheToArray(U[] componentMappings) {
		CacheNode<U> nextNode = mappingCache.getNext();
		for (int i = 0; nextNode != null; i++) {
			componentMappings[i] = nextNode.getValue();
			nextNode = nextNode.getNext();
		}
		
		return componentMappings;
	}
	
}
