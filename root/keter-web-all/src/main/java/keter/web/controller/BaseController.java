package keter.web.controller;

import keter.dao.base.Pagination;
import keter.domain.User;
import keter.web.routes.RestfulRoutes;
import keter.web.routes.Router;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Base controller for every controller in application. Provides some basic
 * helper methods useful in controller logic.
 * 
 * @author Dawid Fatyga
 * 
 */
@Transactional
public abstract class BaseController {

	public static class Routes implements RestfulRoutes {
	}

	protected Pagination pagination = new Pagination();

	/**
	 * Calculates context path for redirection.
	 * 
	 * @param contextPath
	 *            target url
	 * @param arguments
	 *            arguments which will be passed to route
	 * @return context path suitable for redirection
	 */
	public String redirectTo(String action, Object... arguments) {
		return getRoutes().getRoute(action).appendAll(arguments)
				.withRedirection();
	}

	/**
	 * Simple redirection
	 * 
	 * @param action
	 *            target of the recirection
	 */
	public static String redirectToUrl(String action) {
		return "redirect:" + action;
	}

	private static Router routes;

	synchronized protected static Router getRouter() {
		if (routes == null) {
			routes = new Router();
		}
		return routes;
	}

	@ModelAttribute("routes")
	public Router getRoutes() {
		return getRouter().populateWith(this);
	}

	public User getCurrentUser() {
		if (isLoggedIn()) {
			return SecurityController.getCurrentUser().getUser();
		} else {
			User guest = new User();
			guest.setUsername("访客");
			return guest;
		}
	}

	public boolean isLoggedIn() {
		return SecurityController.isLoggedIn();
	}
}
