package fickle.commands;

import fickle.tasks.TaskList;
import fickle.ui.Ui;

/**
 * Says goodbye to the user and ends the chatbot.
 */
public class ByeCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui) {
        ui.sayGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
