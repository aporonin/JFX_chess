package com.starterkit.javafx.dataprovider;

import java.io.IOException;
import java.util.Collection;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.capgemini.chess.service.to.UserProfileTO;
import com.starterkit.javafx.dataprovider.impl.DataProviderImpl;

public interface DataProvider {

	DataProvider INSTANCE = new DataProviderImpl();

	Collection<UserProfileTO> findUsers(String login, String name, String surname)
			throws JsonParseException, JsonMappingException, IOException;

	void deleteUser(Long id) throws JsonParseException, JsonMappingException, IOException;

	UserProfileTO updateUser(UserProfileTO selectedUser) throws JsonParseException, JsonMappingException, IOException;
}
