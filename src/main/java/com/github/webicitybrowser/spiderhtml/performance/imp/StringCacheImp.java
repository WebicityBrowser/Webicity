package com.github.webicitybrowser.spiderhtml.performance.imp;

import java.util.HashMap;
import java.util.Map;

import com.github.webicitybrowser.spiderhtml.performance.StringCache;

public class StringCacheImp implements StringCache {

	private Map<String, String> cache = new HashMap<>();
	
	@Override
	public String get(String str) {
		if (str.length() > 6) {
			return str;
		}
		
		cache.putIfAbsent(str, str);
		return cache.get(str);
	}

}
