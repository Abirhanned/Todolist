import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class Todolist {
    private String id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;

    public Todolist(String title, String description) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = "todo";
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getStatus() { return status; }

    public void setStatus(String status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    // Add a new task
    public static void addTask(List<Todolist> tasks, String title, String description) {
        Todolist newTask = new Todolist(title, description);
        tasks.add(newTask);
        saveToFile(tasks);
        System.out.println("Task added successfully (ID: " + newTask.getId() + ")");
    }

    // List tasks based on status
    public static void listTasks(List<Todolist> tasks, String status) {
        boolean found = false;
        System.out.println("\n--- Task List (" + status.toUpperCase() + ") ---");
        for (Todolist task : tasks) {
            if (task.getStatus().equalsIgnoreCase(status) || status.equalsIgnoreCase("all")) {
                System.out.println("ID: " + task.getId() +
                        "\nTitle: " + task.getTitle() +
                        "\nDescription: " + task.getDescription() +
                        "\nStatus: " + task.getStatus() +
                        "\nCreated At: " + task.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                        "\nUpdated At: " + task.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                        "\n----------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No tasks found with the status: " + status);
        }
    }

    // Update task description without resetting status
    public static void updateTask(List<Todolist> tasks, String id, String newDescription) {
        for (Todolist task : tasks) {
            if (task.getId().equals(id)) {
                task.description = newDescription;
                task.updatedAt = LocalDateTime.now();
                saveToFile(tasks);
                System.out.println("Task updated successfully.");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    // Delete a task
    public static void deleteTask(List<Todolist> tasks, String id) {
        boolean removed = tasks.removeIf(task -> task.getId().equals(id));
        saveToFile(tasks);
        if (removed) {
            System.out.println("Task deleted successfully.");
        } else {
            System.out.println("Task not found.");
        }
    }

    // Mark task as done
    public static void markDone(List<Todolist> tasks, String id) {
        updateStatus(tasks, id, "done");
    }

    // Mark task as in progress
    public static void markInProgress(List<Todolist> tasks, String id) {
        updateStatus(tasks, id, "in-progress");
    }

    // Mark task as not done
    public static void markNotDone(List<Todolist> tasks, String id) {
        updateStatus(tasks, id, "todo");
    }

    // Helper function to update task status
    private static void updateStatus(List<Todolist> tasks, String id, String status) {
        for (Todolist task : tasks) {
            if (task.getId().equals(id)) {
                task.setStatus(status);
                saveToFile(tasks);
                System.out.println("Task marked as " + status + ".");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    // Save tasks to JSON file using Gson
    private static void saveToFile(List<Todolist> tasks) {
        try (FileWriter file = new FileWriter("tasks.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            file.write(gson.toJson(tasks));
        } catch (IOException e) {
            System.out.println("Error saving tasks.");
            e.printStackTrace();
        }
    }

    // Load tasks from JSON file
    public static List<Todolist> loadFromFile() {
        List<Todolist> tasks = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get("tasks.json")));
            if (!content.trim().isEmpty()) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Todolist>>() {}.getType();
                tasks = gson.fromJson(content, listType);
            }
        } catch (IOException e) {
            System.out.println("No existing task file found, starting fresh.");
        }
        return tasks;
    }

    public static void main(String[] args) {
        List<Todolist> tasks = loadFromFile();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nCommands: add, update, delete, mark-done, mark-in-progress, mark-not-done, list, exit");
            System.out.print("Enter command: ");
            String command = scanner.nextLine().trim();

            switch (command) {
                case "add":
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();
                    addTask(tasks, title, description);
                    break;

                case "update":
                    System.out.print("Enter Task ID: ");
                    String updateId = scanner.nextLine();
                    System.out.print("Enter new description: ");
                    String newDesc = scanner.nextLine();
                    updateTask(tasks, updateId, newDesc);
                    break;

                case "delete":
                    System.out.print("Enter Task ID: ");
                    String deleteId = scanner.nextLine();
                    deleteTask(tasks, deleteId);
                    break;

                case "mark-done":
                    System.out.print("Enter Task ID: ");
                    String doneId = scanner.nextLine();
                    markDone(tasks, doneId);
                    break;

                case "mark-in-progress":
                    System.out.print("Enter Task ID: ");
                    String progressId = scanner.nextLine();
                    markInProgress(tasks, progressId);
                    break;

                case "mark-not-done":
                    System.out.print("Enter Task ID: ");
                    String notDoneId = scanner.nextLine();
                    markNotDone(tasks, notDoneId);
                    break;

                case "list":
                    System.out.print("Enter status (all/todo/in-progress/done): ");
                    String status = scanner.nextLine();
                    listTasks(tasks, status);
                    break;

                case "exit":
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid command. Try again.");
            }
        }
    }
}
