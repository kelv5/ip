package fickle.commands;

import java.util.ArrayList;
import java.util.Map;

import fickle.exceptions.FickleException;
import fickle.storage.Storage;
import fickle.tasks.TaskList;
import fickle.ui.Ui;

/**
 * Represents the help command.
 * Shows either help for all commands or for a specific command.
 */
public class HelpCommand extends Command {
    // AiAssisted: HELP_COMMAND_MAP was generated with the help of ChatGPT to organise all chatbot commands clearly.
    // Provides descriptions and example formats for each of the commands supoorted.
    private static final Map<String, String[]> HELP_COMMAND_MAP = Map.ofEntries(
        Map.entry("todo", new String[] {
            "Add a todo task without any date.",
            "todo [TASK_NAME]"
        }),
        Map.entry("deadline", new String[] {
            "Add a deadline task with a due date and optional time." + "(Example DateTime: 21/8/2021 0911)",
            "deadline [TASK_NAME] /by [DATETIME]"
        }),
        Map.entry("event", new String[] {
            "Add an event task with a start and end date with optional time." + "(Example DateTime: 21/8/2021 0911)",
            "event [TASK_NAME] /from [START_DATETIME] /to [END_DATETIME]"
        }),
        Map.entry("delete", new String[] {
            "Delete a task at the specific index from your list.",
            "delete [TASK_INDEX]"
        }),
        Map.entry("mark", new String[] {
            "Mark a task at the specific index as done.",
            "mark [TASK_INDEX]"
        }),
        Map.entry("unmark", new String[] {
            "Unmark a task at the specific index.",
            "unmark [TASK_INDEX]"
        }),
        Map.entry("find", new String[] {
            "Search for tasks containing the keyword.",
            "find [KEYWORD]"
        }),
        Map.entry("list", new String[] {
            "View all your current tasks.",
            "list"
        }),
        Map.entry("schedule", new String[] {
            "View all tasks scheduled on a date (Example Date: 21/8/2021).",
            "schedule [DATE]"
        }),
        Map.entry("help", new String[] {
            "Show a help list of all the commands, or for a specific command.",
            "help OR help [COMMAND_WORD]"
        }),
        Map.entry("bye", new String[] {
            "Exit the chatbot and close the window automatically.",
            "bye"
        })
    );

    private final String commandName;

    /**
     * Constructor for HelpCommand.
     *
     * @param commandName Optional command name for specific command help. Empty string for general help.
     */
    public HelpCommand(String commandName) {
        this.commandName = commandName.toLowerCase();
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FickleException {
        ArrayList<String[]> helpMessages = new ArrayList<>();

        if (commandName.isEmpty()) {
            // General Help
            helpMessages.addAll(HELP_COMMAND_MAP.values());
        } else {
            // Specific command help
            helpMessages.add(getSpecificHelpMessage(commandName));
        }

        ui.printHelpMessages(helpMessages);
    }

    // Returns help message for one specific command or throws FickleException if invalid command name.
    private String[] getSpecificHelpMessage(String commandName) throws FickleException {
        if (!HELP_COMMAND_MAP.containsKey(commandName)) {
            throw new FickleException("\"" + commandName
                                            + "\" is not a valid command name. Type 'help' to see all commands.",
                                            "Learning from...");
        }
        return HELP_COMMAND_MAP.get(commandName);
    }
}
