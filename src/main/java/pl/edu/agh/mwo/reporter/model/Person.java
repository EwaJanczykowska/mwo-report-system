package pl.edu.agh.mwo.reporter.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Person {
    private final String name;
    private final List<Task> tasks;

    public Person(String name) {
        this.name = name;
        this.tasks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void addTasks(Collection<Task> tasks) {
        this.tasks.addAll(tasks);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
