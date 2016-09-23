package com.starterkit.javafx;

import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Startup extends Application {

	private static final String STAGE_TITLE = "Search User Profiles";
	private static final String FXML_PATH = "/com/starterkit/javafx/view/user-search.fxml";
	private static final String BUNDLE_PATH = "com/starterkit/javafx/bundle/base";
	private static final String STYLE_SHEET_PATH = "/com/starterkit/javafx/css/standard.css";

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle(STAGE_TITLE);

		Parent root = FXMLLoader.load(getClass().getResource(FXML_PATH), ResourceBundle.getBundle(BUNDLE_PATH));

		Scene scene = new Scene(root);

		scene.getStylesheets().add(getClass().getResource(STYLE_SHEET_PATH).toExternalForm());

		primaryStage.setScene(scene);

		primaryStage.show();
	}
}
