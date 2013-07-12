package keter.web.routes;


/**
 * Defines route which is a redirection.
 * 
 * @author Dawid Fatyga
 * 
 */
public class RedirectedRoute extends Route {

	public RedirectedRoute(String definition) {
		super(definition);
	}

	@Override
	public Route isRedirect() {
		throw new UnsupportedOperationException(
				"route is already a redirection");
	}
}
