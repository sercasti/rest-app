package ar.com.bank.services.application;

/**
 * Service mocks to persist, validate, search Onboarding client.
 * 
 * @author lewis.florez
 *
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ar.com.bank.services.datasource.backend.CRMService;
import ar.com.bank.services.model.Onboarding;
import ar.com.bank.services.repository.OnboardingRepository;
import ar.com.bank.services.repository.PersonaRepository;

@Service
//TODO write unit test
public class OnboardingServiceImpl {

	@Autowired
	private OnboardingRepository onbordingRepository;

	@Autowired
	private PersonaRepository personaRepository;

	@Autowired
	private CRMService crmService;

	@Autowired
	private FileService fileService;

	public Long saveOnboarding(Onboarding onboarding) {
		if (crmService.verifyClientByDNI(onboarding.getPersona().getDni())) {
			return null;
		}
		personaRepository.save(onboarding.getPersona());
		onbordingRepository.save(onboarding);
		return onboarding.getId();
	}

	public Long saveSelfie(Long onboardingId, MultipartFile selfie) {
		Onboarding onboarding = onbordingRepository.findOne(onboardingId);
		String pathSelfie = fileService.saveFile(selfie, onboarding.getPersona().getDni());
		onboarding.getPersona().setPathPicture(pathSelfie);
		personaRepository.save(onboarding.getPersona());
		onbordingRepository.save(onboarding);
		return onboarding.getId();
	}

	public Long saveDNI(Long onboardingId, MultipartFile[] files) {
		Onboarding onboarding = onbordingRepository.findOne(onboardingId);
		for (MultipartFile file : files) {
			String path = fileService.saveFile(file, onboarding.getPersona().getDni());
			onboarding.getPersona().getPathsDNI().add(path);
		}
		personaRepository.save(onboarding.getPersona());
		onbordingRepository.save(onboarding);
		return onboarding.getId();
	}
	
	public Onboarding getOnboarding(Long onboardingId) {
		return onbordingRepository.findOne(onboardingId);
	}
}
