package pl.edu.agh.mwo.reporter.loader;

import org.apache.poi.ss.usermodel.*;
import pl.edu.agh.mwo.reporter.model.Company;
import pl.edu.agh.mwo.reporter.model.Person;
import pl.edu.agh.mwo.reporter.model.Task;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataLoader {

    public Company loadData(String path) throws IOException {
        File file = new File(path);

        Workbook workbook = WorkbookFactory.create(file);

        String fileName = file.getName();
        String personName = fileName.replace(".xls","").replace("_", " ");

        List<Task> tasks = readPersonTasks(workbook);

        Person person = new Person(personName);
        person.addTasks(tasks);

        Company company = new Company();
        company.addPerson(person);
        return company;
    }

    private List<Task> readPersonTasks(Workbook workbook) {
        List<Task> tasks = new ArrayList<>();

        for (Sheet sheet: workbook) {
            String projectName = sheet.getSheetName();

            for (int i=1; i<sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                Cell dateCell = row.getCell(0);
                Date taskDate = dateCell.getDateCellValue();

                Cell taskCell = row.getCell(1);
                String taskName = taskCell.getStringCellValue();

                Cell hoursCell = row.getCell(2);
                int taskHours = (int) hoursCell.getNumericCellValue();

                Task task = new Task(taskName, convertToLocalDate(taskDate),taskHours, projectName);
                tasks.add(task);
            }

        }

        return tasks;
    }

    private LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

}
