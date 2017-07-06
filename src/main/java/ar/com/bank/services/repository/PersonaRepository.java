package ar.com.bank.services.repository;

import org.springframework.data.repository.CrudRepository;

import ar.com.bank.services.model.Persona;

public interface PersonaRepository extends CrudRepository<Persona, Long> {

}
