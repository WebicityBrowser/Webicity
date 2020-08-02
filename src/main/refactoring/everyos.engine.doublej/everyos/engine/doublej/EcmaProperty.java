package everyos.engine.doublej;

public abstract class EcmaProperty implements EcmaType {
	public boolean enumerable;
	public boolean configurable;
	
	public abstract EcmaProperty duplicate();
	
	public static class EcmaDataProperty extends EcmaProperty {
		public EcmaObject value;
		public boolean writable;
		
		@Override public EcmaDataProperty duplicate() {
			EcmaDataProperty dup = new EcmaDataProperty();
			dup.configurable = this.configurable;
			dup.enumerable = this.enumerable;
			dup.value = this.value;
			dup.writable = this.writable;
			
			return dup;
		}
	}
	public static class EcmaAccessorProperty extends EcmaProperty {
		public EcmaObject get;
		public EcmaObject set;
		
		@Override public EcmaAccessorProperty duplicate() {
			EcmaAccessorProperty dup = new EcmaAccessorProperty();
			dup.configurable = this.configurable;
			dup.enumerable = this.enumerable;
			dup.get = get;
			dup.set = set;
			
			return dup;
		}
		
	}
}
