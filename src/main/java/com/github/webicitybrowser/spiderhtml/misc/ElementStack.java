package com.github.webicitybrowser.spiderhtml.misc;

import java.util.Stack;

import com.github.webicitybrowser.spec.dom.node.Node;

public class ElementStack {

	private final Stack<Node> stack = new Stack<>();
	
	public void push(Node node) {
		stack.add(node);
	}

	public Node peek() {
		return stack.peek();
	}

	public void pop() {
		stack.pop();
	}

	
	
}
