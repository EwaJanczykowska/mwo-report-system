package pl.edu.agh.mwo.reporter.model.report;

import pl.edu.agh.mwo.reporter.model.Person;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Report1 {
    private final String description = "Raport liczby godzim poswieconych na projekty w rozbiciu na pracownikow";
    private final Map<Person, Integer> hoursPerPerson;

    public Report1() {
        this.hoursPerPerson = new HashMap<>();
    }

    public String getDescription() {
        return description;
    }

    public Map<Person, Integer> getHoursPerPerson() {
        return hoursPerPerson;
    }

    public Set<Person> getPersons() {
        return hoursPerPerson.keySet();
    }

    public Integer getHoursForPerson(Person person) {
        return this.hoursPerPerson.get(person);
    }

    public void addPersonWithTotalNumberOfHours(Person person, Integer totalNumberOfHours) {
        this.hoursPerPerson.put(person, totalNumberOfHours);
    }
}
