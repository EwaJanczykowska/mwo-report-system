package pl.edu.agh.mwo.reporter.model.report;

import pl.edu.agh.mwo.reporter.model.Person;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Report1 {
    private final String description = "Raport liczby godzin poswieconych na projekty w rozbiciu na pracownikow";
    private final Map<Person, BigDecimal> hoursPerPerson;

    public Report1() {
        this.hoursPerPerson = new HashMap<>();
    }

    public String getDescription() {
        return description;
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
