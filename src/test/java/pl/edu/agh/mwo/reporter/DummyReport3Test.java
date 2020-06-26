package pl.edu.agh.mwo.reporter;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import pl.edu.agh.mwo.reporter.model.Company;
import pl.edu.agh.mwo.reporter.model.Person;
import pl.edu.agh.mwo.reporter.model.Task;
import pl.edu.agh.mwo.reporter.model.report.Report3;
import pl.edu.agh.mwo.reporter.report.generator.ReportGenerator;
import pl.edu.agh.mwo.reporter.report.printer.Report3Printer;

public class DummyReport3Test {

    static SecureRandom random = new SecureRandom();

    @Test
    public void should_generate_and_print_report_3() throws Exception {
        Company company = companyFixture();

        ReportGenerator generator = new ReportGenerator(company, "", LocalDate.now(), LocalDate.now(), null);

        Report3 report3 = generator.generateReport3();

        new Report3Printer(report3).printToConsole();
    }

    private static Company companyFixture() {
        final Company company = new Company();
        company.addPersons(personsFixture());
        return company;
    }

    private static Collection<Person> personsFixture() {
        List<Person> persons = new ArrayList<>();
        for (int personNumber = 0; personNumber < 3; personNumber++) {
            persons.add(personFixture(personNumber));
        }
        return persons;
    }

    private static Person personFixture(int personNumber) {
        final Person person = new Person("person name " + personNumber);
        person.addTasks(tasksFixture(personNumber));
        return person;
    }

    private static Collection<Task> tasksFixture(int personNumber) {
        List<Task> tasks = new ArrayList<>();
        for (int taskNumber = 0; taskNumber < 3; taskNumber++) {
            tasks.add(taskFixture(personNumber, taskNumber));
        }

        return tasks;
    }

    private static Task taskFixture(int personNumber, int taskNumber) {
        return new Task(String.format("task name %s %s", personNumber, taskNumber), LocalDate.now(), BigDecimal.valueOf(random.nextInt(10)), "Project " + random.nextInt(3));
    }
}
