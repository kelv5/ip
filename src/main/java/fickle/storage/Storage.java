package fickle.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import fickle.exceptions.FickleException;
import fickle.tasks.Deadline;
import fickle.tasks.Event;
import fickle.tasks.Task;
import fickle.tasks.TaskList;
import fickle.tasks.Todo;

/**
 * Handles saving and loading of tasks to/from a file.
 */
public class Storage {

    private final String filePath;

    /**
     * Constructor for Storage.
     *
     * @param filePath The path if the save file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the save file.
     *
     * @return A TaskList with the tasks loaded from the save file. Returns an
     * empty TaskList if the file does not exist.
     * 
     * @throws FickleException If the file exists but cannot be read.
     */
    public TaskList load() throws FickleException {
        checkAndCreateFolder();

        File file = new File(filePath);
        TaskList tasks = new TaskList();

        if (!file.exists()) {
            return tasks;
        }

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                try {
                    Task task = parseTask(line);
                    tasks.addTask(task);
                } catch (FickleException e) {
                    System.out.println("Warning!  Corrupted line: " + e.getMessage());
                    System.out.println("Corrupted Line Skipped:  " + line);
                }
            }
        } catch (FileNotFoundException e) {
            throw new FickleException("Couldn't load tasks from file.");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return tasks;
    }

    /**
     * Overwrites the save file with the list of tasks. This method is triggered
     * by commands such as Mark, Unmark, Delete.
     *
     * @param tasks The list of tasks to be saved.
     * @throws FickleException If fails to write to the saveFile.
     */
    public void overwriteSave(TaskList tasks) throws FickleException {
        try {
            FileWriter fw = new FileWriter(filePath);
            ArrayList<Task> allTasks = tasks.getAllTasks();
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
     * Appends a single task to be saved in the file. This method is triggered
     * by commands such as Todo, Event, Deadline.
     *
     * @param task The task to be appended to the save file.
     * @throws FickleException If fails to write to the saveFile.
     */
    public void appendSave(Task task) throws FickleException {
        try {
            FileWriter fw = new FileWriter(filePath, true);
            fw.write(task.toStorageString());
            fw.write(System.lineSeparator());
            fw.close();
        } catch (IOException e) {
            throw new FickleException("Couldn't append tasks to file.");
        }
    }

    /**
     * If the folder does not exist, it will be created. If the folder already
     * exists, it does nothing.
     */
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
        String[] saveStringsParts = line.split(" \\| ");

        if (saveStringsParts.length < 3) {
            throw new FickleException("Insufficient Fields.");
        }

        String taskType = saveStringsParts[0].trim();
        String doneStatus = saveStringsParts[1].trim();
        String name = saveStringsParts[2].trim();

        if (!doneStatus.equals("0") && !doneStatus.equals("1")) {
            throw new FickleException("Invalid Done Status.");
        }

        Task task;
        switch (taskType) {
        case "T":
            task = new Todo(name);
            break;

        case "D":
            if (saveStringsParts.length < 4)
                throw new FickleException("Insufficient Fields for Deadline.");

            LocalDateTime by = parseStorageDateTime(saveStringsParts[3].trim());

            if (by == null) {
                throw new FickleException("Invalid Date/Time Format for Deadline.");
            }

            task = new Deadline(name, by);
            break;

        case "E":
            if (saveStringsParts.length < 5)
                throw new FickleException("Insufficient Fields for Event.");

            LocalDateTime from = parseStorageDateTime(saveStringsParts[3].trim());
            LocalDateTime to = parseStorageDateTime(saveStringsParts[4].trim());

            if (from == null || to == null) {
                throw new FickleException("Invalid Date/Time Format for Event.");
            }

            if (from.isAfter(to)) {
                throw new FickleException("Event start time is after the end time.");
            }

            task = new Event(name, from, to);
            break;

        default:
            throw new FickleException("Unknown Task Type.");

        }

        if (doneStatus.equals("1")) {
            task.markAsDone();
        }
        return task;
    }

    // Parses date/time string (yyyy-MM-dd HHmm). Returns null if invalid.
    private LocalDateTime parseStorageDateTime(String dateTimeString) {
        DateTimeFormatter loadFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        try {
            return LocalDateTime.parse(dateTimeString, loadFormatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
