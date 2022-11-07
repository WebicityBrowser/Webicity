package everyos.desktop.thready.basic.directive;

import java.util.Iterator;

import everyos.desktop.thready.core.gui.directive.ComposedDirectivePool;
import everyos.desktop.thready.core.gui.directive.Directive;
import everyos.desktop.thready.core.gui.directive.DirectivePool;

public class NestingDirectivePool implements ComposedDirectivePool<DirectivePool> {

	@Override
	public DirectivePool directive(Directive directive) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Directive> T getDirective(Class<T> directiveClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Directive> T getDirectiveOrDefault(Class<T> directiveClass, T def) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Directive> T getRawDirective(Class<T> directiveClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Directive[] getCurrentDirectives() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Directive> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addDirectivePool(DirectivePool pool) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeDirectivePool(DirectivePool pool) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DirectivePool[] getCurrentDirectivePools() {
		// TODO Auto-generated method stub
		return null;
	}

}
