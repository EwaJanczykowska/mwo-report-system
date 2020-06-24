package pl.edu.agh.mwo.reporter.report.printer;


import pl.edu.agh.mwo.reporter.model.report.Report2;

import java.math.BigDecimal;
import java.util.Map;


public class Report2Printer implements IReportPrinter {

    private static final String[] HEADERS = {"Nazwa projektu", "Liczba godzin"};

    private static final int[] COLUMNS_WIDTHS = {20, 20};

    private final Report2 report;

    public Report2Printer(Report2 report2) {
        this.report = report2;
    }

    public void printToConsole() {

        System.out.println("\nRAPORT  2. Lista godzin w projektach.");
        System.out.println("--------------------------------------------------------------");
        System.out.printf("|  %-40s | %-15s|\n", HEADERS[0], HEADERS[1]);
        System.out.println("--------------------------------------------------------------");


        report.getHoursPerProject().forEach((projectName, hours) -> {
            System.out.printf("|  %-40s | %-15s|\n", projectName, hours);
        });
        System.out.println("--------------------------------------------------------------");

    }

    public void printToExcel(String excelFilePath) {
        ExcelExporter excelExporter = new ExcelExporter(excelFilePath, "report2", report.getDescription(), HEADERS);
        excelExporter.setColumnsWidths(COLUMNS_WIDTHS);

        for (Map.Entry<String, BigDecimal> entry : report.getHoursPerProject().entrySet()) {
            String key = entry.getKey();
            BigDecimal value = entry.getValue();

            excelExporter.addRow();
            excelExporter.addCell(0, key);
            excelExporter.addCell(1, value);
        }

        excelExporter.saveToFile();
    }
}
