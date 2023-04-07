package com.github.webicitybrowser.webicitybrowser;

import com.github.webicitybrowser.thready.color.Colors;
import com.github.webicitybrowser.thready.gui.directive.basic.directive.BackgroundColorDirective;
import com.github.webicitybrowser.thready.gui.graphical.base.GUIContent;
import com.github.webicitybrowser.thready.gui.graphical.base.imp.GUIContentImp;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeelBuilder;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.SimpleLookAndFeel;
import com.github.webicitybrowser.thready.gui.tree.basics.ContainerComponent;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.thready.windowing.core.GraphicsSystem;
import com.github.webicitybrowser.thready.windowing.skija.SkijaGraphicsSystem;

public class Main {

	public static void main(String[] args) {
		GraphicsSystem graphicsSystem = SkijaGraphicsSystem.createDefault();
		
		Component rootComponent = ContainerComponent.create();
		rootComponent.directive(BackgroundColorDirective.of(Colors.RED));
		
		LookAndFeelBuilder lookAndFeelBuilder = LookAndFeelBuilder.create();
		SimpleLookAndFeel.installTo(lookAndFeelBuilder);
		LookAndFeel lookAndFeel = lookAndFeelBuilder.build();
		
		GUIContent content = new GUIContentImp();
		content.setRoot(rootComponent, lookAndFeel);
		
		graphicsSystem.createWindow(window -> {
			window
				.getScreen()
				.setScreenContent(content);
		});
		
	}
	
}
