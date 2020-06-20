package pl.edu.agh.mwo.reporter.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Company {
    private final List<Person> persons;

    public Company() {
        persons = new ArrayList<>();
    }

    public List<Person> getPersons() {
        return persons;
    }

    void addPerson(Person person) {
        this.persons.add(person);
    }

    void addPersons(Collection<Person> persons) {
        this.persons.addAll(persons);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return persons.equals(company.persons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons);
    }
}
