package pl.edu.agh.mwo.reporter.model.report;

import java.util.HashMap;
import java.util.Map;

public class Report2 {
    private final String description;
    private final Map<String, Integer> hoursPerProject;

    public Report2(String description) {
        this.description = description;
        this.hoursPerProject = new HashMap<>();
    }

    public String getDescription() {
        return description;
    }

    public Map<String, Integer> getHoursPerProject() {
        return hoursPerProject;
    }

    public Integer getHoursForProject(String projectName) {
        return this.hoursPerProject.get(projectName);
    }

    public void addProjectWithTotalNumberOfHours(String projectName, Integer totalNumberOfHours) {
        this.hoursPerProject.put(projectName, totalNumberOfHours);
    }
}
