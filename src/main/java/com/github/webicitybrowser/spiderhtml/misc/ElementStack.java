package com.github.webicitybrowser.spiderhtml.misc;

import java.util.Stack;

import com.github.webicitybrowser.spec.html.parse.tree.HTMLLeaf;

public class ElementStack {

	private final Stack<HTMLLeaf> stack = new Stack<>();
	
	public void push(HTMLLeaf htmlLeaf) {
		stack.add(htmlLeaf);
	}

	public HTMLLeaf peek() {
		return stack.peek();
	}

	public void pop() {
		stack.pop();
	}

	
	
}
