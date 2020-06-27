package pl.edu.agh.mwo.reporter.model.report;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Report2 {
    private final String title = "Raport 2. Liczba godzin zrealizowanych w projektach";
    private final Map<String, BigDecimal> hoursPerProject;
    private final String employeeName;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;


    public Report2(String employeeName, LocalDate dateFrom, LocalDate dateTo) {
        this.hoursPerProject = new HashMap<>();
        this.employeeName = employeeName;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public String getKeyword() {
        return null;
    }

    public String getTitle() {
        return title;
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

    public Map<String, BigDecimal> getHoursPerProject() {
        return hoursPerProject;
    }

    public BigDecimal getHoursForProject(String projectName) {
        return this.hoursPerProject.get(projectName);
    }

    public void addProjectWithTotalNumberOfHours(String projectName, BigDecimal totalNumberOfHours) {
        this.hoursPerProject.put(projectName, totalNumberOfHours);
    }
}
