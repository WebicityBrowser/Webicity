package everyos.browser.spec.jcss.parser;

public class Tuple<T, T2> {
	private final T t1;
	private final T2 t2;

	public Tuple(T t1, T2 t2) {
		this.t1 = t1;
		this.t2 = t2;
	}
	
	public T getT1() {
		return this.t1;
	}
	
	public T2 getT2() {
		return this.t2;
	}
}
