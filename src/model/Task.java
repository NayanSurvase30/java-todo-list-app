package model;

public class Task {
    private int id;
    private String title;
    private boolean completed;

    public Task(int id, String title, boolean completed) {
        this.id = id;
        this.title = title;
        this.completed = completed; //New task start as pending
    }

    public int getId() {
        return id;
    }
    public String getTitle(){
        return title;
    }
    public boolean isCompleted(){
        return completed;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setCompleted(boolean completed){
        this.completed = completed;
    }
    public void markCompleted(){
        this.completed = true;
    }
}