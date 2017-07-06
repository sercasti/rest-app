package ar.com.bank.services.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.bank.services.application.SolicitudServiceImpl;
import ar.com.bank.services.model.Solicitud;

/**
 * Sample controller for the REST endpoints
 * 
 * @author sergio.castineyras
 */
@RestController
public class SolicitudController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SolicitudServiceImpl solicitudService;

    @Autowired
    private Environment env;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        // slf4j logging example
        String valor = Arrays.toString(env.getActiveProfiles());
        logger.info("up and running on : {}", valor);
        return "up and running on : " + Arrays.toString(env.getActiveProfiles());
    }

    // HATEOAS sample
    @RequestMapping(value = "/solicitudes", method = RequestMethod.GET)
    public List<Resource<Solicitud>> solicitudes() {
        Iterable<Solicitud> solicitudes = solicitudService.getSolicitudes();
        List<Resource<Solicitud>> resources = new ArrayList<>();
        for (Solicitud solicitud : solicitudes) {
            Resource<Solicitud> resource = new Resource<>(solicitud);
            resource.add(linkTo(methodOn(SolicitudController.class).solicitud(solicitud.getId())).withSelfRel());
            resources.add(resource);
        }
        return resources;
    }

    @RequestMapping(value = "/solicitud/{id}", method = RequestMethod.GET)
    public Solicitud solicitud(@PathVariable Integer id) {
        return solicitudService.getSolicitud(id);
    }

}