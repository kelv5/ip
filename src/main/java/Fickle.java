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
     * Runs the Fickle chatbot
     * Displays the logo, greeting, and handles user input.
     * Exits on "bye" command.
     */
    public void run() {
        ui.printLogo();
        ui.greet();

        while (true) {
            try {
                String input = ui.readInput().trim();
                if (input.isEmpty()) {
                    throw new IllegalArgumentException("Input cannot be empty.");
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
                            throw new IllegalArgumentException("The 'list' command does not accept any arguments.");
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
                        throw new IllegalArgumentException("Unknown command.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void processMarkTask(String contextWord) {
        if (contextWord.isEmpty()) {
            throw new IllegalArgumentException("Task index is required for 'mark' command.");
        }

        try {
            int index = Integer.parseInt(contextWord) - 1;
            if (tasks.size() == 0) {
                throw new IllegalArgumentException("You have no tasks to mark.");
            }
            if (!(index >= 0 && index < tasks.size())) {
                throw new IndexOutOfBoundsException("Task index out of bounds.");
            }

            Task task = tasks.getTask(index);
            task.markAsDone();
            ui.printMarkedTask(task.toString());

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Task index is not an integer.");
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Task index out of bounds.");
        }
    }

    private void processUnmarkTask(String contextWord) {
        if (contextWord.isEmpty()) {
            throw new IllegalArgumentException("Task index is required for 'unmark' command.");
        }

        try {
            int index = Integer.parseInt(contextWord) - 1;
            if (tasks.size() == 0) {
                throw new IllegalArgumentException("You have no tasks to unmark.");
            }

            if (!(index >= 0 && index < tasks.size())) {
                throw new IndexOutOfBoundsException("Task index out of bounds.");
            }
            Task task = tasks.getTask(index);
            task.markAsDone();
            ui.printUnmarkedTask(task.toString());

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Task index is not an integer.");
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Task index out of bounds.");
        }
    }

    private void processAddDeadlineTask(String contextWord) {
        if (contextWord.isEmpty()) {
            throw new IllegalArgumentException("Deadline must have a name");
        }

        if (!contextWord.contains("/by")) {
            throw new IllegalArgumentException("Deadline must have a /by to specify due time");
        }

        String[] contents = contextWord.split("/by", 2);
        String name = contents[0].trim();
        String by = contents[1].trim();
        if (name.isEmpty() || by.isEmpty()) {
            throw new IllegalArgumentException("Deadline must have a name and due time");
        }
        Task task = new Deadline(name, by);
        String addedTaskResponse = tasks.addTask(task);
        ui.printAddedTask(addedTaskResponse);
    }

    private void processAddTodoTask(String contextWord) {
        if (contextWord.isEmpty()) {
            throw new IllegalArgumentException("Todo must have a name");
        }
        Task task = new Todo(contextWord);
        String addedTaskResponse = tasks.addTask(task);
        ui.printAddedTask(addedTaskResponse);
    }

    private void processAddEventTask(String contextWord) {
        if (contextWord.isEmpty()) {
            throw new IllegalArgumentException("Event must have a name");
        }

        if (!contextWord.contains("/from")) {
            throw new IllegalArgumentException("Event must have a /from to specify start time");
        }

        if (!contextWord.contains("/to")) {
            throw new IllegalArgumentException("Event must have a /to to specify end time");
        }

        if (contextWord.indexOf("/from") > contextWord.indexOf("/to")) {
            throw new IllegalArgumentException("/from has to be in front of /to");
        }

        String[] firstSecondParts = contextWord.split("/from", 2);
        String name = firstSecondParts[0].trim();
        String[] secondThirdParts = firstSecondParts[1].split("/to", 2);
        String from = secondThirdParts[0].trim();
        String to = secondThirdParts[1].trim();

        if (name.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new IllegalArgumentException("Event must have a name, start time, and end time");
        }
        Task task = new Event(name, from, to);
        String addedTaskResponse = tasks.addTask(task);
        ui.printAddedTask(addedTaskResponse);
    }
}