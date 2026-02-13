package fickle.ui;

import java.util.ArrayList;

import fickle.tasks.Task;
import fickle.tasks.TaskList;

/**
 * UI class for handling user interactions.
 */
public class Ui {
    private String[] outputMessages;

    /**
     * Constructor for Ui.
     */
    public Ui() {
        outputMessages = new String[] { "", "" };
    }

    /**
     * Returns output messages represented by [mainMessage, specialMessage].
     *
     * @return A string array of output messages.
     */
    public String[] getOutput() {
        assert outputMessages.length == 2 : "outputMessages should contain exactly 2 elements";
        return outputMessages;
    }

    /**
     * Displays the ASCII art logo and Fickle's greeting message.
     */
    public String[] printLogoAndGreet() {
        // ASCII art generated using https://patorjk.com/software/taag/ (Font: Stforek)
        String logo = """
                                         ___ _   ____  ___   ___
                                        | __| | / _/ |/ / | | __|
                                        | _|| || \\_|   <| |_| _|
                                        |_| |_| \\__/_|\\_\\___|___|
                                        """;

        String greetings = "Hi! I'm Fickle\n" + "What feels right to start with today?";

        String mainMessage = logo + "\n" + greetings;
        String specialMessage = "";

        return new String[] { mainMessage, specialMessage };
    }

    /**
     * Prints Fickle's goodbye message.
     */
    public void sayGoodbye() {
        String mainMessage = "Goodbye. See you again soon! \n(This window will be closed automatically)";
        String specialMessage = "Day by Day";

        outputMessages = new String[] { mainMessage, specialMessage };
    }

    /**
     * Prints the added task message.
     *
     * @param taskname The name of the added task.
     * @param totalTasks Total number of tasks after added a new task.
     *
     */
    public void printAddedTask(String taskname, int totalTasks) {
        assert taskname != null && !taskname.isEmpty() : "Name of the task added should not be null, nor empty";
        assert totalTasks >= 1 : "totalTasks should be at least 1 when a task is added";

        String taskAddedMessage = "Got it. I've added this task: \n  " + taskname + "\n\n";
        String totalTasksMessage = "Now you have " + totalTasks + " task" + ((totalTasks == 1) ? "" : "s")
                                        + " in the list.";

        String mainMessage = taskAddedMessage + totalTasksMessage;
        String specialMessage = "Still Early";

        outputMessages = new String[] { mainMessage, specialMessage };
    }

    /**
     * Prints the deleted task message.
     *
     * @param taskname The name of the deleted task.
     * @param totalTasks Total number of tasks after deleted a new task.
     *
     */
    public void printDeletedTask(String taskname, int totalTasks) {
        assert taskname != null && !taskname.isEmpty() : "Name of the task deleted should not be null, nor empty";
        assert totalTasks >= 0 : "totalTasks cannot be negative";

        String taskRemovedMessage = "Noted. I've removed this task: \n  " + taskname + "\n\n";
        String totalTasksMessage = "Now you have " + totalTasks + " task" + ((totalTasks == 1) ? "" : "s")
                                        + " in the list.";

        String mainMessage = taskRemovedMessage + totalTasksMessage;
        String specialMessage = "It's Gone";

        outputMessages = new String[] { mainMessage, specialMessage };
    }

    /**
     * Prints the marked task message.
     *
     * @param taskname The name of the marked task.
     */
    public void printMarkedTask(String taskname) {
        String mainMessage = "All set. This task is marked as done: \n  " + taskname;
        String specialMessage = "One After Another";

        outputMessages = new String[] { mainMessage, specialMessage };
    }

    /**
    * Appends a congratulatory message when all tasks are marked as done.
    */
    public void printAllTasksMarked() {
        // Append to existing mainMessage
        String currentMainMessage = outputMessages[0];
        String newLine = "\n\nCongratulations! All tasks are completed!";
        String updatedMainMessage = currentMainMessage + newLine;

        // Replace with a new specialMessage.
        String specialMessage = "A Little Happiness";

        outputMessages = new String[] { updatedMainMessage, specialMessage };
    }

