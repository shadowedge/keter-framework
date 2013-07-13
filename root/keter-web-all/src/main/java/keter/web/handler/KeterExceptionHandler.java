package keter.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class KeterExceptionHandler implements HandlerExceptionResolver {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(KeterExceptionHandler.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		logger.error("系统异常",ex);
		if(ex instanceof AuthenticationException){
			return new ModelAndView("/login?error=1");
		}
		if(ex instanceof AccessDeniedException){
			return new ModelAndView("/framework/errors/403");
		}
		return new ModelAndView("/framework/errors/error");
	}

}
