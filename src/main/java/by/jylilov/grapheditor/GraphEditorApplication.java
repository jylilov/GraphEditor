package by.jylilov.grapheditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GraphEditorApplication extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/GraphEditorApplication.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("Graph Editor");
		primaryStage.show();
	}

}
