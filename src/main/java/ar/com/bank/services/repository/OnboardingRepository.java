package ar.com.bank.services.repository;

import org.springframework.data.repository.CrudRepository;

import ar.com.bank.services.model.Onboarding;

public interface OnboardingRepository extends CrudRepository<Onboarding, Long> {

}
