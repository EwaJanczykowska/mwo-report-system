package pl.edu.agh.mwo.reporter.report.printer;

import pl.edu.agh.mwo.reporter.model.Person;
import pl.edu.agh.mwo.reporter.model.report.Report1;

public class Report1Printer implements IReportPrinter {

    private static final String[] HEADERS = {"Nazwisko i imie", "Liczba godzin"};

    private static final int[] COLUMNS_WIDTHS = {20, 20};

    private final Report1 report;

    public Report1Printer(Report1 report1) {
        this.report = report1;
    }

    public void printToConsole() {
        System.out.printf("%-40s %-15s\n", HEADERS[0], HEADERS[1]);

        report.getHoursPerPerson().forEach((person, hours) -> {
            System.out.printf("%-40s %-15s\n", person.getName(), hours);
        });

    }

    public void printToExcel(String excelFilePath) {
        ExcelExporter excelExporter = new ExcelExporter(excelFilePath, "report1", report.getDescription(), HEADERS);
        excelExporter.setColumnsWidths(COLUMNS_WIDTHS);

        for (Person person : report.getPersons()) {
            excelExporter.addRow();
            excelExporter.addCell(0, person.getName());
            excelExporter.addCell(1, report.getHoursForPerson(person));
        }

        excelExporter.saveToFile();
    }
}
