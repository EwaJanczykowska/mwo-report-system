package pl.edu.agh.mwo.reporter.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Company {
    private final List<Person> persons;

    public Company() {
        persons = new ArrayList<>();
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void addPerson(Person person) {
        this.persons.add(person);
    }

    public void addPersons(Collection<Person> persons) {
        this.persons.addAll(persons);
    }
}
