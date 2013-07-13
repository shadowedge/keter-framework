package keter.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CommonController {

	public static class Routes {
		private static final String Prefix = "/framework/errors";
		static final String _404 = Prefix + "/404";
		static final String _403 = Prefix + "/403";
	} 

	public static class Views {
		private static final String Prefix = "/framework/errors";
		static final String _404 = Prefix + "/404";
		static final String _403 = Prefix + "/403";
	}
	
	/**
	 * <p>Method ：registPost
	 * <p>Description : 首页
	 * @return 
	 * @author  gulixing@msn.com
	 * @version 1.0.0
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String registPost() {
		return "/index";
	}

	@RequestMapping(value = Routes._404, method = RequestMethod.GET)
	public String handle404() {
		return Views._404;
	}

	@RequestMapping(value = Routes._403, method = RequestMethod.GET)
	public String handle403() {
		return Views._403;
	}
}
