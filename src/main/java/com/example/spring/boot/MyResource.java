package com.example.spring.boot;

import java.util.Arrays;
import java.util.List;

import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


@Path("/v1/main")
public class MyResource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String REMOTE_URL = "http://localhost:8091/remoteApp/person";
	
	
	/**
	 * ---------------------------------------------------
	 * URL: http://localhost:8080/v1/main/getAllPerson
	 * ---------------------------------------------------
	 */
	@GET
	@Path("/getAllPerson")
	public void getAllPerson() {
		ResponseEntity<Person[]> response = restTemplate.getForEntity(REMOTE_URL, Person[].class);
		List<Person> personList = Arrays.asList(response.getBody());
		personList.forEach(person -> System.out.println(person));
	}
	
	
	/**
	 * ---------------------------------------------------
	 * URL: http://localhost:8080/v1/main/getPersonById/2
	 * ---------------------------------------------------
	 */
	@GET
	@Path("/getPersonById/{id}")
	public void getPersonById(@PathParam("id") Long id) {
		ResponseEntity<Person> response = restTemplate.getForEntity(REMOTE_URL+"/"+id, Person.class);
		Person person = response.getBody();
		System.out.println(person);
	}
	
	
	/**
	 * ---------------------------------------------------
	 * URL: http://localhost:8080/v1/main/addPerson
	 * Send this JSON in request body {"id":"5", "age":"30", "firstName":"FN", "lastName":"LN"}
	 * ---------------------------------------------------
	 */
	@POST
	@Path("/addPerson")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addPerson(Person person) {
		ResponseEntity<String> response = restTemplate.postForEntity(REMOTE_URL, person, String.class);
		HttpStatus status = response.getStatusCode();
		System.out.println(status);
	}
	
	
	/**
	 * ---------------------------------------------------
	 * URL: http://localhost:8080/v1/main/updatePerson
	 * Send this JSON in request body {"id":"5", "age":"44", "firstName":"FN-NEW", "lastName":"LN-NEW"}
	 * ---------------------------------------------------
	 */
	@PUT
	@Path("/updatePerson")
	public void updatePerson(Person person) {
		restTemplate.put(REMOTE_URL, person);
	}
	
	
	/**
	 * ---------------------------------------------------
	 * URL: http://localhost:8080/v1/main/deletePerson/5
	 * ---------------------------------------------------
	 */
	@DELETE
	@Path("/deletePerson/{id}")
	public void deletePerson(@PathParam("id") Long id) {
		restTemplate.delete(REMOTE_URL + id);
	}
}
