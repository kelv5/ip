package fickle.ui;

import fickle.Fickle;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Fickle fickle;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image fickleImage = new Image(this.getClass().getResourceAsStream("/images/DaFickle.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        // Display ASCII logo and greetings at start
        dialogContainer.getChildren().add(DialogBox.getFickleDialog(Ui.printLogoAndGreet(), fickleImage));
    }

    /** Injects the Fickle instance */
    public void setFickle(Fickle fickle) {
        this.fickle = fickle;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing
     * Fickle's reply and then appends them to the dialog container.
     * Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        String[] responses = fickle.getResponse(input);

        dialogContainer.getChildren().addAll(DialogBox.getUserDialog(input, userImage),
                                        DialogBox.getFickleDialog(responses, fickleImage));

        userInput.clear();

        if (input.equalsIgnoreCase("bye")) {
            // Disable user input and send button
            userInput.setEditable(false);
            sendButton.setDisable(true);

            // Reused from https://stackoverflow.com/a/13602324 (a.b)
            // Minor modification: Retrieved the stage from dialogContainer instead of a button
            Stage stage = (Stage) dialogContainer.getScene().getWindow();

            // Reused from https://stackoverflow.com/a/27334614 (James_D)
            // Creates a delay of 3.3 seconds before closing the window automatically
            PauseTransition delay = new PauseTransition(Duration.seconds(3.3));
            delay.setOnFinished(event -> stage.close());
            delay.play();
        }
    }
}
