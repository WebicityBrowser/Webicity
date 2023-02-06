package everyos.desktop.thready.basic.layout.flowing.directive;

import everyos.desktop.thready.core.gui.directive.Directive;
import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.core.positioning.RelativeSize;
import everyos.desktop.thready.core.positioning.imp.RelativeSizeImp;

public interface SizeDirective extends Directive {

	RelativeSize getSize();

	public static Directive of(RelativeSize size) {
		return new SizeDirective() {
			
			@Override
			public Class<? extends Directive> getDirectiveClass() {
				return SizeDirective.class;
			}
			
			@Override
			public RelativeSize getSize() {
				return size;
			}
			
		};
	}
	
	public static Directive of(AbsoluteSize size) {
		return of(RelativeSizeImp.convertFrom(size));
	}
	
}
