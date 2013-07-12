package keter.web.routes;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Router class that holds mapping between controllers and its actions, and
 * actions and its paths.
 * 
 * @author Dawid Fatyga
 * 
 */
public class Router extends TreeMap<String, Map<String, Route>> {
	private static final long serialVersionUID = 1308856385999235227L;
	private static Logger logger = LoggerFactory.getLogger(Router.class);

	private Class<? extends Object> currentController;

	public Router() {
	}

	public Router(Object controller) {
		populateWith(controller);
	}

	/**
	 * Populates router with entries taken from controller class, searching by
	 * methods annotated by @RequestMapping annotation. Stores mapping between
	 * public method (name) and path attached to certain method.
	 * 
	 * TODO: this should more Spring dependent, so it should use Spring build-in
	 * mechanism for resolving mapping between methods and urls
	 * 
	 * @param controllerClass
	 *            controller class to search
	 */
	public Router populateWith(Class<? extends Object> controllerClass) {
		/* Save the controller class to make shortcut for route getter */
		currentController = controllerClass;

		Method[] methods = controllerClass.getMethods();
		String controllerName = getControllerName(controllerClass);

		if (!containsKey(controllerName)) {
			logger.info("registering " + controllerName);
			Map<String, Route> routes = new TreeMap<String, Route>();
			put(controllerName, routes);

			/* Get prefix for all action-level routes */
			String routePreffix = "";
			RequestMapping classRoute = controllerClass
					.getAnnotation(RequestMapping.class);
			if (classRoute != null) {
				routePreffix = classRoute.value()[0];
			}

			for (Method method : methods) {
				RequestMapping actionRoute = method
						.getAnnotation(RequestMapping.class);
				if (actionRoute != null) {
					String routePath = null;
					try {
						routePath = actionRoute.value()[0];
					} catch (ArrayIndexOutOfBoundsException aioobe) {
						routePath = "";
					}
					routes.put(method.getName(), new Route(routePreffix
							+ routePath));
					logger.info(" " + method.getName() + " -> "
							+ routes.get(method.getName()));
				}
			}
		}
		return this;
	}

	/**
	 * Returns Route map for given controller.
	 * 
	 * @return map of routes for given controller
	 */
	public Map<String, Route> gerRoutesFor(String controllerName) {
		if (!containsKey(controllerName)) {
			put(controllerName, new TreeMap<String, Route>());
		}
		return this.get(controllerName);
	}

	public Map<String, Route> gerRoutesFor(
			Class<? extends Object> controllerClass) {
		return gerRoutesFor(getControllerName(controllerClass));
	}

	public Map<String, Route> gerRoutesFor(Object controller) {
		return gerRoutesFor(controller.getClass());
	}

	/**
	 * Returns a route for given controller, and actions. If arguments parameter
	 * is not empty, populates the route. Routes are fetched for current
	 * controller.
	 * 
	 * @param action
	 *            action of the route
	 * @param arguments
	 *            parameters with which route will be populated
	 * @return generated route
	 */
	public Route getRoute(String action, Object... arguments) {
		return getRouteFromMap(gerRoutesFor(currentController), action,
				arguments);
	}

	/**
	 * @see getRoute(Object controller, String action, Object... arguments)
	 */
	public Route getExternalRoute(String controller, String action,
			Object... arguments) {
		return getRouteFromMap(gerRoutesFor(controller), action, arguments);
	}

	/**
	 * The same as getRoute() but returns String instead of Route.
	 * 
	 * @return generated route
	 */
	public String getPath(String action, Object... arguments) {
		return getRoute(action, arguments).toString();
	}

	/**
	 * The same as getExternalRoute() but returns String instead of Route.
	 * 
	 * @return generated route
	 */
	public String getExternalPath(String controller, String action,
			Object... arguments) {
		return getExternalRoute(controller, action, arguments).toString();
	}

	/**
	 * Route map population based on controller instance
	 * 
	 * @param controller
	 *            controller instance
	 */
	public Router populateWith(Object controller) {
		return populateWith(controller.getClass());
	}

	/**
	 * Converts controller's class name to "controller name" - name without
	 * "Controller" suffix with first letter lowercased. Examples: <br>
	 * <code>
	 *   SessionController -> session
	 *   VeryFunnyController -> veryFunny
	 * </code>
	 * 
	 * @param controller
	 *            controller from which name is taken
	 * @return name of a controller
	 */
	protected String getControllerName(Class<? extends Object> controllerClass) {
		String name = controllerClass.getSimpleName();
		name = name.replaceAll("Controller", "");
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}

	private Route getRouteFromMap(Map<String, Route> routes, String action,
			Object[] arguments) {
		if (routes.containsKey(action)) {
			Route route = routes.get(action);
			return route.appendAll(arguments);
		}
		return null;
	}
}
