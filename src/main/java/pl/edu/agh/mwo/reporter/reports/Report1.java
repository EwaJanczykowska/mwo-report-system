package pl.edu.agh.mwo.reporter.reports;

import pl.edu.agh.mwo.reporter.model.Company;
import pl.edu.agh.mwo.reporter.model.Person;
import pl.edu.agh.mwo.reporter.model.Task;

import java.util.List;

public class Report1 implements Report {

    private Company company;

    public Report1(Company company) {
        this.company = company;
    }

    public void printToConsole() {
        System.out.printf("%-40s %-15s\n", "Nazwisko imie", "Liczba godzin");

        for (Person person : company.getPersons()) {
            int hours = 0;
            for (Task task : person.getTasks()) {
                hours += task.getHours();
            }
            System.out.printf("%-40s %-15d\n", person.getName(), hours);
        }

    }

    public void printToExcel() {
    }
}
