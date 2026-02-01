package fickle.storage;

import fickle.exceptions.FickleException;
import fickle.tasks.Task;
import fickle.tasks.Todo;
import fickle.tasks.Deadline;
import fickle.tasks.Event;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;

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
     * @return An ArrayList of Task loaded from the save file. An empty ArrayList if
     *         the file does not exist
     * 
     * @throws FickleException If the file exists but cannot be read.
     */
    public ArrayList<Task> load() throws FickleException {
        checkAndCreateFolder();

        File file = new File(filePath);
        ArrayList<Task> tasks = new ArrayList<>();

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

                Task task = parseTask(line);
                if (task != null) {
                    tasks.add(task);
                } else {
                    System.out.println("Warning! Corrupted line skipped: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            throw new FickleException("Couldn't load tasks from file.", "What, Where");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return tasks;
    }

    /**
     * Overwrites the save file with the list of tasks.
     * This method is triggered by commands such as Mark, Unmark, Delete.
     *
     * @param tasks The list of tasks to be saved.
     * @throws FickleException If fails to write to the saveFile.
     */
    public void overwriteSave(ArrayList<Task> tasks) throws FickleException {
        try {
            FileWriter fw = new FileWriter(filePath);
            for (Task task : tasks) {
                fw.write(task.toStorageString());
                fw.write(System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            throw new FickleException("Couldn't save tasks to file.", "Anything Goes");
        }
    }

    /**
     * Appends a single task to be saved in the file.
     * This method is triggered by commands such as Todo, Event, Deadline.
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
            throw new FickleException("Couldn't append tasks to file.", "Anything Goes");
        }
    }

    /**
     * If the folder does not exist, it will be created.
     * If the folder already exists, it does nothing.
     */
    private void checkAndCreateFolder() {
        File file = new File(filePath);
        File parent = file.getParentFile();

        if (parent != null) {
            parent.mkdirs();
        }
    }

    private Task parseTask(String line) {
        String[] saveStringsParts = line.split(" \\| ");

        // Corrupted line due to insufficient information
        if (saveStringsParts.length < 3)
            return null;

        String taskType = saveStringsParts[0];
        String doneStatus = saveStringsParts[1];
        String name = saveStringsParts[2];

        // Corrupted line due to invalid doneStatus
        if (!doneStatus.equals("0") && !doneStatus.equals("1")) {
            return null;
        }

        Task task = null;
        // Corrupted line due to insufficient timing information
        switch (taskType) {
            case "T":
                task = new Todo(name);
                break;
            case "D":
                if (saveStringsParts.length < 4)
                    return null;
                task = new Deadline(name, saveStringsParts[3]);
                break;
            case "E":
                if (saveStringsParts.length < 5)
                    return null;
                task = new Event(name, saveStringsParts[3], saveStringsParts[4]);
                break;
            default:
                return null;
        }

        if (doneStatus.equals("1")) {
            task.markAsDone();
        }
        return task;
    }
}