package keter.web.controller;

import keter.web.security.AnonymousUserAdapter;
import keter.web.security.UserAdapter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SecurityController {
	
	public static class Routes {
		private static final String Prefix = "";
		static final String login = Prefix + "/login";
		static final String _403 = Prefix + "/403";
	}

	public static class Views {
		private static final String Prefix = "";
		static final String login = Prefix + "/login";
		static final String _403 = Prefix + "/403";
	}
	

	@RequestMapping(value = Routes.login, method = RequestMethod.GET)
	public String login() {
		return Views.login;
	}

	@RequestMapping(value = Routes._403, method = RequestMethod.GET)
	public String handle403() {
		return Views._403;
	}
	
	/**
	 * Returns UserAdapter object for the currently logged in user, or null if
	 * no User is logged in.
	 * 
	 * @return currently logged user or null
	 */
	public static UserAdapter getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();

		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserAdapter) {
				return ((UserAdapter) principal);
			}
		}

		return new AnonymousUserAdapter();
	}

	/**
	 * Utility method to determine if the current user is logged in.
	 * 
	 * @return true if user is logged in
	 */
	public static boolean isLoggedIn() {
		return !(getCurrentUser() instanceof AnonymousUserAdapter);
	}
}
