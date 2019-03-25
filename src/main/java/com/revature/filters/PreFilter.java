package com.revature.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class PreFilter extends HttpFilter {

	/**
	 * Generated Serial Version ID
	 */
	private static final long serialVersionUID = 3756640463339457791L;

	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		setAccessControlHeaders(resp);
		chain.doFilter(req, resp);
	}

	private void setAccessControlHeaders(HttpServletResponse resp) {
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE");
		resp.setHeader("Access-Control-Allow-Headers", "Content-type, Authorization");
		resp.setHeader("Access-Control-Expose-Headers", "Authorization");
	}
}
