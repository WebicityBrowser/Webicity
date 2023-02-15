package everyos.browser.webicity.core.ui.imp;

import java.util.Optional;

import everyos.browser.webicity.core.ui.Frame;
import everyos.browser.webicity.core.ui.event.FrameMutationEventListener;
import everyos.browser.webicity.core.ui.renderer.Renderer;
import everyos.web.spec.uri.URL;

public class FrameImp implements Frame {
	
	private Optional<Renderer> currentRenderer = Optional.empty();

	public FrameImp() {}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return currentRenderer
			.map(renderer -> renderer.getName())
			.orElse("about:blank");
	}

	@Override
	public URL getURL() {
		return currentRenderer
			.map(renderer -> renderer.getURL())
			.orElse(URL.createFromStringSafe("about:blank"));
	}

	@Override
	public void navigate(URL url) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub

	}

	@Override
	public void back() {
		// TODO Auto-generated method stub

	}

	@Override
	public void forward() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public Renderer getCurrentRenderer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addFrameMutationListener(FrameMutationEventListener mutationListener, boolean sync) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeFrameMutationListener(FrameMutationEventListener mutationListener) {
		// TODO Auto-generated method stub

	}

}
