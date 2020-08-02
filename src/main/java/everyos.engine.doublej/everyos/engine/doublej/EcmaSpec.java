package everyos.engine.doublej;

import everyos.engine.doublej.EcmaProperty.EcmaAccessorProperty;
import everyos.engine.doublej.EcmaProperty.EcmaDataProperty;

public final class EcmaSpec {
	public static EcmaCompletion sameValue(EcmaType x, EcmaType y) {
		return new EcmaCompletion(x==y||x.equals(y));
	}
	
	public static EcmaCompletion isExtensible(EcmaObject o) {
		return o.isExtensible();
	}
	
	public static boolean isGenericDescriptor(EcmaProperty desc) {
		return !(desc instanceof EcmaAccessorProperty || desc instanceof EcmaDataProperty);
	}
	
	public static EcmaCompletion ordinaryGetPrototypeOf(EcmaObject o) {
		return new EcmaCompletion(o.prototype);
	}	
	public static EcmaCompletion ordinarySetPrototypeOf(EcmaObject o, EcmaObject v) {
		if (((EcmaBoolean) sameValue(o, v).value).getValue()) return new EcmaCompletion(true);
		if (!o.extensible) return new EcmaCompletion(false);
		EcmaObject p=v; boolean done=false;
		while (!done) {
			if (p==null) {
				done = true;
			} else if (((EcmaBoolean) sameValue(p, o).value).getValue()) {
				return new EcmaCompletion(false);
			} else {
				try {
					if (!o.getClass().getDeclaredMethod("getPropertyOf").equals(EcmaObject.class.getDeclaredMethod("getPropertyOf"))) {
						done = true;
					} else {
						p = p.prototype;
					}
				} catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
					done = true;
				}
			}
		}
		o.prototype = v;
		return new EcmaCompletion(true);
	}
	public static EcmaCompletion ordinaryIsExtensible(EcmaObject o) {
		return new EcmaCompletion(o.extensible);
	}
	public static EcmaCompletion ordinaryPreventExtensions(EcmaObject o) {
		o.extensible = false;
		return new EcmaCompletion(true);
	}
	public static EcmaCompletion ordinaryGetOwnProperty(EcmaObject o, EcmaType key) {
		if (!o.properties.containsKey(key)) return new EcmaCompletion(new EcmaUndefined());
		return new EcmaCompletion(o.properties.get(key).duplicate());
	}
	public static EcmaCompletion ordinaryDefineOwnProperty(EcmaObject o, EcmaType p, EcmaProperty desc) {
		EcmaCompletion current = o.getOwnProperty(p);
		if (current.type!=EcmaCompletion.NORMAL) return current;
		EcmaCompletion extensible = isExtensible(o);
		if (extensible.type!=EcmaCompletion.NORMAL) return extensible;
		
		return validateAndApplyPropertyDescriptor(o, p, ((EcmaBoolean) extensible.value).getValue(), desc, current.value);
	}
	public static EcmaCompletion validateAndApplyPropertyDescriptor(EcmaType o, EcmaType p, boolean extensible, EcmaProperty desc, EcmaType currento) {
		if (currento instanceof EcmaUndefined) {
			if (!extensible) return new EcmaCompletion(false);
			if (!(o instanceof EcmaUndefined)) {
				((EcmaObject) o).properties.put(p, desc.duplicate());
			}
			return new EcmaCompletion(true);
		}
		//TODO: Every field absent?
		EcmaProperty current = (EcmaProperty) currento;
		if (!current.configurable) {
			if (desc.configurable) return new EcmaCompletion(false);
			if (!(current.enumerable==desc.enumerable)) return new EcmaCompletion(false);
		}
		
		if (isGenericDescriptor(desc)) {
		} else if (desc instanceof EcmaDataProperty != current instanceof EcmaDataProperty) {
			if (current.configurable==false) return new EcmaCompletion(false);
			if (!(o instanceof EcmaUndefined)) {
				EcmaProperty np = current instanceof EcmaDataProperty?new EcmaAccessorProperty():new EcmaDataProperty();
				np.configurable = current.configurable;
				np.enumerable = current.enumerable;
				((EcmaObject) o).properties.put(p, np);
			} else if (current instanceof EcmaDataProperty) {
				if (!(current.configurable||((EcmaDataProperty)current).writable)) {
					if (((EcmaDataProperty)desc).writable) return new EcmaCompletion(false);
					if (((EcmaDataProperty)desc).value!=((EcmaDataProperty)current).value) return new EcmaCompletion(false);
					return new EcmaCompletion(true);
				}
			} else {
				if (!current.configurable) {
					if (((EcmaAccessorProperty) desc).set!=((EcmaAccessorProperty) current).set) return new EcmaCompletion(false);
					if (((EcmaAccessorProperty) desc).get!=((EcmaAccessorProperty) current).get) return new EcmaCompletion(false);
					return new EcmaCompletion(true);
				}
			}
		}
		//TODO: Other fields?
		return new EcmaCompletion(true);
	}
	
	//TODO: HasProperty onwards
}