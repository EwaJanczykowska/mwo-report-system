package pl.edu.agh.mwo.reporter.model.report;

import pl.edu.agh.mwo.reporter.model.Person;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Report1 {
    private final String title = "Raport godzin poswieconych przez pracowników na realizację projektów.";
    private final Map<Person, BigDecimal> hoursPerPerson;
    private String employeeName;
    private LocalDate dateFrom;
    private LocalDate dateTo;


    public Report1(String employeeName, LocalDate dateFrom, LocalDate dateTo) {
        this.hoursPerPerson = new HashMap<>();
        this.employeeName = employeeName;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public String getTitle() {
        return title;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public Map<Person, BigDecimal> getHoursPerPerson() {
        return hoursPerPerson;
    }

    public Set<Person> getPersons() {
        return hoursPerPerson.keySet();
    }

    public BigDecimal getHoursForPerson(Person person) {
        return this.hoursPerPerson.get(person);
    }

    public void addPersonWithTotalNumberOfHours(Person person, BigDecimal totalNumberOfHours) {
        this.hoursPerPerson.put(person, totalNumberOfHours);
    }
}
