package com.github.webicitybrowser.spec.css.property.shared.length;

public interface AbsoluteLengthValue extends LengthValue {
	
	AbsoluteLengthUnit getUnit();

	float getValue();
	
	public static enum AbsoluteLengthUnit {
		CM, MM, Q, IN, PC, PT, PX
	}

}
