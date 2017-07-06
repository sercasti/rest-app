package ar.com.bank.services.objectset;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import ar.com.bank.services.enums.Environments;
import ar.com.bank.services.model.Solicitud;
import ar.com.bank.services.repository.SolicitudRepository;

/**
 * Create sample data on the database after application startup if this is running on a local or dev environment and the
 * table is empty
 * 
 * @author sergio.castineyras
 */
@Component
public class DatabaseObjectSet implements CommandLineRunner {

    @Autowired
    private Environment env;

    @Autowired
    private SolicitudRepository repository;

    private static final String[] NAMES = { "Alex", "Robert", "Miguel", "Diego" };
    private static final String[] SURNAMES = { "Rodriguez", "Fernandez", "Garcia", "Benitez" };
    private static final Random rand = new Random();

    @Override
    public void run(String... args) throws Exception {
        List<String> active = Arrays.asList(env.getActiveProfiles());
        if (active.contains(Environments.LOCAL.getName())
                || active.contains(Environments.DEV.getName()) && repository.count() == 0) {
            createDummySolicitudes();
        }
    }

    private void createDummySolicitudes() {
        repository.save(Arrays.asList(createDummySolicitud(), createDummySolicitud(), createDummySolicitud()));

    }

    private Solicitud createDummySolicitud() {
        return new Solicitud(NAMES[rand.nextInt(4)], SURNAMES[rand.nextInt(4)], String.valueOf(rand.nextInt(90)),
                String.valueOf(rand.nextInt(10000000) + 20000000));
    }
}