package pl.edu.agh.mwo.reporter.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Company {
    private final List<Person> persons;

    private LocalDate startDate;

    private LocalDate endDate;

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

    public Person getPersonByName(String personName) {
        for (Person person: persons) {
            if (person.getName().equals(personName)) {
                return person;
            }
        }
        return null;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(persons, company.persons) &&
                Objects.equals(startDate, company.startDate) &&
                Objects.equals(endDate, company.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, startDate, endDate);
    }

    @Override
    public String toString() {
        return "Company{" +
                "persons=" + persons +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
