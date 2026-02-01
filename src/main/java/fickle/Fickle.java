package fickle;

import fickle.commands.Command;
import fickle.exceptions.FickleException;
import fickle.tasks.TaskList;
import fickle.ui.Ui;
import fickle.parser.Parser;
import fickle.storage.Storage;

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
        try {
            tasks = new TaskList(storage.load());
        } catch (FickleException e) {
            ui.printFickleException(e.getMessage(), e.getSecondLine());
            tasks = new TaskList();
        }
    }

    /**
     * The entry point for the Fickle chatbot.
     * 
     * @param args
     */
    public static void main(String[] args) {
        Fickle fickle = new Fickle("data/tasks.txt");
        fickle.run();
    }

    /**
     * Runs the Fickle chatbot
     * Displays the logo, greeting, handles user input and executes commands.
     * Exits on "bye" command and print goodbye.
     */
    public void run() {
        ui.printLogo();
        ui.greet();
        boolean isExit = false;
        while (!isExit) {
            try {
                String input = ui.readInput().trim();
                Command command = Parser.parse(input);
                command.execute(tasks, ui);
                isExit = command.isExit();
            } catch (FickleException e) {
                // FickleException may contain an optional second display line
                if (e.hasSecondLine()) {
                    ui.printFickleException(e.getMessage(), e.getSecondLine());
                } else {
                    ui.printFickleException(e.getMessage());
                }
            } catch (Exception e) {
                ui.printError(e.getMessage());
            }
        }
    }
}