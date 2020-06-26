package pl.edu.agh.mwo.reporter.report.generator;

import pl.edu.agh.mwo.reporter.model.Company;
import pl.edu.agh.mwo.reporter.model.Person;
import pl.edu.agh.mwo.reporter.model.Task;
import pl.edu.agh.mwo.reporter.model.report.Report1;
import pl.edu.agh.mwo.reporter.model.report.Report2;
import pl.edu.agh.mwo.reporter.model.report.Report3;
import pl.edu.agh.mwo.reporter.model.report.Report4;
import pl.edu.agh.mwo.reporter.model.report.Report5;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class ReportGenerator implements IReportGenerator {

    private final Company company;
    private String employeeName;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private List<String> projectNames;
    private String keyword;

    public ReportGenerator(Company company, String employeeName, LocalDate dateFrom, LocalDate dateTo, String keyword) {
        this.company = company;
        this.employeeName = employeeName;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.projectNames = findProjectNames(company);
        this.keyword = keyword;
    }

    @Override
    public Report1 generateReport1() {
        Report1 report1 = new Report1(employeeName, dateFrom, dateTo);
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
        Report2 report2 = new Report2(employeeName, dateFrom, dateTo);

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

    @Override
    public Report4 generateReport4() {
        Report4 report4 = new Report4(employeeName, dateFrom, dateTo, keyword);
        company.getPersons().stream()
                .map(Person::getTasks)
                .flatMap(Collection::stream)
                .forEach(task -> report4.addTask(task.getName(), task.getProjectName(), task.getHours()));
        return report4;
    }

    @Override
    public Report5 generateReport5() {
        Report5 report5 = new Report5(employeeName, dateFrom, dateTo, keyword);

        for (Person person : company.getPersons()) {
            for (Task task : person.getTasks()) {
                if (task.getName().toLowerCase().contains(keyword.toLowerCase())) {
                    report5.addTask(task);
                }
            }

        }
        return report5;
    }
}
