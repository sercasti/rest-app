package ar.com.bank.services.auth.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.stereotype.Component;

/**
 * Injects the routing service URL to all outgoing HTTP rest calls to the bank
 * backend, except the login request, which needs to go to the default URL
 * 
 * @author sergio.castineyras
 */
@Component
@Profile({"bank", "dev-vpn"})
public class RoutingServiceInterceptor implements ClientHttpRequestInterceptor {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${routing.service.url}")
	private String routingURL;

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		// Only append the routing server URL if this is not a login request
		if (!AuthInterceptor.isLoginRequest(request)) {
			request = new RoutingHttpRequestWrapper(request);
		}

		return execution.execute(request, body);
	}

	private class RoutingHttpRequestWrapper extends HttpRequestWrapper {

		public RoutingHttpRequestWrapper(HttpRequest request) {
			super(request);
		}

		@Override
		public URI getURI() {
			String originalRequest = super.getURI().toString();
			StringBuilder sb = new StringBuilder();
			sb.append(routingURL);
			if(!routingURL.endsWith("/"))
				sb.append("/");
			sb.append(originalRequest);
			String routedRequest = sb.toString();
			try {
				return new URI(routedRequest);
			} catch (URISyntaxException e) {
				logger.error("error building URI {}", routedRequest);
				return null;
			}
		}

	}
}
