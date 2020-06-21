package pl.edu.agh.mwo.reporter.report.generator;

import pl.edu.agh.mwo.reporter.model.Company;
import pl.edu.agh.mwo.reporter.model.Person;
import pl.edu.agh.mwo.reporter.model.Task;
import pl.edu.agh.mwo.reporter.model.report.Report1;
import pl.edu.agh.mwo.reporter.model.report.Report2;

import java.math.BigDecimal;

public class ReportGenerator implements IReportGenerator {

    private final Company company;

    public ReportGenerator(Company company) {
        this.company = company;
    }

    public Report1 generateReport1() {
        Report1 report1 = new Report1();
        for (Person person : company.getPersons()) {
            BigDecimal hours = BigDecimal.ZERO;
            for (Task task : person.getTasks()) {
                hours = hours.add(task.getHours());
            }
            report1.addPersonWithTotalNumberOfHours(person, hours);
        }
        return report1;
    }

    public Report2 generateReport2() {
        throw new IllegalStateException("not implemented yet!");
    }
}
