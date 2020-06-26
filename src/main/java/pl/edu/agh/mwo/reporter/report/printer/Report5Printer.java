package pl.edu.agh.mwo.reporter.report.printer;

import pl.edu.agh.mwo.reporter.model.Person;
import pl.edu.agh.mwo.reporter.model.Task;
import pl.edu.agh.mwo.reporter.model.report.Report2;
import pl.edu.agh.mwo.reporter.model.report.Report5;

import java.math.BigDecimal;
import java.util.Map;

public class Report5Printer implements IReportPrinter {

    private static final String[] HEADERS = {"Zadanie", "Liczba godzin","Data", "Nazwa projektu"};

    private static final int[] COLUMNS_WIDTHS = {50, 20, 20, 20};

    private final Report5 report;

    public Report5Printer(Report5 report5) {
        this.report = report5;
    }

    public void printToConsole() {
        System.out.println("\n");
        System.out.println(report.getTitle() + ": " + report.getKeyword());
        if (report.getDateFrom() !=null && report.getDateTo() !=null) {
            System.out.println("Dane od: " + report.getDateFrom() + " do: "+report.getDateTo());
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------------");
        System.out.printf("|  %-60s | %-15s| %-15s| %-15s|\n", HEADERS[0], HEADERS[1], HEADERS[2],HEADERS[3]);
        System.out.println("-------------------------------------------------------------------------------------------------------------------");

        for (Task task: report.getTasks()){
            System.out.printf("|  %-60s | %-15s| %-15s| %-15s|\n", task.getName() , task.getHours(), task.getDate(), task.getProjectName());
        }

        System.out.println("--------------------------------------------------------------------------------------------------------------------");

    }

    public void printToExcel(String excelFilePath) {
        ExcelExporter excelExporter = new ExcelExporter(excelFilePath, "report5", report.getTitle()+ report.getKeyword(),
                HEADERS, report.getEmployeeName(), report.getDateFrom(), report.getDateTo(), report.getKeyword());

        excelExporter.setColumnsWidths(COLUMNS_WIDTHS);

        for (Task task: report.getTasks()){
            excelExporter.addRow();
            excelExporter.addCell(0, task.getName());
            excelExporter.addCell(1, task.getHours());
            excelExporter.addCell(2, task.getDate().toString());
            excelExporter.addCell(3, task.getProjectName());
        }

        excelExporter.saveToFile();
    }
}
