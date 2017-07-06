package ar.com.bank.services.controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ar.com.bank.services.application.FileService;
import ar.com.bank.services.application.OnboardingServiceImpl;
import ar.com.bank.services.datasource.backend.CRMService;
import ar.com.bank.services.model.Onboarding;
import ar.com.bank.services.model.Persona;
import ar.com.bank.services.utils.ApiMessage;
import ar.com.bank.services.utils.BuilderApiMessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Bean to allow to register a client.
 * 
 * @author lewis.florez
 */
@Api("Endpoint for onboarding operations")
@RestController
//TODO write missing unit tests
public class OnboardingController {

	@Autowired
	private CRMService crmService;

	@Autowired
	private OnboardingServiceImpl onboardingService;

	@Autowired
	private BuilderApiMessageUtil apiMessageUtil;
	
	@Autowired
	private FileService fileService;

	/**
	 * End point to allows validate if DNI client exist.
	 * 
	 * @return false if not exist DNI like client and true if exist.
	 */
	@ApiOperation(value = "Returns if the DNI already exists", response = String.class)
	@RequestMapping(value = "/verifyClientByDNI/{dni}", method = RequestMethod.GET, produces = "application/json")
	public String verifyClientByDNI(
			@ApiParam(name = "dni", value = "DNI number", required = true) @PathVariable(name = "dni", required = true) String dni) {
		return "{\"" + dni + "\":" + crmService.verifyClientByDNI(dni) + "}";
	}

