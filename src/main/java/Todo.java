public class Todo extends Task {
    /**
     * Constructor for Todo task.
     * 
     * @param name The name of the todo task.
     */
    public Todo(String name) {
        super(name);
    }

    /**
     * Returns the string representation of the todo task.
     * 
     * @return The string representation with taskType, status icon, name.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
