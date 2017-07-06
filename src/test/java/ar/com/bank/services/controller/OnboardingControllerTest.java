package ar.com.bank.services.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Bean test para validar el registro de un cliente.
 * 
 * @author lewis.florez
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//test cases should ALWAYS hit the in memory database, never a real DB on any ENV
@ActiveProfiles("local")
public class OnboardingControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Test
	public void validateExistDniCliente() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/verifyClientByDNI/{dni}", "2349")
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().json("{\"2349\":true}"));
	}
	
	@Test
	public void validateNotExistDniCliente() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/verifyClientByDNI/{dni}", "1234")
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().json("{\"1234\":false}"));
	}
	
}
