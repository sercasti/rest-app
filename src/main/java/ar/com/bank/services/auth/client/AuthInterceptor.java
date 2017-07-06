package ar.com.bank.services.auth.client;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

/**
 * Intercept every request going to the bank backend and make sure they include
 * the auth token
 * 
 * @author sergio.castineyras
 */
@Component
@Profile({"bank", "dev-vpn"})
public class AuthInterceptor implements ClientHttpRequestInterceptor {

	@Autowired
	private TokenAuthenticator tokenAuthenticator;

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		// Only validate the existence of the token if this is not a login
		// request
		if (!isLoginRequest(request)) {
			if (tokenAuthenticator.isNotAuthenticated()) {
				tokenAuthenticator.authenticate();
			}
			// if it couldn't get a token, nothing else we can do.
			if (tokenAuthenticator.isNotAuthenticated()) {
				throw new RuntimeException("failed to obtain a token from the security service");
			}
			// set the token for all outgoing requests
			request.getHeaders().set("X-Authorization-token", tokenAuthenticator.getCurrentToken());
		}
		// Intercept the response to detect if the token has expired
		ClientHttpResponse response = execution.execute(request, body);
		if (HttpStatus.UNAUTHORIZED.equals(response.getStatusCode())) {
			response = retryRequestIfTokenExpired(request, body, execution);
		}

		return response;
	}

	private ClientHttpResponse retryRequestIfTokenExpired(HttpRequest request, byte[] body,
			ClientHttpRequestExecution execution) throws IOException {
		// token has expired, renew
		tokenAuthenticator.authenticate();
		if (tokenAuthenticator.isNotAuthenticated()) {
			throw new RuntimeException("failed to get a new token from the security service");
		}
		// set the new token for all outgoing requests
		request.getHeaders().set("X-Authorization-token", tokenAuthenticator.getCurrentToken());
		// retry request with new token
		return execution.execute(request, body);
	}

	static boolean isLoginRequest(HttpRequest request) {
		List<String> loginRequestHeaders = request.getHeaders().get("Authorization");
		return loginRequestHeaders != null && !loginRequestHeaders.isEmpty() && loginRequestHeaders.get(0) != null;
	}

}
