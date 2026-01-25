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
        this.ui = new Ui();
        this.tasks = new TaskList();
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
     * Runs the Fickle chatbot Displays the logo, greeting, and handles user
     * input. Exits on "bye" command.
     */
    public void run() {
        ui.printLogo();
        ui.greet();

        while (true) {
            try {
                String input = ui.readInput().trim();
                if (input.isEmpty()) {
                    throw new IllegalArgumentException("Input can't be empty ~~~ Don't Leave me Alone.");
                }
                String[] commandParts = input.split(" ", 2);
                String commandWord = commandParts[0].toLowerCase();

                String contextWord = commandParts.length > 1 ? commandParts[1] : "";

                switch (commandWord) {
                    case "bye":
                        ui.sayGoodbye();
                        return;

                    case "list":
                        if (contextWord.length() > 0) {
                            throw new IllegalArgumentException(
                                    "The 'list' command does not accept any arguments ~~~ Even fickleness has rules.");
                        } else {
                            ui.printTaskList(tasks);
                        }
                        break;

                    case "mark":
                        processMarkTask(contextWord);
                        break;

                    case "unmark":
                        processUnmarkTask(contextWord);
                        break;

                    case "deadline":
                        processAddDeadlineTask(contextWord);
                        break;

                    case "todo":
                        processAddTodoTask(contextWord);
                        break;

                    case "event":
                        processAddEventTask(contextWord);
                        break;

                    default:
                        throw new IllegalArgumentException("Unknown command ~~~ Going Nowhere.");
                }
            } catch (IllegalArgumentException e) {
                ui.printException(e.getMessage());
            } catch (Exception e) {
                ui.printError(e.getMessage());
            }
        }
    }

    private void processMarkTask(String contextWord) {
        if (contextWord.isEmpty()) {
            throw new IllegalArgumentException(
                    "Missing task index for 'mark' command ~~~ Without it, task is like a Shadow's shadow. ");
        }

        try {
            int index = Integer.parseInt(contextWord) - 1;
            if (tasks.getSize() == 0) {
                throw new IllegalArgumentException("No tasks remaining to mark ~~~ A Little Happiness.");
            }
            if (index < 0) {
                throw new IndexOutOfBoundsException(
                        "Invalid task index: less than 1 ~~~ Too small, like Insignificance.");
            }

            if (index >= tasks.getSize()) {
                throw new IndexOutOfBoundsException(
                        "Invalid task index: exceeds the total number of tasks ~~~ Too Much.");
            }

            Task task = tasks.getTask(index);
            task.markAsDone();
            ui.printMarkedTask(task.toString());

            if (tasks.isAllMarked()) {
                ui.printSingleLineWithoutLine("Congratulations! All tasks are completed!");
                ui.printEasterAlignedRight("A Little Happiness");
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Invalid task index: not an integer ~~~ Confuses me, like Contradiction.");
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void processUnmarkTask(String contextWord) {
        if (contextWord.isEmpty()) {
            throw new IllegalArgumentException(
                    "Missing task index for 'unmark' command ~~~ Without it, task is like a Shadow's shadow. ");
        }

        try {
            int index = Integer.parseInt(contextWord) - 1;
            if (tasks.getSize() == 0) {
                throw new IllegalArgumentException("No tasks remaining to unmark ~~~ A Little Happiness. ");
            }
            if (index < 0) {
                throw new IndexOutOfBoundsException(
                        "Invalid task index: less than 1 ~~~ Too small, like Insignificance.");
            }

            if (index >= tasks.getSize()) {
                throw new IndexOutOfBoundsException(
                        "Invalid task index: exceeds the total number of tasks ~~~ Too Much.");
            }

            Task task = tasks.getTask(index);
            task.markAsNotDone();
            ui.printUnmarkedTask(task.toString());

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Invalid task index: not an integer ~~~ It confuses me, like Contradiction.");
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void processAddDeadlineTask(String contextWord) {
        if (contextWord.isEmpty()) {
            throw new IllegalArgumentException("Deadline must have a name ~~~ Missing it, like Missing You.");
        }

        if (!contextWord.contains("/by")) {
            throw new IllegalArgumentException(
                    "Deadline must have a /by to specify due time ~~~ Without it, it's Too Late.");
        }

        String[] contents = contextWord.split("/by", 2);
        String name = contents[0].trim();
        String by = contents[1].trim();
        if (name.isEmpty() || by.isEmpty()) {
            throw new IllegalArgumentException("Deadline must have a name and due time ~~~ else, it's Untold.");
        }
        Task task = new Deadline(name, by);
        addTaskandPrint(task);
    }

    private void processAddTodoTask(String contextWord) {
        if (contextWord.isEmpty()) {
            throw new IllegalArgumentException("Todo must have a name ~~~ Missing it, like Missing You.");
        }
        Task task = new Todo(contextWord);
        addTaskandPrint(task);
    }

    private void processAddEventTask(String contextWord) {
        if (contextWord.isEmpty()) {
            throw new IllegalArgumentException("Event must have a name ~~~ Missing it, like Missing You.");
        }

        if (!contextWord.contains("/from") || !contextWord.contains("/to")) {
            throw new IllegalArgumentException(
                    "Event must have both /from and /to to specify start and end time ~~~ Without them, it's Too Late.");
        }

        if (contextWord.indexOf("/from") > contextWord.indexOf("/to")) {
            throw new IllegalArgumentException(
                    "/from has to be in front of /to ~~~ It confuses me, like Contradiction.");
        }

        String[] firstSecondParts = contextWord.split("/from", 2);
        String name = firstSecondParts[0].trim();
        String[] secondThirdParts = firstSecondParts[1].split("/to", 2);
        String from = secondThirdParts[0].trim();
        String to = secondThirdParts[1].trim();

        if (name.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new IllegalArgumentException(
                    "Event must have a name, start time, and end time ~~~ else, it's Untold.");
        }
        Task task = new Event(name, from, to);
        addTaskandPrint(task);
    }

    private void addTaskandPrint(Task task) {
        String addedTaskResponse = "  " + tasks.addTask(task);
        ui.printAddedTask(addedTaskResponse, tasks.getSize());
    }
}