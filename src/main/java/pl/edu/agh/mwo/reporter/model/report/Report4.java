package pl.edu.agh.mwo.reporter.model.report;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Report4 {

    private final String title = "Raport 4. Top 20 najbardziej czasochłonnych zadań ";
    private final List<Record> records;
    private final String employeeName;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;
    private final String keyword;

    public Report4(String employeeName, LocalDate dateFrom, LocalDate dateTo, String keyword) {
        this.records = new ArrayList<>();
        this.employeeName = employeeName;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.keyword = keyword;
    }

    public void addTask(String taskName, String projectName, BigDecimal hours) {
        Optional<Record> record = records.stream()
                .filter(r -> r.getProjectName().equals(projectName) && r.getTaskName().equals(taskName))
                .findAny();

        if (record.isPresent()) {
            record.get().addHours(hours);
        } else {
            Record newRecord = new Record(taskName, projectName);
            newRecord.addHours(hours);
            records.add(newRecord);
        }
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

    public String getTitle() {
        return title;
    }

    public List<Record> getTop20() {
        return records.stream()
                .sorted(Comparator.comparing(Record::getHours).reversed())
                .limit(20)
                .collect(Collectors.toList());
    }

    public static class Record {
        private final String taskName;
        private final String projectName;
        private BigDecimal hours;

        public Record(
                String taskName,
                String projectName) {
            this.taskName = taskName;
            this.projectName = projectName;
            this.hours = BigDecimal.ZERO;
        }

        public void addHours(BigDecimal hours) {
            this.hours = this.hours.add(hours);
        }

        public String getTaskName() {
            return taskName;
        }

        public String getProjectName() {
            return projectName;
        }

        public BigDecimal getHours() {
            return hours;
        }
    }
}
