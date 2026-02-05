package fickle.commands;

import fickle.storage.Storage;
import fickle.tasks.TaskList;
import fickle.ui.Ui;

/**
 * Says goodbye to the user and ends the chatbot.
 */
public class ByeCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.sayGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
