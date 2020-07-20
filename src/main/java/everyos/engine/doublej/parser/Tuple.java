package everyos.engine.doublej.parser;

class Tuple<T1, T2> {
	private T1 l;
	private T2 r;

	public Tuple(T1 l, T2 r) {
		this.l = l;
		this.r = r;
	}
	
	public T1 getT1() {
		return l;
	}
	public T2 getT2() {
		return r;
	}
}