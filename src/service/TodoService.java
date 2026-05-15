package service;
import model.Task;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



public class TodoService {
    private ArrayList<Task> tasks = new ArrayList<>(); 
    private int taskIdCounter = 1;


    public void addTask(Task task){
        tasks.add(task);
    }

    public boolean removeTaskById(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                tasks.remove(i);
                return true;
            }
        }
        return false;
    }

    public Task getTaskById(int id){
        for (Task t : tasks) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    public ArrayList<Task> getAllTasks(){
        return tasks;
    }

    public boolean markTaskCompletedById(int id) {
        Task t = getTaskById(id);
        if (t != null) {
            t.setCompleted(true);
            return true;
        }
        return false;
    }

    public int getNextTaskId(){
        return taskIdCounter++;
    }

    public void saveTaskToFile(){

        // Create data folder if it doesn't exist
        File dir = new File("data");
        if(!dir.exists()){
            dir.mkdir();
        }
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("data/tasks.txt"));
            for(Task task : tasks){
                String line = task.getId() + " | " + task.getTitle() + " | " + task.isCompleted();
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        }
        catch(IOException e){
            System.out.println("Error saving task to the fil:" + e.getMessage());
        }
    }

    public void loadTaskFromFile(){
        File file = new File("data/tasks.txt");
        if(!file.exists()){
            return;
        }
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            int maxId = 0;
            while((line = reader.readLine()) != null){
                String[] parts = line.split("\\s*\\|\\s*");
                int id = Integer.parseInt(parts[0]);
                String title = parts[1];
                boolean completed = Boolean.parseBoolean(parts[2]);

                Task task = new Task(id, title, completed);
                tasks.add(task);

                for (Task t : tasks) {
                    if (t.getId() > maxId) {
                        maxId = t.getId();
                    }
                }
                taskIdCounter = maxId + 1;

            }
        }
        catch(Exception e){
            System.out.println("Error loading tasks from file.");
        }
    }

}   