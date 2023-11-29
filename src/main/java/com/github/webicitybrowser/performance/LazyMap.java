package com.github.webicitybrowser.performance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * A map that is lazily initialized. Since the rendering engine
 * uses a lot of maps, but doesn't always store values in them,
 * and maps can be quite heavy, this class allows for lazy
 * initialization of maps. It would be more ideal to null out
 * the map when it is empty, but that would require null checks
 * everywhere, which would make the code harder to read and
 * maintain, as well as making it more error prone.
 * 
 * Eventually, the goal is to find an alternative to using
 * so many maps everywhere.
 * 
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public abstract class LazyMap<K, V> implements Map<K, V> {

	private Map<K, V> initialized = null;
	
	@Override
	public int size() {
		if (initialized == null) {
			return 0;
		}
		return initialized.size();
	}

	@Override
	public boolean isEmpty() {
		if (initialized == null) {
			return false;
		}
		return initialized.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		if (initialized == null) {
			return false;
		}
		return initialized.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		if (initialized == null) {
			return false;
		}
		return initialized.containsValue(value);
	}

	@Override
	public V get(Object key) {
		if (initialized == null) {
			return null;
		}
		return initialized.get(key);
	}

	@Override
	public V put(K key, V value) {
		ensureMapInitialized();
		return initialized.put(key, value);
	}

	@Override
	public V remove(Object key) {
		if (initialized == null) {
			return null;
		}
		return initialized.remove(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		ensureMapInitialized();
		initialized.putAll(m);
	}

	@Override
	public void clear() {
		if (initialized == null) {
			return;
		}
		initialized.clear();
	}

	@Override
	public Set<K> keySet() {
		if (initialized == null) {
			return Set.of();
		}
		return initialized.keySet();
	}

	@Override
	public Collection<V> values() {
		if (initialized == null) {
			return new ArrayList<>(0);
		}
		return initialized.values();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		if (initialized == null) {
			return Set.of();
		}
		return initialized.entrySet();
	}

	private void ensureMapInitialized() {
		if (initialized == null) {
			this.initialized = initialize();
		}
	}

	abstract protected Map<K, V> initialize();
	
}
