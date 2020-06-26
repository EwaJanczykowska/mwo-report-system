package pl.edu.agh.mwo.reporter.model.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Report4 {

    private String title = "Raport 4. Top 20 zadań według spędzonego czasu";
    private List<Record> records;

    public Report4() {
        this.records = new ArrayList<>();
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
        private String taskName;
        private String projectName;
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
