package ar.com.bank.services.datasource.backend;

import java.io.File;

/**
 * Public interface to model the sharepoint services at the bank
 * 
 * @author sergio.castineyras
 */
public interface SharepointService {

    public Integer uploadFiles(Integer solicitudId, File... files);
}
