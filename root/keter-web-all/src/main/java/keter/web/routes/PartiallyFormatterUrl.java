package keter.web.routes;



/**
 * Partially formatted URL. Allows to build chained formatting append() calls.
 * 
 * @author Dawid Fatyga
 */
class PartiallyFormattedRoute extends Route {
	public PartiallyFormattedRoute(String definition, Object argument) {
		super(definition);
		append(argument);
	}

	@Override
	public PartiallyFormattedRoute append(Object argument) {
		setFormattedUrl(getDefinition().replaceFirst(PARAM_PATTERN, "%s"));
		setDefinition(format(argument));
		return this;
	}
}