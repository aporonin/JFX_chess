package com.starterkit.javafx.dataprovider.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.capgemini.chess.service.to.UserProfileTO;
import com.starterkit.javafx.dataprovider.DataProvider;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class DataProviderImpl implements DataProvider {

	private static final String AUTHENTICATION_USERNAME = "franek";
	private static final String AUTHENTICATION_PASSWORD = "pass";
	// REV: adres serwera z konfiguracji
	private static final String SEARCH_PATH = "http://localhost:8090/user/search";
	private static final String ROOT_PATH = "http://localhost:8090/user";
	private static final Logger LOG = Logger.getLogger(DataProviderImpl.class);
	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public Collection<UserProfileTO> findUsers(String login, String name, String surname)
			throws JsonParseException, JsonMappingException, IOException {
		LOG.debug("Entering findUsers()");
		Collection<UserProfileTO> listUsers = new ArrayList<UserProfileTO>();
		// REV: ten obiekt powinien byc utworzony tylko raz i zapisany jako atrybut klasy 
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter(AUTHENTICATION_USERNAME, AUTHENTICATION_PASSWORD));
		MultivaluedHashMap<String, String> params = new MultivaluedHashMap<>();
		params.add("login", login);
		params.add("name", name);
		params.add("surname", surname);
		WebResource webResource = client.resource(SEARCH_PATH).queryParams(params);
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
		String output = response.getEntity(String.class);
		// REV: nie ma potrzeby robić mapowania samemu - response.getEntity(new GenericType<Collection<UserProfileTO>>)
		listUsers = Arrays.asList(objectMapper.readValue(output, UserProfileTO[].class));
		return listUsers;
	}

	@Override
	public void deleteUser(Long id) throws JsonParseException, JsonMappingException, IOException {
		LOG.debug("Entering deleteUser()");
		// REV: j.w.
		Client client = Client.create();
		WebResource webResource = client.resource(ROOT_PATH + id);
		webResource.delete(ClientResponse.class);
	}

	@Override
	public UserProfileTO updateUser(UserProfileTO selectedUser)
			throws JsonParseException, JsonMappingException, IOException {
		LOG.debug("Entering updateUser()");
		// REV: j.w.
		Client client = Client.create();
		WebResource webResource = client.resource(ROOT_PATH);
		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, selectedUser);
		UserProfileTO user = response.getEntity(UserProfileTO.class);
		return user;
	}
}
