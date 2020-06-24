package pl.edu.agh.mwo.reporter.loader;

import org.apache.poi.ss.usermodel.*;
import pl.edu.agh.mwo.reporter.model.Company;
import pl.edu.agh.mwo.reporter.model.Person;
import pl.edu.agh.mwo.reporter.model.Task;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataLoader {

    public Company loadData(List<Path> paths, String filterByEmployee) throws IOException {
        Company company = new Company();
        Person filterPerson = null;


        for (Path path : paths) {
            File file = path.toFile();
            Workbook workbook = WorkbookFactory.create(file);

            String fileName = file.getName();
            String personName = fileName.replace(".xls", "").replace("_", " ");


            List<Task> tasks = readPersonTasks(workbook);
            if (filterByEmployee == null) {
                Person person = company.getPersonByName(personName);
                if (person == null) {
                    person = new Person(personName);
                    company.addPerson(person);
                }
                person.addTasks(tasks);
            } else {
                filterByEmployee = filterByEmployee.replace("_", " ");
                if (personName.toLowerCase().contains(filterByEmployee)) {
                    if (filterPerson == null) {
                        filterPerson = new Person(personName);
                        company.addPerson(filterPerson);
                    }
                    filterPerson.addTasks(tasks);
                }

            }
        }

        return company;
    }

    private List<Task> readPersonTasks(Workbook workbook) {
        List<Task> tasks = new ArrayList<>();

        for (Sheet sheet : workbook) {
            String projectName = sheet.getSheetName();
            boolean isError = false;
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                isError = false;
                Row row = sheet.getRow(i);
                Cell dateCell = row.getCell(0);
                Date taskDate = null;
                try {
                    if (dateCell == null || dateCell.getDateCellValue() == null) {
                        System.out.println("Pusta komorka w: " + (i + 1) + "A");
                        isError = true;
                    } else {
                        taskDate = dateCell.getDateCellValue();
                    }
                } catch (IllegalStateException e) {
                    System.out.println("Nieprawidlowa data w komorce :" + (i + 1) + "A");
                    isError = true;
                }

                Cell taskCell = row.getCell(1);
                String taskName = "";
                try {
                    if (taskCell == null || taskCell.getStringCellValue() == null) {
                        System.out.println("Pusta komorka w: " + (i + 1) + "B");
                        isError = true;
                    } else {
                        taskName = taskCell.getStringCellValue();
                    }
                } catch (IllegalStateException e) {
                    System.out.println("Komorka " + (i + 1) + "B" + " nie zawiera prawdidlowej wartosci tekstowej");
                    isError = true;
                }

                Cell hoursCell = row.getCell(2);
                BigDecimal taskHours = BigDecimal.ZERO;
                if (hoursCell == null) {
                    System.out.println("Pusta komorka w: " + (i + 1) + "C");
                    isError = true;
                } else {
                    try {
                        taskHours = BigDecimal.valueOf(hoursCell.getNumericCellValue());
                    } catch (IllegalStateException e) {
                        System.out.println("Wartosc nienumeryczna w komorce : " + (i + 1) + "C");
                        isError = true;
                    }
                }
                if (isError) {
                    System.out.println("Wiersz " + (i + 1) + " nie uwzgledniony w raporcie");
                    continue;
                }
                Task task = new Task(taskName, convertToLocalDate(taskDate), taskHours, projectName);
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
