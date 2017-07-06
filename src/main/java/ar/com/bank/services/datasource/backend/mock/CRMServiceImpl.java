package ar.com.bank.services.datasource.backend.mock;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import ar.com.bank.services.datasource.backend.CRMService;

/**
 * Service mocks to get information about client from CRM.
 * 
 * @author lewis.florez
 */
@Service
@Profile({ "local", "dev" })
public class CRMServiceImpl implements CRMService {

	@Override
	public boolean verifyClientByDNI(String dni) {
		StringUtils.trim(dni);
		return StringUtils.endsWith(dni, "9");
	}
}
