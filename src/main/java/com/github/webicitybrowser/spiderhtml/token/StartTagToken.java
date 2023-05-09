package com.github.webicitybrowser.spiderhtml.token;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spiderhtml.performance.StringCache;

public class StartTagToken extends TagToken {
	
	private final List<AttributeBuilder> attributes = new ArrayList<>();
	
	private StringBuilder attributeNameBuilder;
	private StringBuilder attributeValueBuilder;

	public StartTagToken(String name) {
		super(name);
	}

	public void startNewAttribute() {
		this.attributeNameBuilder = new StringBuilder();
		this.attributeValueBuilder = new StringBuilder();
		attributes.add(new AttributeBuilder(attributeNameBuilder, attributeValueBuilder));
	}
	
	public void appendToAttributeName(int ch) {
		attributeNameBuilder.appendCodePoint(ch);
	}
	
	public void appendToAttributeValue(int ch) {
		attributeValueBuilder.appendCodePoint(ch);
	}
	
	public StartTagAttribute[] getAttributes(StringCache stringCache) {
		StartTagAttribute[] attributeArr = new StartTagAttribute[attributes.size()];
		for (int i = 0; i < attributeArr.length; i++) {
			AttributeBuilder attrBuild = attributes.get(i);
			attributeArr[i] = new StartTagAttribute(
				stringCache.get(attrBuild.nameBuilder.toString()),
				stringCache.get(attrBuild.valueBuilder.toString()));
		}
		
		return attributeArr;
	}
	
	public static record StartTagAttribute(String name, String value) {}
	
	private static record AttributeBuilder(StringBuilder nameBuilder, StringBuilder valueBuilder) {}
	
}
