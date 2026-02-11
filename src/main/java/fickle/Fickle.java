package fickle;

import java.util.ArrayList;

import fickle.commands.Command;
import fickle.exceptions.FickleException;
import fickle.parser.Parser;
import fickle.storage.Storage;
import fickle.tasks.TaskList;
import fickle.ui.Ui;

/**
 * Fickle chatbot main class.
 */
public class Fickle {
    private Ui ui;
    private TaskList tasks;
    private Storage storage;

    /**
     * Constructor for Fickle.
     */
    public Fickle(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList();
    }

    /**
    * Loads tasks from storage, handles corrupted lines, and returns welcome messages to display in the GUI.
    *
    * @return An Arraylist of string arrays representing welcome messages.
    */
    public ArrayList<String[]> run() {
        ArrayList<String[]> welcomeMessages = new ArrayList<>();
        try {
            tasks = new TaskList(storage.load());

            ArrayList<String> warnings = storage.getCorruptedWarnings();

            if (!warnings.isEmpty()) {
                // Build and add corrupted warnings to welcome messages
                ui.buildCorruptedWarnings(warnings);
                welcomeMessages.add(ui.getOutput());
            }

        } catch (FickleException e) {
            // Handle storage load failure
            ui.printFickleException(e.getMessage(), e.getSecondLine());
            tasks = new TaskList();

            welcomeMessages.add(ui.getOutput());
        }
        // Always show logo and greeting
        welcomeMessages.add(ui.printLogoAndGreet());

        return welcomeMessages;
    }

    /**
    * Executes a user command and returns Fickle's response messages.
    *
    * @param input The raw input from the user.
    * @return String array with main and special messages to be displayed.
    */
    public String[] getResponse(String input) {
        boolean isExit = false;
        try {
            Command command = Parser.parse(input);
            command.execute(tasks, ui, storage);
            isExit = command.isExit();

            if (isExit) {
                ui.sayGoodbye();
            }

            return ui.getOutput();
        } catch (FickleException e) {
            // FickleException may contain an optional second display line
            if (e.hasSecondLine()) {
                ui.printFickleException(e.getMessage(), e.getSecondLine());
            } else {
                ui.printFickleException(e.getMessage());
            }
            return ui.getOutput();
        } catch (Exception e) {
            ui.printError(e.getMessage());
            return ui.getOutput();
        }
    }
}
