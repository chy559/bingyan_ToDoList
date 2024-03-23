package com.example.todolist.tasks;

public class TaskItem {
    private long TaskId;
    private String title;
    private String time;
    private int tag;
    public TaskItem(){
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public long getTaskId() {
        return TaskId;
    }
    public void setTaskId(long taskId) {
        TaskId = taskId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public TaskItem(String title,String time,int tag){
        this.time=time;
        this.title=title;
        this.tag=tag;
    }
    @Override
    public String toString(){
        return title+"\n"+ time.substring(5,16)+" "+TaskId;
    }
}
