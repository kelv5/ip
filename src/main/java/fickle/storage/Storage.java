package fickle.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Scanner;

import fickle.exceptions.FickleException;
import fickle.tasks.Deadline;
import fickle.tasks.Event;
import fickle.tasks.Task;
import fickle.tasks.TaskList;
import fickle.tasks.Todo;

/**
 * Handles saving and loading of tasks to and from a file.
 */
public class Storage {
    private static final DateTimeFormatter LOAD_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd HHmm")
                                    .withResolverStyle(ResolverStyle.STRICT);
    private final String filePath;
    private ArrayList<String> corruptedWarnings;

    /**
     * Constructor for Storage.
     *
     * @param filePath The path if the save file.
     */
    public Storage(String filePath) {
        assert filePath != null && !filePath.isEmpty() : "filePath should not be null nor empty";

        this.filePath = filePath;
        corruptedWarnings = new ArrayList<String>();
    }

    /**
     * Returns the list of corrupted line warnings when loading tasks from save file.
     *
     * @return An Arraylist of corrupted line messages.
     */
    public ArrayList<String> getCorruptedWarnings() {
        return corruptedWarnings;
    }

    /**
     * Loads tasks from the save file.
     *
     * @return A TaskList with tasks loaded from the save file.
     *         Returns an empty TaskList if the file does not exist.
     *
     * @throws FickleException If the file exists but cannot be read.
     */
    public TaskList load() throws FickleException {
        checkAndCreateFolder();
        corruptedWarnings.clear();

        File file = new File(filePath);
        TaskList tasks = new TaskList();

        if (!file.exists()) {
            return tasks;
        }

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);

            tasks = parseTasksFromScanner(scanner);
        } catch (FileNotFoundException e) {
            throw new FickleException("Couldn't load tasks from file.");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return tasks;
    }

    // Parses tasks from scanner one by one.
    private TaskList parseTasksFromScanner(Scanner sc) {
        TaskList tasks = new TaskList();

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();

            if (line.isEmpty()) {
                continue;
            }

            try {
                Task task = parseTask(line);
                tasks.addTask(task);
            } catch (FickleException e) {
                corruptedWarnings.add("[" + e.getMessage() + "] " + line);
            }
        }

        return tasks;
    }

    /**
     * Overwrites the save file with the list of tasks.
     * Triggers this method with commands such as Mark, Unmark, or Delete.
     *
     * @param tasks The list of tasks to be saved.
     * @throws FickleException If fails to write to the saveFile.
     */
    public void overwriteSave(TaskList tasks) throws FickleException {
        try {
            FileWriter fw = new FileWriter(filePath);
            ArrayList<Task> allTasks = tasks.getAllTasks();

            assert allTasks != null : "The new tasklist to overwrite should not be null";

            for (Task task : allTasks) {
                fw.write(task.toStorageString());
                fw.write(System.lineSeparator());
            }

            fw.close();
        } catch (IOException e) {
            throw new FickleException("Couldn't save tasks to file.");
        }
    }

    /**
     * Appends a single task to be saved in the file.
     * Triggers this method with commands such as Todo, Event, Deadline.
     *
     * @param task The task to be appended to the save file.
     * @throws FickleException If fails to write to the saveFile.
     */
    public void appendSave(Task task) throws FickleException {
        assert task != null : "Task to be added in storage file should not be null";

        try {
            FileWriter fw = new FileWriter(filePath, true);
            fw.write(task.toStorageString());
            fw.write(System.lineSeparator());
            fw.close();
        } catch (IOException e) {
            throw new FickleException("Couldn't append tasks to file.");
        }
    }

    // Creates a folder if it does not exist. Otherwise, the folder remains unchanged.
    private void checkAndCreateFolder() {
        File file = new File(filePath);
        File parent = file.getParentFile();

        if (parent != null) {
            parent.mkdirs();
        }
    }

    /**
     * Parses a line from the storage file into a Task object.
     *
     * @param line The line from the storage file.
     * @return A Task object.
     * @throws FickleException If the line from storage file is corrupted.
     */
    private Task parseTask(String line) throws FickleException {
        assert line != null && !line.isEmpty() : "Storage line should not be null nor empty";

        String[] saveStringsParts = checkAndSplitLine(line);

        String taskType = saveStringsParts[0].trim();
        String doneStatus = saveStringsParts[1].trim();
        String name = saveStringsParts[2].trim();

        Task task;

        switch (taskType) {
        case "T":
            task = parseTodo(name);
            break;

        case "D":
            task = parseDeadline(name, saveStringsParts);
            break;

        case "E":
            task = parseEvent(name, saveStringsParts);
            break;

        default:
            throw new FickleException("Unknown Task Type");

        }

        if (doneStatus.equals("1")) {
            task.markAsDone();
        }

        return task;
    }

    // Checks a storage line and splits it.
    private String[] checkAndSplitLine(String line) throws FickleException {
        String[] parts = line.split(" \\| ");

        if (parts.length < 3) {
            throw new FickleException("Insufficient Fields");
        }

        String doneStatus = parts[1].trim();

        if (!doneStatus.equals("0") && !doneStatus.equals("1")) {
            throw new FickleException("Invalid Done Status");
        }

        return parts;
    }

    private Task parseTodo(String name) {
        return new Todo(name);
    }

    private Task parseDeadline(String name, String[] saveStringsParts) throws FickleException {
        if (saveStringsParts.length < 4) {
            throw new FickleException("Insufficient Fields for Deadline");
        }

        LocalDateTime by = parseStorageDateTime(saveStringsParts[3].trim());

        if (by == null) {
            throw new FickleException("Invalid Date/Time Format for Deadline");
        }

        return new Deadline(name, by);
    }

    private Task parseEvent(String name, String[] saveStringsParts) throws FickleException {
        if (saveStringsParts.length < 5) {
            throw new FickleException("Insufficient Fields for Event");
        }

        LocalDateTime from = parseStorageDateTime(saveStringsParts[3].trim());
        LocalDateTime to = parseStorageDateTime(saveStringsParts[4].trim());

        if (from == null || to == null) {
            throw new FickleException("Invalid Date/Time Format for Event");
        }

        if (from.isAfter(to)) {
            throw new FickleException("Event start time is after the end time");
        }

        return new Event(name, from, to);
    }

    /**
     * Parses a date/time string in the format yyyy-MM-dd HHmm.
     *
     * @param dateTimeString The date/time string to parse.
     * @return A LocalDateTime object if valid, or null invalid.
     */
    private LocalDateTime parseStorageDateTime(String dateTimeString) {
        assert dateTimeString != null && !dateTimeString.isEmpty() : "DateTime string should not be null nor empty";

        try {
            return LocalDateTime.parse(dateTimeString, LOAD_DATETIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
