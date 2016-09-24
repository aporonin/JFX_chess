package com.starterkit.javafx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.capgemini.chess.service.to.UserProfileTO;
import com.starterkit.javafx.dataprovider.DataProvider;
import com.starterkit.javafx.model.UserEdit;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserEditController {

	private static final Logger LOG = Logger.getLogger(UserEditController.class);

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label loginField;

	@FXML
	private TextField nameField;

	@FXML
	private TextField surnameField;

	@FXML
	private TextField emailField;

	@FXML
	private TextField passwordField;

	@FXML
	private TextField aboutField;

	@FXML
	private TextField lifeMottoField;

	@FXML
	private Button saveButton;

	@FXML
	private Button cancelButton;

	private final UserEdit model = new UserEdit();

	private final DataProvider dataProvider = DataProvider.INSTANCE;

	public UserEditController() {
		LOG.debug("Constructor: loginField = " + loginField);
		LOG.debug("Constructor: nameField = " + nameField);
		LOG.debug("Constructor: surnameField = " + surnameField);
		LOG.debug("Constructor: emailField = " + emailField);
		LOG.debug("Constructor: passwordField = " + passwordField);
		LOG.debug("Constructor: aboutField = " + aboutField);
		LOG.debug("Constructor: lifeMottoField = " + lifeMottoField);
	}

	@FXML
	private void initialize() {
		LOG.debug("initialize():loginField = " + loginField);
		LOG.debug("initialize(): nameField = " + nameField);
		LOG.debug("initialize(): surnameField = " + surnameField);
		LOG.debug("initialize(): emailField = " + emailField);
		LOG.debug("initialize(): passwordField = " + passwordField);
		LOG.debug("initialize(): aboutField = " + aboutField);
		LOG.debug("initialize(): lifeMottoField = " + lifeMottoField);

		loginField.textProperty().bindBidirectional(model.loginProperty());
		nameField.textProperty().bindBidirectional(model.nameProperty());
		surnameField.textProperty().bindBidirectional(model.surnameProperty());
		emailField.textProperty().bindBidirectional(model.emailProperty());
		passwordField.textProperty().bindBidirectional(model.passwordProperty());
		aboutField.textProperty().bindBidirectional(model.aboutMeProperty());
		lifeMottoField.textProperty().bindBidirectional(model.lifeMottoProperty());

	}

	@FXML
	private void saveButtonAction(ActionEvent event) throws JsonParseException, JsonMappingException, IOException {

		LOG.debug("'Save' button clicked");
		UserProfileTO updateUser = new UserProfileTO();
		updateUser.setId(model.getEditResult().getId());
		updateUser.setName(model.getName());
		updateUser.setSurname(model.getSurname());
		updateUser.setEmail(model.getEmail());
		updateUser.setPassword(model.getPassword());
		updateUser.setAboutMe(model.getAboutMe());
		updateUser.setLifeMotto(model.getLifeMotto());

		Task<UserProfileTO> backgroundTask = new Task<UserProfileTO>() {

			@Override
			protected UserProfileTO call() throws Exception {
				LOG.debug("call() called");
				UserProfileTO result = dataProvider.updateUser(updateUser);
				return result;
			}

			@Override
			protected void succeeded() {
				LOG.debug("succeeded() called");
				UserProfileTO result = getValue();
				model.setEditResult(result);
				Stage stage = (Stage) saveButton.getScene().getWindow();
				stage.close();
			}
		};
		new Thread(backgroundTask).start();
	}

	// REV: bardziej by pasowalo setData
	public void getData(UserProfileTO userProfile) {
		model.setLogin(userProfile.getLogin());
		model.setName(userProfile.getName());
		model.setSurname(userProfile.getSurname());
		model.setPassword(userProfile.getPassword());
		model.setAboutMe(userProfile.getAboutMe());
		model.setEmail(userProfile.getEmail());
		model.setLifeMotto(userProfile.getLifeMotto());
		model.setEditResult(userProfile);
	}

	@FXML
	public void cancelAction() {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

}
