package everyos.engine.doublej;

public class EcmaCompletion {
	public EcmaCompletion(EcmaType value) {
		this.value = value;
	}
	public EcmaCompletion(boolean b) {
		this.value = new EcmaBoolean(b);
	}

	public EcmaType value;
	public EcmaString target;
	public int type;
	
	public static final int NORMAL = 0;
	public static final int BREAK = 1;
	public static final int CONTINUE = 2;
	public static final int RETURN = 3;
	public static final int THROW = 4;
}
