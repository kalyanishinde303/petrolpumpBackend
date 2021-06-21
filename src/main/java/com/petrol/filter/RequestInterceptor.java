package com.petrol.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.petrol.dao.UserRepository;
import com.petrol.security.JwtService;
import com.petrol.security.JwtToken;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LogManager.getLogger(RequestInterceptor.class);

	@Autowired
	Environment env;
    @Autowired
	UserRepository userRepo;
	@Autowired
	JwtService jws;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {

		return true;
	}

	private boolean authorisedRequest(HttpServletRequest request) {
		boolean isAuthorized = false;
		List<String> authorizedURLsList = new ArrayList<String>();
		authorizedURLsList.add("validate");
		authorizedURLsList.add("otp/resend");
		authorizedURLsList.add("user");
		authorizedURLsList.add("authenticate");
		// only searching for users because all users end-point need
		// to be authorized without jwt as user does not require password for accessing
		// those end-points
		// and as user might not have password for setting/resetting password or for
		// verifying user
		authorizedURLsList.add("users");
		for (String url : authorizedURLsList) {
			if (request.getRequestURI().contains(url)) {
				isAuthorized = true;
			}
		}
		return isAuthorized;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (logger.isDebugEnabled())
			logger.debug("In RequestInterceptor, post handle method clearing tenant from context for -> "
					+ request.getRequestURI());
	}

}
