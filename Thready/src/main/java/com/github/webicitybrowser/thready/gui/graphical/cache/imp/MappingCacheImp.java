package com.github.webicitybrowser.thready.gui.graphical.cache.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.github.webicitybrowser.thready.gui.graphical.cache.MappingCache;

public class MappingCacheImp<T, U> implements MappingCache<T, U> {
	
	// TODO: Create relevant tests

	private final Function<U, T> keyFinder;
	
	private List<U> mappingCacheAsList;
	
	// Built-in linked lists do not give us access to the node
	private final CacheNode<U> mappingCache = new CacheNode<>(null);
	
	public MappingCacheImp(Function<U, T> keyFinder) {
		this.keyFinder = keyFinder;
	}
	
	@Override
	public void recompute(List<T> children, Function<T, U> mappingGenerator) {
		ensureCacheContainsCurrentMappings(children, mappingGenerator);
		this.mappingCacheAsList = saveCacheToList(children.size());
	}

	@Override
	public List<U> getComputedMappings() {
		return mappingCacheAsList;
	}
	
	private void ensureCacheContainsCurrentMappings(List<T> children, Function<T, U> mappingGenerator) {
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
	
	private List<U> saveCacheToList(int size) {
		List<U> componentMappings = new ArrayList<>(size);
		CacheNode<U> nextNode = mappingCache.getNext();
		while (nextNode != null) {
			componentMappings.add(nextNode.getValue());
			nextNode = nextNode.getNext();
		}
		
		return componentMappings;
	}
	
}
