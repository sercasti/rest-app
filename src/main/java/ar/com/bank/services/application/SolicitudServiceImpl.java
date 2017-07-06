package ar.com.bank.services.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import ar.com.bank.services.model.Solicitud;
import ar.com.bank.services.repository.SolicitudRepository;

@Service
public class SolicitudServiceImpl {

    @Autowired
    private SolicitudRepository repository;

    public Solicitud getSolicitud(Integer id) {
        return repository.findOne(id);
    }

    @Cacheable("solicitudesCache")
    public Iterable<Solicitud> getSolicitudes() {
        return repository.findAll();
    }
}
