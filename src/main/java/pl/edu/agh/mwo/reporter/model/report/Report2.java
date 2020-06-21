package pl.edu.agh.mwo.reporter.model.report;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Report2 {
    private final String description;
    private final Map<String, BigDecimal> hoursPerProject;

    public Report2(String description) {
        this.description = description;
        this.hoursPerProject = new HashMap<>();
    }

    public String getDescription() {
        return description;
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
