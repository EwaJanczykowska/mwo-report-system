package pl.edu.agh.mwo.reporter.model.report;

import pl.edu.agh.mwo.reporter.model.Task;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Report5 {

    private final String title = "Raport 5. Wykaz zadań wg podanej frazy ";

    private final List<Task>  tasks;
    private final String employeeName;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;
    private final String keyword;

    public Report5( String employeeName, LocalDate dateFrom, LocalDate dateTo, String keyword) {
        this.tasks = new ArrayList<Task>();
        this.employeeName = employeeName;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.keyword = keyword;
    }

    public List<Task> addTask(Task zadanie){
       this.tasks.add(zadanie);
       return tasks;
    }


    public String getTitle() {
        return title;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public String getKeyword() {
        return keyword;
    }

    @Override
    public String toString() {
        return "Report5{" +
                "title='" + title + '\'' +
                ", tasks=" + tasks +
                ", employeeName='" + employeeName + '\'' +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}
