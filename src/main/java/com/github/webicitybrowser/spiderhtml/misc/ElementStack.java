package com.github.webicitybrowser.spiderhtml.misc;

import java.util.Stack;

import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.html.parse.ParserSettings;

public class ElementStack {

	private ParserSettings settings;
	private final Stack<Node> stack = new Stack<>();
	
	public ElementStack(ParserSettings settings) {
		this.settings = settings;
	}

	public void push(Node node) {
		stack.add(node);
	}

	public Node peek() {
		return stack.peek();
	}
	
	public Node peek(int pos) {
		return stack.get(stack.size() - pos - 1);
	}

	public Node pop() {
		Node node = stack.pop();
		settings.onNodePopped(node);
		return node;
	}

	public int size() {
		return stack.size();
	}

	public boolean isEmpty() {
		return stack.isEmpty();
	}
	
}
