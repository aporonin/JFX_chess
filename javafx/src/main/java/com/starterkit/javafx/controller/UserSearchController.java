package com.starterkit.javafx.controller;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.capgemini.chess.service.to.UserProfileTO;
import com.starterkit.javafx.dataprovider.DataProvider;
import com.starterkit.javafx.model.UserSearch;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UserSearchController {

	private static final Logger LOG = Logger.getLogger(UserSearchController.class);

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField loginField;

	@FXML
	private TextField nameField;

	@FXML
	private TextField surnameField;

	@FXML
	private TableView<UserProfileTO> resultTable;

	@FXML
	private TableColumn<UserProfileTO, String> loginColumn;

	@FXML
	private TableColumn<UserProfileTO, String> nameColumn;

	@FXML
	private TableColumn<UserProfileTO, String> surnameColumn;

	@FXML
	private TableColumn<UserProfileTO, String> emailColumn;

	@FXML
	private Button searchButton;

	@FXML
	private Button deleteButton;

	@FXML
	private Button editButton;

	private final DataProvider dataProvider = DataProvider.INSTANCE;

	private final UserSearch model = new UserSearch();

	public UserSearchController() {
		LOG.debug("Constructor: loginField = " + loginField);
		LOG.debug("Constructor: nameField = " + nameField);
		LOG.debug("Constructor: surnameField = " + surnameField);
	}

	@FXML
	private void initialize() {
		LOG.debug("initialize():loginField = " + loginField);
		LOG.debug("initialize(): nameField = " + nameField);
		LOG.debug("initialize(): surnameField = " + surnameField);

		initializeResultTable();

		loginField.textProperty().bindBidirectional(model.loginProperty());
		nameField.textProperty().bindBidirectional(model.nameProperty());
		surnameField.textProperty().bindBidirectional(model.surnameProperty());
		resultTable.itemsProperty().bind(model.resultProperty());

		searchButton.disableProperty().bind(loginField.textProperty().isEmpty().and(nameField.textProperty().isEmpty())
				.and(surnameField.textProperty().isEmpty()));
	}

	private void initializeResultTable() {

		loginColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLogin()));
		nameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
		surnameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getSurname()));
		emailColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEmail()));

		resultTable.setPlaceholder(new Label(resources.getString("table.emptyText")));
	}

	@FXML
	private void searchButtonAction(ActionEvent event) {

		LOG.debug("'Search' button clicked");

		Task<Collection<UserProfileTO>> backgroundTask = new Task<Collection<UserProfileTO>>() {

			@Override
			protected Collection<UserProfileTO> call() throws Exception {
				LOG.debug("call() called");
				Collection<UserProfileTO> result = dataProvider.findUsers(model.getLogin(), model.getName(),
						model.getSurname());
				return result;
			}

			@Override
			protected void succeeded() {
				LOG.debug("succeeded() called");
				Collection<UserProfileTO> result = getValue();
				model.setResult(new ArrayList<UserProfileTO>(result));
				resultTable.getSortOrder().clear();
			}
		};
		// REV: brak obslugi bledow
		new Thread(backgroundTask).start();
	}

	@FXML
	private void deleteButtonAction(ActionEvent event) throws JsonParseException, JsonMappingException, IOException {

		LOG.debug("'Delete Profile' button clicked");

		Task<Collection<UserProfileTO>> backgroundTask = new Task<Collection<UserProfileTO>>() {

			@Override
			protected Collection<UserProfileTO> call() throws Exception {
				LOG.debug("call() called");
				dataProvider.deleteUser(resultTable.getSelectionModel().getSelectedItem().getId());
				return model.getResult();
			}

			@Override
			protected void succeeded() {
				LOG.debug("succeeded() called");
				model.getResult().remove(resultTable.getSelectionModel().getSelectedItem());
				resultTable.getSortOrder().clear();
			}
		};
		// REV: brak obslugi bledow

		new Thread(backgroundTask).start();
	}

	@FXML
	private Stage editButtonAction(ActionEvent event) {
		LOG.debug("'Edit Profile' button clicked");

		Stage primaryStage = new Stage(StageStyle.DECORATED);
		try {
			primaryStage.setTitle("User Profile");

			// REV: moznaby utworzyc okno w konstruktorze
			FXMLLoader root = new FXMLLoader(getClass().getResource("/com/starterkit/javafx/view/user-edit.fxml"),
					ResourceBundle.getBundle("com/starterkit/javafx/bundle/base"));

			Parent loader = root.load();

			UserEditController user = root.<UserEditController> getController();
			user.getData(resultTable.getSelectionModel().getSelectedItem());
			Scene scene = new Scene(loader);

			primaryStage.setScene(scene);

			// REV: okno powinno byc modalne
			primaryStage.show();

		} catch (Exception e) {
			// REV: o co tu chodzi?
			fail("No selected row");
		}
		return primaryStage;
	}
}
