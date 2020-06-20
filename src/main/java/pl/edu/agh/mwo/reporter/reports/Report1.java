package pl.edu.agh.mwo.reporter.reports;

import pl.edu.agh.mwo.reporter.model.Company;
import pl.edu.agh.mwo.reporter.model.Person;

import java.util.List;

public class Report1 implements ReportGenerator {

    List<Person> personList;

    public void printToConsole(Company company) {
        this.personList = company.getPersons();
    }

    public void printToExcel(Company company) {
        this.personList = company.getPersons();
    }
}
