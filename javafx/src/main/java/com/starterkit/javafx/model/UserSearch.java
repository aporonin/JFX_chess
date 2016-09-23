package com.starterkit.javafx.model;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.chess.service.to.UserProfileTO;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class UserSearch {

	private final StringProperty login = new SimpleStringProperty();
	private final StringProperty name = new SimpleStringProperty();
	private final StringProperty surname = new SimpleStringProperty();
	private final StringProperty email = new SimpleStringProperty();
	private final ListProperty<UserProfileTO> result = new SimpleListProperty<>(
			FXCollections.observableList(new ArrayList<>()));

	public String getLogin() {
		return login.get();
	}

	public void setLogin(String value) {
		login.set(value);
	}

	public StringProperty loginProperty() {
		return login;
	}

	public String getName() {
		return name.get();
	}

	public void setName(String value) {
		name.set(value);
	}

	public StringProperty nameProperty() {
		return name;
	}

	public String getSurname() {
		return surname.get();
	}

	public void setSurname(String value) {
		surname.set(value);
	}

	public StringProperty surnameProperty() {
		return surname;
	}

	public String getEmail() {
		return email.get();
	}

	public void setEmail(String value) {
		email.set(value);
	}

	public StringProperty emailProperty() {
		return email;
	}

	public List<UserProfileTO> getResult() {
		return result.get();
	}

	public void setResult(List<UserProfileTO> value) {
		result.setAll(value);
	}

	public ListProperty<UserProfileTO> resultProperty() {
		return result;
	}

}
