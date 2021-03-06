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

    public Company loadData(List<Path> paths, LocalDate dateFrom, LocalDate dateTo, String filterByEmployee) throws IOException {
        Company company = new Company();

        LocalDate startDate = null;
        LocalDate endDate = null;

        for (Path path : paths) {
            File file = path.toFile();
            Workbook workbook = null;

            try {
                workbook = WorkbookFactory.create(file);
            } catch (IOException e) {
                System.out.println("Błąd przy otwieraniu pliku: " + e.getMessage());
                System.exit(1);
            }

            String fileName = file.getName();
            String personName = fileName.replace(".xls", "").replace("_", " ");
            filterByEmployee = (filterByEmployee == null) ? filterByEmployee : filterByEmployee.replace("_", " ").toLowerCase();

            if (filterByEmployee != null && !personName.toLowerCase().contains(filterByEmployee)) {
                continue;
            }

            List<Task> tasks = readPersonTasks(path, workbook, dateFrom, dateTo);

            if (tasks.isEmpty()) {
                continue;
            }

            for (Task task : tasks) {
                LocalDate taskDate = task.getDate();
                if (startDate == null || taskDate.isBefore(startDate)) {
                    startDate = taskDate;
                }
                if (endDate == null || taskDate.isAfter(endDate)) {
                    endDate = taskDate;
                }
            }

            Person person = company.getPersonByName(personName);
            if (person == null) {
                person = new Person(personName);
                company.addPerson(person);
            }
            person.addTasks(tasks);
        }

        company.setStartDate(startDate);
        company.setEndDate(endDate);

        return company;
    }

    private List<Task> readPersonTasks(Path path, Workbook workbook, LocalDate dateFrom, LocalDate dateTo) {
        List<Task> tasks = new ArrayList<>();

        for (Sheet sheet : workbook) {
            String projectName = sheet.getSheetName();
            boolean isError = false;
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                isError = false;
                Row row = sheet.getRow(i);

                if (isRowEmpty(row)) {
                    continue;
                }

                Cell dateCell = row.getCell(0);
                Date taskDate = null;


                try {
                    if (dateCell == null || dateCell.getDateCellValue() == null) {
                        System.out.println("Pusta komorka w: " + (i + 1) + "A (skoroszyt:" + projectName+") w pliku: " + path);
                        isError = true;
                    } else {
                        taskDate = dateCell.getDateCellValue();
                    }
                } catch (IllegalStateException e) {
                    System.out.println("Nieprawidlowa data w komorce :" + (i + 1) + "A (skoroszyt:" + projectName+") w pliku: " + path);
                    isError = true;
                }

                Cell taskCell = row.getCell(1);
                String taskName = "";
                try {
                    if (taskCell == null || taskCell.getStringCellValue() == null) {
                        System.out.println("Pusta komorka w: " + (i + 1) + "B (skoroszyt:" + projectName+") w pliku: " + path);
                        isError = true;
                    } else {
                        taskName = taskCell.getStringCellValue();
                    }
                } catch (IllegalStateException e) {
                    System.out.println("Komorka " + (i + 1) + "B (skoroszyt:" + projectName + ") nie zawiera prawdidlowej wartosci tekstowej w pliku: " + path);
                    isError = true;
                }

                Cell hoursCell = row.getCell(2);
                BigDecimal taskHours = BigDecimal.ZERO;
                if (hoursCell == null) {
                    System.out.println("Pusta komorka w: " + (i + 1) + "C (skoroszyt:" + sheet.getSheetName()+") w pliku: " + path);
                    isError = true;
                } else {
                    try {
                        taskHours = BigDecimal.valueOf(hoursCell.getNumericCellValue());
                    } catch (IllegalStateException e) {
                        System.out.println("Wartosc nienumeryczna w komorce : " + (i + 1) + "C (skoroszyt:" + sheet.getSheetName()+") w pliku: " + path);
                        isError = true;
                    }
                }
                if (isError) {
                    System.out.println("Wiersz " + (i + 1) + " nie uwzgledniony w raporcie");
                    continue;
                }


                LocalDate localDate = convertToLocalDate(taskDate);
                if (dateFrom != null && localDate.isBefore(dateFrom)) {
                    continue;
                }
                if (dateTo != null && localDate.isAfter(dateTo)) {
                    continue;
                }

                Task task = new Task(taskName, localDate, taskHours, projectName);
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

    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        if (row.getLastCellNum() <= 0) {
            return true;
        }
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK && cell.toString() != null) {
                return false;
            }
        }
        return true;
    }

}
