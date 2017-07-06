package ar.com.bank.services.datasource.backend;

/**
 * Public interface to connect model with CRM services of the bank.
 * 
 * @author lewis.florez
 */
public interface CRMService {

    /**
     * Validate if DNI client exist in CRM like a client.
     * 
     * @param dni DNI of client.
     * @return true if exist otherwise false.
     */
    boolean verifyClientByDNI(String dni);
}
