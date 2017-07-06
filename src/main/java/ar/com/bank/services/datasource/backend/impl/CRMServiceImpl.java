package ar.com.bank.services.datasource.backend.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ar.com.bank.services.datasource.backend.CRMService;

@Service
@Profile({ "bank", "dev-vpn" })
public class CRMServiceImpl implements CRMService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${crm.applicationId}")
	private String crmApplicationId;
	@Value("${crm.versionId}")
	private String crmVersionId;
	@Value("${crm.revisionId}")
	private String crmRevisionId;
	private String crmApplicationURL;

	@PostConstruct
	public final void initIt() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(crmApplicationId);
		sb.append("/V");
		sb.append(crmVersionId);
		sb.append("/R");
		sb.append(crmRevisionId);
		crmApplicationURL = sb.toString();
	}

	@Override
	public boolean verifyClientByDNI(final String dni) {
		//AuthTemplateCustomizer will intercept this call and do the following:
		//a: make sure the request is sent with an authenticated token (@see AuthInterceptor.java)
		//b: prepend the routing server url from the config file (@see RoutingServiceInterceptor.java)
		//as requested per the bank, the URL will look like:
		//URL: Servicio de enrutamiento/Nombre de servicio de integracion/VNumero de versión/RNumero de revision
		//URL: RoutingServiceInterceptor + / + crmApplicationId + /V + crmVersionId + /R + crmRevisionId
		ResponseEntity<String> response = restTemplate.getForEntity(crmApplicationURL + "/existeDNI/{dni}", String.class, dni);
		return response.getBody().equalsIgnoreCase("true");
	}

}
