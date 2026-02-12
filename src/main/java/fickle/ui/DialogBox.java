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

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's
 * face and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);

        // Uses a monospaced font (Consolas) to ensure proper alignment for ASCII logo
        // Code below inspired by https://en.wikipedia.org/wiki/Monospaced_font#Use_in_art
        dialog.setFont(javafx.scene.text.Font.font("Consolas", 12));

        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the
     * right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    /**
    * Creates a dialog box for the user with the given input text and user image.
    *
    * @param text The input text to display in the dialog.
    * @param img The user's image.
    * @return A DialogBox representing the user's message.
    */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
    * Creates a dialog box for Fickle chatbot with the given output messages and Fickle's image.
    *
    * @param texts A string array represented by [mainMessage, specialMessage].
    * @param img The Fickle's image.
    * @return A flipped DialogBox representing Fickle's message.
    */
    public static DialogBox getFickleDialog(String[] texts, Image img) {
        String mainMessage = texts[0];
        String specialMessage = texts[1];

        String outputText = mainMessage;
        if (!specialMessage.isEmpty()) {
            outputText += "\n\n" + specialMessage;
        }
        var db = new DialogBox(outputText, img);
        db.flip();
        return db;
    }
}
