package pl.edu.agh.mwo.reporter.loader;

import org.junit.Assert;
import org.junit.Test;
import pl.edu.agh.mwo.reporter.model.Company;
import pl.edu.agh.mwo.reporter.model.Person;

import java.util.List;

public class DataLoaderTest {

    @Test
    public void read_jan_kowalski_data() throws Exception {
        DataLoader dataLoader = new DataLoader();

        Company company = dataLoader.loadData("resources/2012/01/Kowalski_Jan.xls");

        Assert.assertNotNull(company);
        List<Person> persons = company.getPersons();

        Assert.assertEquals(1, persons.size());
        Person person = persons.get(0);

        Assert.assertEquals("Kowalski Jan", person.getName());
        Assert.assertEquals(11, person.getTasks().size());

        System.out.println(company);
    }
}