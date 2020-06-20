package pl.edu.agh.mwo.reporter.reports;

import pl.edu.agh.mwo.reporter.model.Company;
import pl.edu.agh.mwo.reporter.model.Person;
import pl.edu.agh.mwo.reporter.model.Task;

import java.util.List;

public class Report1 implements ReportGenerator {

    List<Person> personList;

    public void printToConsole(Company company) {
        this.personList = company.getPersons();

        System.out.printf("%-40s %-15s\n", "Imie nazwisko", "Liczba godzin");

        for (Person person : personList) {
            int hours = 0;
            for (Task task : person.getTasks()) {
                hours += task.getHours();
            }
            System.out.printf("%-40s %-15d\n", person.getName(), hours);
        }

    }

    public void printToExcel(Company company) {
        this.personList = company.getPersons();
    }
}
