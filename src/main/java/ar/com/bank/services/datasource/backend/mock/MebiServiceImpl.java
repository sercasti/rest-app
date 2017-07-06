package ar.com.bank.services.datasource.backend.mock;

import java.util.Random;

import ar.com.bank.services.datasource.backend.MebiService;

/**
 * Mock implementation to use until we have connection to the real service
 * 
 * @author sergio.castineyras
 */
public class MebiServiceImpl implements MebiService {

    private static final Random rand = new Random();

    @Override
    public Integer getScoring(String dni) {
        return rand.nextInt(10);
    }

}
