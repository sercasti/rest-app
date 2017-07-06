package ar.com.bank.services.auth.client;

import java.io.UnsupportedEncodingException;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * This class is meant to encapsulate all the complexity of getting the
 * authentication token from the security server, and updating the token when it
 * expires
 * 
 * @author sergio.castineyras
 */
@Component
@Profile({"bank", "dev-vpn"})
public class TokenAuthenticator {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private String currentToken;
	@Value("${authentication.service.username}")
	private String username;
	@Value("${authentication.service.password}")
	private String password;
	@Value("${authentication.service.url}")
	private String authURL;

	public boolean isNotAuthenticated() {
		return currentToken == null;
	}

	public void authenticate() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64(username) + ":" + base64(password));
		HttpEntity<Object> entity = new HttpEntity<Object>(headers);
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
		try {
			RestTemplate restTemplate = restTemplateBuilder.build();
			ResponseEntity<String> response = restTemplate.exchange(authURL, HttpMethod.GET, entity, String.class);
			if (HttpStatus.UNAUTHORIZED.equals(response.getStatusCode())) {
				throw new RuntimeException("Authentication with security service failed");
			} else if (HttpStatus.NO_CONTENT.equals(response.getStatusCode())) {
				HttpHeaders responseHeaders = response.getHeaders();
				currentToken = responseHeaders.get("X-Authorization-token").isEmpty() ? null
						: responseHeaders.get("X-Authorization-token").get(0);
			}
		} catch (RestClientException uhe) {
			logger.debug("");
		}

	}

	private static String base64(String string) {
		try {
			return DatatypeConverter.printBase64Binary(string.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public String getCurrentToken() {
		return this.currentToken;
	}
}