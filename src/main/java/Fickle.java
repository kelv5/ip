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
                    throw new FickleException("Input can't be empty.", "Don't Leave me Alone.");
                }

                String[] commandParts = input.split(" ", 2);
                String commandWord = commandParts[0].toLowerCase();
                String contextWord = commandParts.length > 1 ? commandParts[1] : "";

                switch (commandWord) {
                    case "bye":
                        ui.sayGoodbye();
                        return;

                    case "list":
                        processList(contextWord);
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
                        throw new FickleException(
                                "Sorry, I didn't understand that. Try using my commands!", "Going Nowhere.");
                }
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

    private void processList(String contextWord) throws FickleException {
        if (contextWord.length() > 0) {
            throw new FickleException("The list command doesn't accept any arguments.", "Even fickleness has rules");
        } else {
            ui.printTaskList(tasks);
        }

    }

    private void processMarkTask(String contextWord) throws FickleException {
        if (contextWord.isEmpty()) {
            throw new FickleException(
                    "Missing task index for 'mark' command.", "Shadow's shadow without it");
        }

        try {
            int index = Integer.parseInt(contextWord) - 1;
            if (tasks.getSize() == 0) {
                throw new FickleException("No tasks remaining to mark.", "A Little Happiness");
            }
            if (index < 0) {
                throw new FickleException(
                        "Invalid task index: less than 1.", "Too small, Insignificance");
            }

            if (index >= tasks.getSize()) {
                throw new FickleException(
                        "Invalid task index: exceeds the total number of tasks.", "Too Much");
            }

            Task task = tasks.getTask(index);
            task.markAsDone();
            ui.printMarkedTask(task.toString());

            // Special message when all tasks are completed
            if (tasks.isAllMarked()) {
                ui.printSingleLineWithoutLine("Congratulations! All tasks are completed!");
                ui.printEasterAlignedRight("A Little Happiness");
            }

        } catch (NumberFormatException e) {
            throw new FickleException(
                    "Invalid task index: not an integer.", "Confuses me, Contradiction");
        }
    }

    private void processUnmarkTask(String contextWord) throws FickleException {
        if (contextWord.isEmpty()) {
            throw new FickleException(
                    "Missing task index for 'unmark' command.", "Shadow's shadow without it");
        }

        try {
            int index = Integer.parseInt(contextWord) - 1;
            if (tasks.getSize() == 0) {
                throw new FickleException("No tasks remaining to unmark.", "A Little Happiness");
            }
            if (index < 0) {
                throw new FickleException(
                        "Invalid task index: less than 1.", "Too small, Insignificance");
            }

            if (index >= tasks.getSize()) {
                throw new FickleException(
                        "Invalid task index: exceeds the total number of tasks.", "Too Much");
            }

            Task task = tasks.getTask(index);
            task.markAsNotDone();
            ui.printUnmarkedTask(task.toString());

        } catch (NumberFormatException e) {
            throw new FickleException(
                    "Invalid task index: not an integer.", "Confuses me, Contradiction");
        }
    }

    private void processAddDeadlineTask(String contextWord) throws FickleException {
        if (contextWord.isEmpty()) {
            throw new FickleException("Deadline name is missing.", "Like Missing You");
        }

        if (!contextWord.contains("/by")) {
            throw new FickleException(
                    "Deadline must have a /by to specify due time.", "Too Late without it");
        }

        String[] contents = contextWord.split("/by", 2);
        String name = contents[0].trim();
        String by = contents[1].trim();
        if (name.isEmpty() || by.isEmpty()) {
            throw new FickleException("Deadline must have a name and due time.", "else, it's Untold");
        }
        Task task = new Deadline(name, by);
        addTaskandPrint(task);
    }

    private void processAddTodoTask(String contextWord) throws FickleException {
        if (contextWord.isEmpty()) {
            throw new FickleException("Todo name is missing.", "Like Missing You");
        }
        Task task = new Todo(contextWord);
        addTaskandPrint(task);
    }

    private void processAddEventTask(String contextWord) throws FickleException {
        if (contextWord.isEmpty()) {
            throw new FickleException("Event name is missing.", "Like Missing You");
        }

        if (!contextWord.contains("/from") || !contextWord.contains("/to")) {
            throw new FickleException(
                    "Event must have both /from and /to to specify start and end time.",
                    "Too Late without it");
        }

        if (contextWord.indexOf("/from") > contextWord.indexOf("/to")) {
            throw new FickleException(
                    "/from has to be in front of /to.", "Confuses me, Contradiction");
        }

        String[] firstSecondParts = contextWord.split("/from", 2);
        String name = firstSecondParts[0].trim();
        String[] secondThirdParts = firstSecondParts[1].split("/to", 2);
        String from = secondThirdParts[0].trim();
        String to = secondThirdParts[1].trim();

        if (name.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new FickleException(
                    "Event must have a name, start time, and end time.", "else, it's Untold");
        }
        Task task = new Event(name, from, to);
        addTaskandPrint(task);
    }

    private void addTaskandPrint(Task task) {
        String addedTaskResponse = "  " + tasks.addTask(task);
        ui.printAddedTask(addedTaskResponse, tasks.getSize());
    }
}