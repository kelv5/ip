public class Task {
    private String name;

    /**
     * Constructor for Task.
     * 
     * @param name The name of the task.
     */
    public Task(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the task.
     * 
     * @return The taskname.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the string representation of the task.
     * 
     * @return The taskname as string.
     */
    @Override
    public String toString() {
        return name;
    }
}