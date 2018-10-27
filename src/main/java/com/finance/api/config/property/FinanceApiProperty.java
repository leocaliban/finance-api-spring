package com.finance.api.config.property;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("finance")
public class FinanceApiProperty {

	private List<String> originsPermitidas = Arrays.asList("http://localhost:4200", "http://localhost:8100",
			"https://finance-angular.herokuapp.com");

	private final Seguranca seguranca = new Seguranca();

	public static class Seguranca {

		private boolean enableHttps;

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}
	}


	public List<String> getOriginsPermitidas() {
		return originsPermitidas;
	}


	public void setOriginsPermitidas(List<String> originsPermitidas) {
		this.originsPermitidas = originsPermitidas;
	}


	public Seguranca getSeguranca() {
		return seguranca;
	}

}
