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
                String input = ui.readInput();
                if (input.equalsIgnoreCase("bye")) {
                    break;
                }
                if (input.equalsIgnoreCase("list")) {
                    ui.printTaskList(tasks);
                } else {
                    Task task = new Task(input);
                    String addedTaskResponse = tasks.addTask(task);
                    ui.printAddedTask(addedTaskResponse);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        ui.sayGoodbye();
    }
}