package pl.edu.agh.mwo.reporter.model;

import java.time.LocalDate;

public class Task {
    private final String name;
    private final LocalDate date;
    private final int hours;
    private final String projectName;

    public Task(String name, LocalDate date, int hours, String projectName) {
        this.name = name;
        this.date = date;
        this.hours = hours;
        this.projectName = projectName;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getHours() {
        return hours;
    }

    public String getProjectName() {
        return projectName;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", hours=" + hours +
                ", projectName='" + projectName + '\'' +
                '}';
    }
}
