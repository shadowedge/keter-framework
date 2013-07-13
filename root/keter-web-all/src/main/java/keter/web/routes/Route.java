package keter.web.routes;


/**
 * Route class used by Router to define some additional functionality to pure
 * stringy-route definition like formatting parametrized urls.
 * 
 * @author Dawid Fatyga
 */
public class Route {
	private String definition;
	private String formatedUrl;

	/**
	 * Elements such as {param} will be replaced by %s to do formatting.
	 */
	protected static final String PARAM_PATTERN = "\\{[^\\}]+\\}";

	public Route(String definition) {
		setDefinition(definition);
	}

	/**
	 * Formats the URL replacing path parameters by corresponding arguments.
	 * 
	 * @param arguments
	 *            arguments passed to URL
	 * @return formated URL
	 */
	public String format(Object... arguments) {
		return String.format(formatedUrl, arguments);
	}

	/**
	 * Partially formats the url, allowing to chain append() calls to get fully
	 * formatted url. This method is called by ELResolver to achieve syntax
	 * similar to:<code>
	 * route[param][param]
	 * </code>
	 * 
	 * @param argument
	 *            arguments passed to format url
	 * @return partially formated url
	 */
	public Route append(Object argument) {
		return new PartiallyFormattedRoute(definition, argument);
	}

	/**
	 * Works the same as format but returns route instead of string.
	 * 
	 * @param arguments
	 *            arguments passed to format
	 * @return route formatted with given arguments
	 */
	public Route appendAll(Object[] arguments) {
		return new Route(format(arguments));
	}

	/**
	 * Returns route which is a redirection to original route.
	 * 
	 * @return generated route
	 */
	public Route isRedirect() {
		return new RedirectedRoute("redirect:" + definition);
	}

	/**
	 * The same as isRedirect() but returns String instead of Route.
	 * 
	 * @return generated route
	 */
	public String withRedirection() {
		return isRedirect().toString();
	}

	@Override
	public String toString() {
		return getDefinition();
	}

	public String getDefinition() {
		return definition;
	}

	public void setFormattedUrl(String formattedUrl) {
		this.formatedUrl = formattedUrl;
	}

	/**
	 * Sets the definition of url and builds its formated representation.
	 * 
	 * @param definition
	 *            definition of a URL
	 */
	public void setDefinition(String definition) {
		this.definition = definition;
		this.formatedUrl = this.definition.replaceAll(PARAM_PATTERN, "%s");
	}
}
