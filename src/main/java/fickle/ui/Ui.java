package fickle.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.IntStream;

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
     * Sets Fickle's goodbye message.
     */
    public void sayGoodbye() {
        String mainMessage = "Goodbye. See you again soon! \n(This window will be closed automatically)";
        String specialMessage = "Day by Day";

        setOutputMessage(mainMessage, specialMessage);
    }

    /**
     * Sets the added task message.
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

        setOutputMessage(mainMessage, specialMessage);
    }

    /**
     * Sets the deleted task message.
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

        setOutputMessage(mainMessage, specialMessage);
    }

    /**
     * Sets the marked task message.
     *
     * @param taskname The name of the marked task.
     */
    public void printMarkedTask(String taskname) {
        String mainMessage = "All set. This task is marked as done: \n  " + taskname;
        String specialMessage = "One After Another";

        setOutputMessage(mainMessage, specialMessage);
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

        setOutputMessage(updatedMainMessage, specialMessage);
    }

    /**
     * Sets the unmarked task message.
     *
     * @param taskname The name of the unmarked task.
     */
    public void printUnmarkedTask(String taskname) {
        String mainMessage = "Noted. This task is now unmarked: \n  " + taskname;
        String specialMessage = "Pace Yourself";

        setOutputMessage(mainMessage, specialMessage);
    }

    /**
     * Prepares the list of tasks for display.
     *
     * @param tasks The tasklist to be printed.
     */
    public void printTaskList(TaskList tasks) {

        String header = "Here's your task list for you:";
        String emptyMainString = "No tasks remaining in your task list.";
        String emptySpecialString = "A Little Happiness";
        String nonEmptySpecialMsg = "Glimpses of a Journey";
        ArrayList<Task> allTasks = tasks.getAllTasks();

        prepareTaskList(allTasks, header, emptyMainString, emptySpecialString, nonEmptySpecialMsg);
    }

    /**
     * Prepares the list of tasks that match the keyword for display.
     *
     * @param keyword The keyword to search for tasks.
     * @param matchedTasks The list of tasks that match the keyword.
     */
    public void printMatchedTaskList(String keyword, ArrayList<Task> matchedTasks) {
        String header = "Here are the matching tasks for [" + keyword + "] in your list:";
        String emptyMainString = "Sorry, no tasks found matching [" + keyword + "].";
        String emptySpecialString = "Out of Nothing";
        String nonEmptySpecialMsg = "Hidden Love";

        prepareTaskList(matchedTasks, header, emptyMainString, emptySpecialString, nonEmptySpecialMsg);
    }

    /**
     * Prepares the list of tasks that are scheduled on the given date for display.
     *
     * @param date The target date to search for tasks.
     * @param scheduledTasks The list of tasks that occur on the date.
     */
    public void printScheduledTaskList(LocalDate date, ArrayList<Task> scheduledTasks) {
        String header = "Here are the tasks scheduled on:" + date + ":";
        String emptyMainString = "No tasks scheduled on " + date + ".";
        String emptySpecialString = "A Little Happiness";
        String nonEmptySpecialMsg = "Live in Life";

        prepareTaskList(scheduledTasks, header, emptyMainString, emptySpecialString, nonEmptySpecialMsg);
    }

    /**
     * Prepares the list of help messages for display.
     *
     * @param helpMessages The list of help message arrays.
     */
    public void printHelpMessages(ArrayList<String[]> helpMessages) {
        assert helpMessages != null && !(helpMessages.isEmpty()) : "helpMessages should never be null or empty.";

        String header = "Here are your Help messages:";
        StringBuilder sb = new StringBuilder();
        sb.append(header + " \n\n");

        for (String[] helpMessage : helpMessages) {
            String description = helpMessage[0];
            String format = helpMessage[1];

            sb.append("Description: " + description + "\n");
            sb.append("Format: " + format + "\n\n");
        }

        String mainMessage = sb.toString().trim();
        String specialMessage = "A Guide for You";
        setOutputMessage(mainMessage, specialMessage);
    }

    // Prepares a task list for display with a main and special message.
    private void prepareTaskList(ArrayList<Task> tasks, String header, String emptyMainMsg, String emptySpecialMsg,
                                    String nonEmptySpecialMsg) {
        if (tasks.isEmpty()) {
            setOutputMessage(emptyMainMsg, emptySpecialMsg);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(header + " \n\n");

        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1) + ". " + tasks.get(i).toString() + "\n");
        }

        String mainMessage = sb.toString().trim();
        setOutputMessage(mainMessage, nonEmptySpecialMsg);
    }

    /**
    * Prepares the list of all the warnings caused by corrupted lines for display.
    *
    * @param corruptedWarnings The list of corrupted line messages.
    */
    public void buildCorruptedWarnings(ArrayList<String> corruptedWarnings) {
        assert !corruptedWarnings.isEmpty() : "corruptedWarnings should not be empty";

        String mainMessage = "Warning! Corrupted lines below skipped from your saved tasks: \n";

        mainMessage += IntStream.range(0, corruptedWarnings.size())
                                        .mapToObj(i -> (i + 1) + ". " + corruptedWarnings.get(i))
                                        .reduce((s1, s2) -> s1 + "\n" + s2).orElse("");

        String specialMessage = "Anything Goes";

        setOutputMessage(mainMessage, specialMessage);
    }

    /**
     * Sets a Exception message for invalid input with double lines.
     *
     * @param exceptionMessage The description the invalid input.
     * @param secondLine The second message to display.
     */
    public void printFickleException(String exceptionMessage, String secondLine) {
        String mainMessage = "[Invalid Input] " + exceptionMessage;
        String specialMessage = secondLine;

        setOutputMessage(mainMessage, specialMessage);
    }

    /**
     * Sets an error message when tasks cannot be loaded from or saved to a
     * file.
     *
     * @param exceptionMessage The description of the file operation failure.
     */
    public void printFickleException(String exceptionMessage) {
        String mainMessage = "[Load/Save Error] " + exceptionMessage;
        String specialMessage = "";

        setOutputMessage(mainMessage, specialMessage);
    }

    /**
     * Sets an error message.
     *
     * @param errorMessage The message description the error.
     */
    public void printError(String errorMessage) {
        String mainMessage = "[Error] " + errorMessage;
        String specialMessage = "";

        setOutputMessage(mainMessage, specialMessage);
    }

    // Updates the outputMessages to be displayed.
    private void setOutputMessage(String mainMessage, String specialMessage) {
        outputMessages = new String[] { mainMessage, specialMessage };
    }
}