    /**
     * Prints the unmarked task message.
     *
     * @param taskname The name of the unmarked task.
     */
    public void printUnmarkedTask(String taskname) {
        String mainMessage = "Noted. This task is now unmarked: \n  " + taskname;
        String specialMessage = "Pace Yourself";

        outputMessages = new String[] { mainMessage, specialMessage };
    }

    /**
     * Prints the list of tasks.
     *
     * @param tasks The tasklist to be printed.
     */
    public void printTaskList(TaskList tasks) {
        if (tasks.getSize() == 0) {
            String mainMessage = "No tasks remaining in your task list.";
            String specialMessage = "A Little Happiness";

            outputMessages = new String[] { mainMessage, specialMessage };
            return;
        }

        assert tasks.getSize() > 0 : "Non-empty TaskList should have at least one task in it";

        StringBuilder sb = new StringBuilder();
        sb.append("Here's your task list for you: \n");

        for (int i = 0; i < tasks.getSize(); i++) {
            sb.append((i + 1) + ". " + tasks.getTask(i).toString() + "\n");
        }

        String mainMessage = sb.toString().trim();
        String specialMessage = "Glimpses of a Journey";

        outputMessages = new String[] { mainMessage, specialMessage };
    }

    /**
     * Prints the list of tasks that match the keyword in the task list.
     *
     * @param keyword The keyword to search for tasks.
     * @param matchedTasks The list of tasks that match the keyword.
     */
    public void printMatchedTaskList(String keyword, ArrayList<Task> matchedTasks) {
        assert !keyword.isEmpty() : "Keyword should not be empty";

        if (matchedTasks.size() == 0) {
            String mainMessage = "Sorry, no tasks found matching [" + keyword + "].";
            String specialMessage = "Out of Nothing";

            outputMessages = new String[] { mainMessage, specialMessage };
            return;
        }

        assert matchedTasks.size() > 0 : "Non-empty matched Tasklist should have at least one matched task";

        StringBuilder sb = new StringBuilder();
        sb.append("Here are the matching tasks for [" + keyword + "] in your list: \n");

        for (int i = 0; i < matchedTasks.size(); i++) {
            sb.append((i + 1) + ". " + matchedTasks.get(i).toString() + "\n");
        }

        String mainMessage = sb.toString().trim();
        String specialMessage = "Hidden Love";

        outputMessages = new String[] { mainMessage, specialMessage };
    }

    /**
    * Prints the list of all the warnings caused by corrupted lines in the save file.
    *
    * @param corruptedWarnings The list of corrupted line messages.
    */
    public void buildCorruptedWarnings(ArrayList<String> corruptedWarnings) {
        assert !corruptedWarnings.isEmpty() : "corruptedWarnings should not be empty";

        StringBuilder sb = new StringBuilder();
        sb.append("Warning! Corrupted lines below skipped from your saved tasks: \n");

        for (int i = 0; i < corruptedWarnings.size(); i++) {
            sb.append((i + 1) + ". " + corruptedWarnings.get(i) + "\n");
        }

        String mainMessage = sb.toString().trim();
        String specialMessage = "Anything Goes";

        outputMessages = new String[] { mainMessage, specialMessage };
    }

    /**
     * Prints a Exception message for invalid input with double lines.
     *
     * @param exceptionMessage The description the invalid input.
     * @param secondLine The second message to display.
     */
    public void printFickleException(String exceptionMessage, String secondLine) {
        String mainMessage = "[Invalid Input]  " + exceptionMessage;
        String specialMessage = secondLine;

        outputMessages = new String[] { mainMessage, specialMessage };
    }

    /**
     * Prints an error message when tasks cannot be loaded from or saved to a
     * file.
     *
     * @param exceptionMessage The description of the file operation failure.
     */
    public void printFickleException(String exceptionMessage) {
        String mainMessage = "[Load/Save Error]  " + exceptionMessage;
        String specialMessage = "";

        outputMessages = new String[] { mainMessage, specialMessage };
    }

    /**
     * Prints an error message.
     *
     * @param errorMessage The message description the error.
     */
    public void printError(String errorMessage) {
        String mainMessage = "[Error]  " + errorMessage;
        String specialMessage = "";

        outputMessages = new String[] { mainMessage, specialMessage };
    }
}
