package pl.edu.agh.mwo.reporter.report.generator;

import pl.edu.agh.mwo.reporter.model.Company;
import pl.edu.agh.mwo.reporter.model.Person;
import pl.edu.agh.mwo.reporter.model.Task;
import pl.edu.agh.mwo.reporter.model.report.Report1;
import pl.edu.agh.mwo.reporter.model.report.Report2;
import pl.edu.agh.mwo.reporter.model.report.Report3;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ReportGenerator implements IReportGenerator {

    private final Company company;
    private String employeeName;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private List<String> projectNames;

    public ReportGenerator(Company company, String employeeName, LocalDate dateFrom, LocalDate dateTo) {
        this.company = company;
        this.employeeName = employeeName;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.projectNames = findProjectNames(company);
    }

    @Override
    public Report1 generateReport1() {
        Report1 report1 = new Report1(employeeName,dateFrom, dateTo);
        for (Person person : company.getPersons()) {
            BigDecimal hours = BigDecimal.ZERO;
            for (Task task : person.getTasks()) {
                hours = hours.add(task.getHours());
            }
            report1.addPersonWithTotalNumberOfHours(person, hours);
        }
        return report1;
    }

    @Override
    public Report2 generateReport2() {
        Report2 report2 = new Report2(employeeName,dateFrom, dateTo);

        for (String projectName : projectNames) {
            BigDecimal hours = BigDecimal.ZERO;
            for (Person person : company.getPersons()) {
                for (Task task : person.getTasks()) {
                    if (task.getProjectName().equals(projectName)) {
                        hours = hours.add(task.getHours());
                    }
                }
            }
            report2.addProjectWithTotalNumberOfHours(projectName, hours);
        }

        return report2;
    }

    @Override
    public Report3 generateReport3() {
        Report3 report3 = new Report3(projectNames);
        for (Person person : company.getPersons()) {
            for (Task task : person.getTasks()) {
                BigDecimal hours = task.getHours();
                String projectName = task.getProjectName();
                report3.addHoursForPersonByProject(projectName, person.getName(), hours);
            }
        }
        return report3;
    }

    private static List<String> findProjectNames(Company company) {
        return company.getPersons().stream()
                .map(Person::getTasks)
                .flatMap(Collection::stream)
                .map(Task::getProjectName)
                .distinct()
                .collect(Collectors.toList());
    }
}
