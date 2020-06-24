package pl.edu.agh.mwo.reporter.loader;

import org.junit.Assert;
import org.junit.Test;
import pl.edu.agh.mwo.reporter.model.Company;
import pl.edu.agh.mwo.reporter.model.Person;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class DataLoaderTest {

    @Test
    public void read_jan_kowalski_data() throws Exception {
        DataLoader dataLoader = new DataLoader();

        List<Path> paths = Arrays.asList(Paths.get("resources/2012/01/Kowalski_Jan.xls"), Paths.get("resources/2012/01/Nowak_Piotr.xls"));

        Company company = dataLoader.loadData(paths, null);

        Assert.assertNotNull(company);
        List<Person> persons = company.getPersons();

        Assert.assertEquals(2, persons.size());

        Person person0 = persons.get(0);
        Assert.assertEquals("Kowalski Jan", person0.getName());
        Assert.assertEquals(11, person0.getTasks().size());

        Person person1 = persons.get(1);
        Assert.assertEquals("Nowak Piotr", person1.getName());
        Assert.assertEquals(10, person1.getTasks().size());

        System.out.println(company);
    }
}