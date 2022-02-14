package everyos.browser.spec.jhtml.browsing;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.spec.javadom.imp.JDDocumentBuilder;
import everyos.browser.spec.javadom.intf.Document;
import everyos.browser.spec.javadom.intf.Element;
import everyos.browser.spec.jfetch.imp.JRequest;
import everyos.browser.spec.jfetch.intf.Request;
import everyos.browser.spec.jhtml.enm.HistoryHandlingBehavior;
import everyos.browser.spec.jhtml.intf.Window;
import everyos.browser.spec.jhtml.intf.WindowProxy;
import everyos.browser.spec.jnet.URL;

public class BrowsingContext {
	private WindowProxy proxy;
	private BrowsingContext opener;
	private boolean disowned = false;
	private boolean isClosing = false;
	private List<SessionHistoryEntry> sessionHistory = new ArrayList<>();
	private Window activeWindow;
	private Document activeDocument;
	private int virtualBrowsingContextGroupID = 0;
	private URL initialURL;
	private Origin openerOrigin;
	private Origin creatorOrigin;
	private URL creatorURL;
	private URL creatorBaseURL;
	
	public BrowsingContext(Document creator, Element embedder, BrowsingContextGroup group) {
		if (creator!=null) {
			this.creatorOrigin = creator.getOrigin();
			this.creatorURL = creator.getURLAsURL();
			this.virtualBrowsingContextGroupID = creator.getTopLevelContext().getVirtualBrowsingContextGroupID();
		}
		List<Integer> sandboxFlags = determineTheCreationSandboxingFlags(this, embedder);
		Origin origin = determineTheOrigin(this, URL.ABOUT_BLANK, sandboxFlags, creatorOrigin, null);
		PermissionsPolicy permissionsPolicy = createAPermissionsPolicy(this, origin);
		//TODO: Javascript stuff
		URL topLevelCreationURL = URL.ABOUT_BLANK;
		if (embedder!=null) {
			//TODO
		}
		//TODO: I skipped a few things
		
		Document document = new JDDocumentBuilder()
			.setType(Document.HTML)
			.setQuirksMode(Document.QUIRKS_MODE)
			.setContentType("text/html")
			.setOrigin(origin)
			//TODO
			.setReadyForPostLoadTasks(true)
			.build();
		
		assert(document.getURLAsURL().equals(URL.ABOUT_BLANK)); //TODO
		//TODO
		//setActiveDocument(document);
		//TODO
		sessionHistory.add(SessionHistoryEntry.of(document));
	}
	
	public int getVirtualBrowsingContextGroupID() {
		return virtualBrowsingContextGroupID;
	}
	
	public void navigate(BrowsingContext sourceBrowsingContext, Object resource, boolean exceptionsEnabled,
		HistoryHandlingBehavior historyHandling, String navigationType) {
		
		if (resource instanceof URL) {
			resource = new JRequest((URL) resource);
		}
		if (resource instanceof Request && historyHandling == HistoryHandlingBehavior.RELOAD) {
			((Request) resource).setReloadNavigationFlag(true);
		}
		//TODO: Allowed to navigate code
		//TODO: Check pre-existing attempt
		//TODO: Fragments thing in my giant if statement
		if (historyHandling!=HistoryHandlingBehavior.RELOAD && resource instanceof Request &&
			((Request) resource).getURL().equals(activeDocument.getURLAsURL()) &&
			((Request) resource).getURL().getFragment()!=null) {
			
			//TODO: Navigate to a fragment
		}
		
		//TODO: Replace
		Origin activeDocumentNavigationOrigin = activeDocument.getOrigin();
		//TODO: If script involved
		//Origin incumbentNavigationOrigin = 
	}
	
	public void navigate(BrowsingContext sourceBrowsingContext, Object resource) {
		navigate(sourceBrowsingContext, resource, false, HistoryHandlingBehavior.DEFAULT, "other");
	}
	
	private void setActiveDocument(Document document) {
		Window window = document.getRelevantGlobalObject();
		activeWindow = window;
		window.setAssociatedDocument(document);
		//TODO
	}

	private Origin determineTheOrigin(BrowsingContext browsingContext, URL url, List<Integer> sandboxFlags,
		Origin invocationOrigin, Origin activeDocumentNavigationOrigin) {
		
		//TODO: Flag
		if (url==null) {
			return new Origin();
		}
		if (activeDocumentNavigationOrigin!=null && url.getProtocol().equals("javascript")) {
			return activeDocumentNavigationOrigin;
		}
		if (invocationOrigin!=null && url.equals(URL.ABOUT_BLANK)) {
			return invocationOrigin;
		}
		if (url.equals(URL.ofSafe("about:srcdoc"))) {
			return this.getContainerDocument().getOrigin();
		}
		
		return url.getOrigin();
	}

	private Document getContainerDocument() {
		return null;
	}

	private List<Integer> determineTheCreationSandboxingFlags(BrowsingContext browsingContext, Element embedder) {
		return List.of(); //TODO
	}
	
	private PermissionsPolicy createAPermissionsPolicy(BrowsingContext browsingContext, Origin origin) {
		// TODO Finish. Also, this might need moved
		return new PermissionsPolicy() {};
	}
}
