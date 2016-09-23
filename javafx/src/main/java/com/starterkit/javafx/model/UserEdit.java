package com.starterkit.javafx.model;

import com.capgemini.chess.service.to.UserProfileTO;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserEdit {

	private final StringProperty login = new SimpleStringProperty();
	private final StringProperty name = new SimpleStringProperty();
	private final StringProperty surname = new SimpleStringProperty();
	private final StringProperty email = new SimpleStringProperty();
	private final StringProperty password = new SimpleStringProperty();
	private final StringProperty aboutMe = new SimpleStringProperty();
	private final StringProperty lifeMotto = new SimpleStringProperty();
	private final ObjectProperty<UserProfileTO> editResult = new SimpleObjectProperty<UserProfileTO>();

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

	public String getPassword() {
		return password.get();
	}

	public void setPassword(String value) {
		password.set(value);
	}

	public StringProperty passwordProperty() {
		return password;
	}

	public String getAboutMe() {
		return aboutMe.get();
	}

	public void setAboutMe(String value) {
		aboutMe.set(value);
	}

	public StringProperty aboutMeProperty() {
		return aboutMe;
	}

	public String getLifeMotto() {
		return lifeMotto.get();
	}

	public void setLifeMotto(String value) {
		lifeMotto.set(value);
	}

	public StringProperty lifeMottoProperty() {
		return lifeMotto;
	}

	public void setEditResult(UserProfileTO value) {
		editResult.set(value);
	}

	public UserProfileTO getEditResult() {
		return editResult.get();
	}

	public ObjectProperty<UserProfileTO> editResultProperty() {
		return editResult;
	}
}
