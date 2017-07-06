package ar.com.bank.services.auth.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * This class is meant to implement a component that will intercept all HTTP
 * Rest calls to the bank backend and inject the security token on the header,
 * and inject the routing server URL, so all this complexity is far away from
 * our application code
 * 
 * @author sergio.castineyras
 */
@Component
@Profile({"bank", "dev-vpn"}) // Only use on Profile that is connected to bank VPN to connect to ESB
public class AuthTemplateCustomizer implements RestTemplateCustomizer {

	@Autowired
	private AuthInterceptor authInterceptor;

	@Autowired
	private RoutingServiceInterceptor routingInterceptor;

	@Override
	public void customize(RestTemplate restTemplate) {
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(authInterceptor);
		interceptors.add(routingInterceptor);
		restTemplate.setInterceptors(interceptors);
	}

}
