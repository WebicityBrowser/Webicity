package everyos.engine.doublej;

public interface EcmaCallable {
	public EcmaCompletion call(EcmaObject self, EcmaType[] params);
}
