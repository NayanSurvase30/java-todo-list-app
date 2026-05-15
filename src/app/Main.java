package app;

import java.util.Scanner;
import model.Task;
import service.TodoService;

public class Main {

    public static void printMenu(){

        System.out.println("\n==== TO DO LIST ====");
        System.out.println("1. Add Task");
        System.out.println("2. View Tasks");
        System.out.println("3. Mark Task Completed");
        System.out.println("4. Delete Task");
        System.out.println("5. Exit");
    }

    public static void handleAddTask(TodoService todoService,Scanner sc){
        System.out.print("Enter task title: ");
        String title = sc.nextLine();
        if(title.trim().isEmpty()){
            System.out.println("Task title cannot be empty.");
            return;
        }

        Task task = new Task(
                    todoService.getNextTaskId(),
                    title,
                    false
                    );

        todoService.addTask(task);
        todoService.saveTaskToFile();
        System.out.println("Task added successfully!");
        return;
    }

    public static void handleViewTask(TodoService todoService){
        if (todoService.getAllTasks().isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            System.out.println("\n---- Task List ----");
            for (Task t : todoService.getAllTasks()) {
                String status = t.isCompleted() ? "Completed" : "Pending";
                System.out.println(
                                t.getId() + " | " +
                                t.getTitle() + " | " +
                                status
                            );
                    }
                }
    }

    public static void handleMarkedTaskCompleted(TodoService todoService,Scanner sc){
        if (todoService.getAllTasks().isEmpty()) {
                        System.out.println("No tasks to mark.");
                        return;
                    }
                    handleViewTask(todoService);
                    

                    System.out.print("\nEnter task ID to mark completed: ");
                    int completeId = sc.nextInt();
                    sc.nextLine();

                     Task task = todoService.getTaskById(completeId);
                    if (task != null && todoService.markTaskCompletedById(completeId)) {
                        todoService.saveTaskToFile();
                        System.out.println("Task '" + task.getTitle() + "' marked as completed!");
                    } else {
                        System.out.println("Task ID not found.");
                    }
    }

    public static void handleDeleteTask(TodoService todoService,Scanner sc){
        if (todoService.getAllTasks().isEmpty()) {
                        System.out.println("No tasks to delete.");
                        return;
                    }

                    handleViewTask(todoService);

                    System.out.print("Enter task ID to delete: ");
                    int deleteId = sc.nextInt();
                    sc.nextLine();

                    Task taskToDelete = todoService.getTaskById(deleteId);
                    if (taskToDelete != null && todoService.removeTaskById(deleteId)) {
                        todoService.saveTaskToFile();
                        System.out.println("Task '" + taskToDelete.getTitle() + "' deleted successfully!");
                    } else {
                        System.out.println("Task ID not found.");
                    } 
    }

    public static void main(String[] args) {

        TodoService todoService = new TodoService();
        todoService.loadTaskFromFile();   // Load saved tasks at start

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n============================\n");
            printMenu();

            System.out.print("\nEnter your choice: ");
            if(!sc.hasNextInt()){
                System.out.println("Please enter a number: ");
                sc.nextLine();
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine(); // newline
            if(choice < 1 || choice > 5){
                System.out.println("Select valid option '1-5': ");
                continue;
            }

            switch (choice) {

                //ADD TASK
                case 1:
                    handleAddTask(todoService, sc);
                    break;

                // VIEW TASKS
                case 2:
                    handleViewTask(todoService);
                    break;

                //  MARK COMPLETED (BY ID)
                case 3:
                    handleMarkedTaskCompleted(todoService, sc);
                    break;

                // DELETE TASK (BY ID)
                case 4:
                    handleDeleteTask(todoService, sc);
                    break;

                // EXIT & SAVE
                case 5:
                    todoService.saveTaskToFile();
                    System.out.println("Tasks saved. Exiting application...");
                    running = false;
                    break;

                // INVALID INPUT
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        sc.close();
    }
}