package fickle;

import java.io.IOException;

import fickle.ui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Fickle using FXML.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // Load the FXML layout for the main window
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();

            // Create a scene with the loaded layout
            Scene scene = new Scene(ap);
            stage.setScene(scene);

            // Format the window with minimum size constraints and title
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            stage.setTitle("Fickle");

            Fickle fickle = new Fickle("data/tasks.txt");

            // Inject the Fickle instance
            fxmlLoader.<MainWindow>getController().setFickle(fickle);

            // Show the application window
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
