package fickle.ui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Represents a dialog box consisting of an ImageView for avatar and a label containing speaker's message.
 */
public class DialogBox extends HBox {
    @FXML
    private VBox messageBox;

    @FXML
    private Label mainLabel;

    @FXML
    private Label specialLabel;

    @FXML
    private ImageView displayPicture;

    /**
     * Constructor for DialogBox.
     *
     * @param mainText The main text content.
     * @param specialText The special text content (Optional: would be empty string for UserDialog).
     * @param messageType The type of message for CSS styling.
     * @param img The current speaker's avatar image.
     */
    private DialogBox(String mainText, String specialText, String messageType, Image img) {
        loadFxml();
        setDialogText(mainText, specialText);
        applyCssStyles(messageType);
        setDisplayImage(img);
    }

    // Loads the FXML layout for the DialogBox and set current class as the controller.
    private void loadFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Sets the main and special text for the dialog box.
    // Hides the special label if the special text is empty.
    private void setDialogText(String mainText, String specialText) {
        mainLabel.setText(mainText);

        // Displays a special text if it is present
        if (!specialText.isEmpty()) {
            specialLabel.setText(specialText);
        } else {
            // Reused from https://stackoverflow.com/a/49053199 (fabian)
            // Removes from layout and hides the label visually.
            specialLabel.setManaged(false);
            specialLabel.setVisible(false);
        }
    }

    // Applies CSS styling to the dialog box and its labels.
    private void applyCssStyles(String messageType) {
        // Applies CSS styling to labels
        mainLabel.getStyleClass().add("main-label");
        specialLabel.getStyleClass().add("special-label");

        // Applies CSS styling to the dialog box.
        messageBox.getStyleClass().add("message-box");

        // Applies CSS styling to different type of Fickle messages.
        if (messageType != null) {
            messageBox.getStyleClass().add(messageType);
        }
    }

    // Sets the profile image of the dialog box.
    private void setDisplayImage(Image img) {
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);

        // Style Fickle's messageBox to face the right direction
        messageBox.getStyleClass().add("reply-label");
    }

    /**
    * Creates a dialog box for the user with the given input text and user image.
    *
    * @param text The input text to display in the dialog.
    * @param img The user's image.
    * @return A DialogBox representing the user's message.
    */
    public static DialogBox getUserDialog(String text, Image img) {
        // empty special text for UserDialog
        return new DialogBox(text, "", null, img);
    }

    /**
    * Creates a dialog box for Fickle chatbot with the given output messages and Fickle's image.
    *
    * @param texts A string array represented by [mainMessage, specialMessage].
    * @param img The Fickle's image.
    * @return A flipped DialogBox representing Fickle's message.
    */
    public static DialogBox getFickleDialog(String[] texts, Image img) {
        String mainText = texts[0];
        String specialText = texts[1];
        String messageType = texts[2];
        var db = new DialogBox(mainText, specialText, messageType, img);

        db.flip();
        return db;
    }
}
