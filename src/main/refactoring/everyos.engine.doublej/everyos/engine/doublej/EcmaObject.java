package everyos.engine.doublej;

import java.util.HashMap;

public class EcmaObject implements EcmaType {
	protected HashMap<EcmaType, EcmaProperty> properties;
	
	protected EcmaObject prototype;
	protected boolean extensible;
	
	public EcmaCompletion getPrototypeOf() {
		return EcmaSpec.ordinaryGetPrototypeOf(this);
	}
	public EcmaCompletion isExtensible() {
		return EcmaSpec.ordinaryIsExtensible(this);
	}
	public EcmaCompletion preventExtensions() {
		return EcmaSpec.ordinaryPreventExtensions(this);
	}
	public EcmaCompletion getOwnProperty(EcmaType key) {
		return EcmaSpec.ordinaryGetOwnProperty(this, key);
	}
	public EcmaCompletion defineOwnProperty(EcmaType p, EcmaProperty desc) {
		return EcmaSpec.ordinaryDefineOwnProperty(this, p, desc);
	}
}
