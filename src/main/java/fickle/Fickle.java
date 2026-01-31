package fickle;

import fickle.commands.Command;
import fickle.exceptions.FickleException;
import fickle.tasks.TaskList;
import fickle.ui.Ui;
import fickle.parser.Parser;

/**
 * Fickle chatbot main class.
 */
public class Fickle {
    private Ui ui;
    private TaskList tasks;

    /**
     * Constructor for Fickle.
     */
    public Fickle() {
        ui = new Ui();
        tasks = new TaskList();
    }

    /**
     * The entry point for the Fickle chatbot.
     * 
     * @param args
     */
    public static void main(String[] args) {
        Fickle fickle = new Fickle();
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
                    ui.printInvalidInput(e.getMessage(), e.getSecondLine());
                } else {
                    ui.printInvalidInput(e.getMessage());
                }
            } catch (Exception e) {
                ui.printError(e.getMessage());
            }
        }
    }
}