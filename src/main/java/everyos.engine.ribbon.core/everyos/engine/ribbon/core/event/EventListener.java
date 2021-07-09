package everyos.engine.ribbon.core.event;

public interface EventListener<T extends UIEvent> {
	public void accept(T e);
}
