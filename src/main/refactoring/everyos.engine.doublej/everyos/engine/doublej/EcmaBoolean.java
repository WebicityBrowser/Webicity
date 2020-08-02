package everyos.engine.doublej;

import java.util.Objects;

public class EcmaBoolean implements EcmaType {
	private boolean value;

	public EcmaBoolean(boolean b) {
		this.value = b;
	}

	public boolean getValue() {
		return value;
	}
	
	@Override public boolean equals(Object o) {
		return o instanceof EcmaBoolean && ((EcmaBoolean) o).value == value;
	}
	@Override public int hashCode() {
		return Objects.hash(value);
	}
}
