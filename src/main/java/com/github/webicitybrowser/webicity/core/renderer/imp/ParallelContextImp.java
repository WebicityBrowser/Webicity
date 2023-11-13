package com.github.webicitybrowser.webicity.core.renderer.imp;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.github.webicitybrowser.spec.htmlbrowsers.ParallelContext;

public class ParallelContextImp implements ParallelContext {

	private final Executor executor = Executors.newCachedThreadPool();

	@Override
	public void inParallel(Runnable runnable) {
		executor.execute(runnable);
	}

}
