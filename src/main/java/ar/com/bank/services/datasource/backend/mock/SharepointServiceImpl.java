package ar.com.bank.services.datasource.backend.mock;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.bank.services.datasource.backend.SharepointService;

/**
 * Mock implementation to use until we have connection to the real service
 * 
 * @author sergio.castineyras
 */
public class SharepointServiceImpl implements SharepointService {

    private static final Map<Integer, List<File>> map = new HashMap<>();

    @Override
    public Integer uploadFiles(Integer solicitudId, File... files) {
        map.put(solicitudId, Arrays.asList(files));
        return solicitudId;
    }

}
