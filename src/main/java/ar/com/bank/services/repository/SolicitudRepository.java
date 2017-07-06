package ar.com.bank.services.repository;

import org.springframework.data.repository.CrudRepository;

import ar.com.bank.services.model.Solicitud;

public interface SolicitudRepository extends CrudRepository<Solicitud, Integer> {

}