	/**
	 * End point allows to save a onboarding of client.
	 * 
	 * @param persona
	 *            object to be persisted.
	 * @return Json with all information about save transaction persona of
	 *         client.
	 * @See {@link ResponseEntity}
	 * @see Persona
	 */
	@RequestMapping(value = "/registerClient", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ApiMessage> registerClient(@RequestBody Persona persona) {

		ApiMessage apiMessage = null;
		ResponseEntity<ApiMessage> responseEntity = null;

		if (StringUtils.isBlank(persona.getDni())) {
			apiMessage = apiMessageUtil.buildMessage(HttpStatus.BAD_REQUEST,
					"api.bank.code.onboarding.persona.dni.null", "api.bank.msg.onboarding.persona.dni.null", true);
			responseEntity = new ResponseEntity<>(apiMessage, apiMessage.getStatus());
			return responseEntity;
		}

		if (StringUtils.isBlank(persona.getFirstName())) {
			apiMessage = apiMessageUtil.buildMessage(HttpStatus.BAD_REQUEST,
					"api.bank.code.onboarding.persona.firstname.null",
					"api.bank.msg.onboarding.persona.firstname.null", true);
			responseEntity = new ResponseEntity<>(apiMessage, apiMessage.getStatus());
			return responseEntity;
		}

		if (StringUtils.isBlank(persona.getLastName())) {
			apiMessage = apiMessageUtil.buildMessage(HttpStatus.BAD_REQUEST,
					"api.bank.code.onboarding.persona.lastname.null", "api.bank.msg.onboarding.persona.lastname.null",
					true);
			responseEntity = new ResponseEntity<>(apiMessage, apiMessage.getStatus());
			return responseEntity;
		}

		if (persona.getDateOfBirth() == null) {
			apiMessage = apiMessageUtil.buildMessage(HttpStatus.BAD_REQUEST,
					"api.bank.code.onboarding.persona.dateofbirth.null",
					"api.bank.msg.onboarding.persona.dateofbirth.null", true);
			responseEntity = new ResponseEntity<>(apiMessage, apiMessage.getStatus());
			return responseEntity;
		}

		if (StringUtils.isBlank(persona.getSexo())) {
			apiMessage = apiMessageUtil.buildMessage(HttpStatus.BAD_REQUEST,
					"api.bank.code.onboarding.persona.sexo.null", "api.bank.msg.onboarding.persona.sexo.null", true);
			responseEntity = new ResponseEntity<>(apiMessage, apiMessage.getStatus());
			return responseEntity;
		}

		if (persona.getExpirationDateDni() == null) {
			apiMessage = apiMessageUtil.buildMessage(HttpStatus.BAD_REQUEST,
					"api.bank.code.onboarding.persona.expirationdatedni.null",
					"api.bank.msg.onboarding.persona.expirationdatedni.null", true);
			responseEntity = new ResponseEntity<>(apiMessage, apiMessage.getStatus());
			return responseEntity;
		}

		Onboarding onboarding = new Onboarding(null, persona);
		Long onboardingId = onboardingService.saveOnboarding(onboarding);

		if (onboardingId == null) {
			apiMessage = apiMessageUtil.buildMessage(HttpStatus.BAD_REQUEST,
					"api.bank.code.onboarding.onboarding.register.exist",
					"api.bank.msg.onboarding.onboarding.register.exist", true);
			responseEntity = new ResponseEntity<>(apiMessage, apiMessage.getStatus());
		} else {
			String msg = apiMessageUtil.getMessage("api.bank.msg.onboarding.onboarding.register.ok");
			apiMessage = new ApiMessage(HttpStatus.CREATED, onboardingId.toString(), msg);
			responseEntity = new ResponseEntity<>(apiMessage, apiMessage.getStatus());
		}

		return responseEntity;
	}

	/**
	 * End point allows to save DNI for an onboardingId
	 * 
	 * @param onboardingId
	 * @Param DNI
	 */
	@RequestMapping(value = "/uploadDNIPics", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json")
	public ResponseEntity<ApiMessage> uploadDNI(@RequestParam("files") MultipartFile[] files,
			@RequestParam("onboardingId") Long onboardingId) {
		onboardingService.saveDNI(onboardingId, files);
		ApiMessage apiMessage = new ApiMessage(HttpStatus.OK, onboardingId.toString(), "DNI Saved");
		return new ResponseEntity<>(apiMessage, apiMessage.getStatus());
	}

	/**
	 * End point allows to save selfie for an onboardingId
	 * 
	 * @param onboardingId
	 * @Param selfie
	 * @return the confidence value for the face verification (selfie vs dni
	 *         photo)
	 */
	@RequestMapping(value = "/uploadSelfie", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ApiMessage> uploadSelfie(@RequestParam("file") MultipartFile file,
			@RequestParam("onboardingId") Long onboardingId) {
		onboardingService.saveSelfie(onboardingId, file);
		ApiMessage apiMessage = new ApiMessage(HttpStatus.OK, onboardingId.toString(),
				"The confidence on face verification is 0.734233");
		return new ResponseEntity<>(apiMessage, apiMessage.getStatus());
	}
	
	
	/**
	 * get legajo by onboardingId
	 * 
	 * @param onboardingId
	 * @return the onboarding object
	 */
	@RequestMapping(value = "/legajo/{onboardingId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Onboarding getOnboarding(
			@PathVariable(name = "onboardingId", required = true) Long onboardingId) {
		return onboardingService.getOnboarding(onboardingId);
	}
	
	/**
	 * get file by path
	 * 
	 * @param path
	 * @return the document
	 * @throws IOException
	 * @throws MalformedURLException
	 * TODO move to FileController for reuse
	 */
	@RequestMapping(value = "/getFile/{folder}/{path}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<byte[]> getDocument(@PathVariable(name = "folder", required = true) String folder,
			@PathVariable(name = "path", required = true) String path)
			throws MalformedURLException, IOException {

		File file = fileService.loadFile(folder + "/" + path);
		String type = file.toURL().openConnection().guessContentTypeFromName(path);

		byte[] out = new byte[(int) file.length()];
		DataInputStream dis = new DataInputStream(new FileInputStream(file));
		dis.readFully(out);
		dis.close();

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("content-disposition", "inline;filename=" + path);
		responseHeaders.add("Content-Type", type);

		return new ResponseEntity<byte[]>(out, responseHeaders, HttpStatus.OK);
	}

}
