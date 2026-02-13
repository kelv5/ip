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
    * Runs the chatbot, load tasks from storage, handles corrupted lines.
    * Returns welcome messages to display in the GUI.
    *
    * @return An Arraylist of string arrays representing welcome messages.
    */
    public ArrayList<String[]> run() {
        ArrayList<String[]> welcomeMessages = new ArrayList<>();

        loadTasks(welcomeMessages);
        addCorruptedWarnings(welcomeMessages);
        addGreetingMessages(welcomeMessages);

        return welcomeMessages;
    }

    /**
    * Loads tasks from storage into the task list.
    * Initialises an empty task list and display exception message if failed to load storage.
    *
    * @param welcomeMessages ArrayList to collect any exception message for display.
    */
    private void loadTasks(ArrayList<String[]> welcomeMessages) {
        try {
            tasks = new TaskList(storage.load());
        } catch (FickleException e) {
            ui.printFickleException(e.getMessage());
            welcomeMessages.add(ui.getOutput());

            tasks = new TaskList();
        }
    }

    /**
    * Checks for any corrupted storage lines and adds warning messages into welcomeMessages.
    *
    * @param welcomeMessages ArrayList to collect any warning messages.
    */
    private void addCorruptedWarnings(ArrayList<String[]> welcomeMessages) {
        ArrayList<String> warnings = storage.getCorruptedWarnings();

        if (!warnings.isEmpty()) {
            // Add corrupted warnings into welcome messages
            ui.buildCorruptedWarnings(warnings);
            welcomeMessages.add(ui.getOutput());
        }
    }

    /**
     * Adds the greeting meetings and ASCII logo to the welcome messages.
     *
     * @param welcomeMessages ArrayList to collect greeting messages and logo.
     */
    private void addGreetingMessages(ArrayList<String[]> welcomeMessages) {
        welcomeMessages.add(ui.printLogoAndGreet());
    }

    /**
    * Executes a user command and returns Fickle's response messages.
    *
    * @param input The raw input from the user.
    * @return String array with main and special messages to be displayed.
    */
    public String[] getResponse(String input) {
        try {
            executeCommand(input);
        } catch (FickleException e) {
            ui.printFickleException(e.getMessage(), e.getSecondLine());
        } catch (Exception e) {
            ui.printError(e.getMessage());
        }

        // Always return the collected UI output
        return ui.getOutput();
    }

    /**
    * Parses user input and executes the command.
    *
    * @param input Raw input string.
    * @throws FickleException If parsing of input or command execution fails.
    */
    private void executeCommand(String input) throws FickleException {
        Command command = Parser.parse(input);
        command.execute(tasks, ui, storage);
    }
}
