package fickle;

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

        try {
            tasks = new TaskList(storage.load());
        } catch (FickleException e) {
            ui.printFickleException(e.getMessage(), e.getSecondLine());
            tasks = new TaskList();
        }
    }

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
