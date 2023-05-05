package com.github.webicitybrowser.webicity.renderer.backend.html.cssom;

public record CSSOMFilterEntry<T, U>(int priority, CSSOMFilter<T, U> filter, CSSOMNode<T, U> cssomNode) {

}
