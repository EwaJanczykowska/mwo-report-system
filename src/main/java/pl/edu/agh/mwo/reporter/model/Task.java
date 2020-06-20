package pl.edu.agh.mwo.reporter.model;

import java.time.LocalDate;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return hours == task.hours &&
                name.equals(task.name) &&
                date.equals(task.date) &&
                projectName.equals(task.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, hours, projectName);
    }
}
