package com.finance.api.token;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Classe {@link RefreshTokenCookiePreProcessorFilter} responsável por inserir o
 * refresh token do cookie na requisição.
 * 
 * @author Leocaliban
 *
 * 26 de out de 2018
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // Indica prioridade alta, para a requisição ser avaliada antes das demais
public class RefreshTokenCookiePreProcessorFilter implements Filter {
	
	/**
	 * Recupera o refresh token do cookie
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest requisicao = (HttpServletRequest) request;

		if ("/oauth/token".equalsIgnoreCase(requisicao.getRequestURI())
				&& "refresh_token".equals(requisicao.getParameter("grant_type")) && requisicao.getCookies() != null) {
			for (Cookie cookie : requisicao.getCookies()) {
				if (cookie.getName().equals("refreshTokenCookie")) {
					String refreshToken = cookie.getValue();
					requisicao = new MyServletRequestWrapper(requisicao, refreshToken);
				}
			}
		}
		chain.doFilter(requisicao, response);
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	static class MyServletRequestWrapper extends HttpServletRequestWrapper {
		private String refreshToken;

		public MyServletRequestWrapper(HttpServletRequest request, String refreshToken) {
			super(request);
			this.refreshToken = refreshToken;
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
			map.put("refresh_token", new String[] { refreshToken });
			map.setLocked(true);
			return map;
		}
	}
}
