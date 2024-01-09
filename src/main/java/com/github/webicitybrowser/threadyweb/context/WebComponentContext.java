package com.github.webicitybrowser.threadyweb.context;

public interface WebComponentContext {
	
	<T> T getContext(Class<T> contextCls);

}
