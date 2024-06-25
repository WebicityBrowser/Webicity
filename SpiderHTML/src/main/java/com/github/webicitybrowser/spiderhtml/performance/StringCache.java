package com.github.webicitybrowser.spiderhtml.performance;

import com.github.webicitybrowser.spiderhtml.performance.imp.StringCacheImp;

public interface StringCache {

	String get(String str);

	static StringCache create() {
		return new StringCacheImp();
	}
	
}
