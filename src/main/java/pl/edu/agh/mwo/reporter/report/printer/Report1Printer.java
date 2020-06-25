package pl.edu.agh.mwo.reporter.report.printer;

import org.openxmlformats.schemas.spreadsheetml.x2006.main.STSourceType;
import pl.edu.agh.mwo.reporter.model.Person;
import pl.edu.agh.mwo.reporter.model.report.Report1;

import java.sql.SQLOutput;

public class Report1Printer implements IReportPrinter {

    private static final String[] HEADERS = {"Nazwisko i imie", "Liczba godzin"};

    private static final int[] COLUMNS_WIDTHS = {20, 20};

    private final Report1 report;

    public Report1Printer(Report1 report1) {
        this.report = report1;
    }

    public void printToConsole() {
        System.out.println("\n");
        if (report.getEmployeeName() !=null){
            System.out.println("Raport godzin projektowych dla: " +report.getEmployeeName());
        } else {
            System.out.println(report.getTitle());
        }
        if (report.getDateFrom() !=null) {
            System.out.println("Dane od: " + report.getDateFrom() + " do: "+report.getDateTo());
        }
        System.out.println("--------------------------------------------------------------");
        System.out.printf("|  %-40s | %-15s|\n", HEADERS[0], HEADERS[1]);
        System.out.println("--------------------------------------------------------------");

        report.getHoursPerPerson().forEach((person, hours) -> {
            System.out.printf("|  %-40s | %-15s|\n", person.getName(), hours);
        });
        System.out.println("--------------------------------------------------------------");
    }

    public void printToExcel(String excelFilePath) {
        ExcelExporter excelExporter = new ExcelExporter(excelFilePath, "report1", report.getTitle(),
                HEADERS, report.getEmployeeName(), report.getDateFrom(), report.getDateTo());

        excelExporter.setColumnsWidths(COLUMNS_WIDTHS);

        for (Person person : report.getPersons()) {
            excelExporter.addRow();
            excelExporter.addCell(0, person.getName());
            excelExporter.addCell(1, report.getHoursForPerson(person));
        }

        excelExporter.saveToFile();
    }
}
