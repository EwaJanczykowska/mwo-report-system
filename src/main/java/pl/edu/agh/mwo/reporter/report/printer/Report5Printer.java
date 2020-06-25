package pl.edu.agh.mwo.reporter.report.printer;

import pl.edu.agh.mwo.reporter.model.Task;
import pl.edu.agh.mwo.reporter.model.report.Report2;
import pl.edu.agh.mwo.reporter.model.report.Report5;

import java.math.BigDecimal;
import java.util.Map;

public class Report5Printer implements IReportPrinter {

    private static final String[] HEADERS = {"Zadanie", "Liczba godzin"};

    private static final int[] COLUMNS_WIDTHS = {20, 20};

    private final Report5 report;

    public Report5Printer(Report5 report5) {
        this.report = report5;
    }

    public void printToConsole() {
        System.out.println("\n");
        System.out.println(report.getTitle() + " :" + report.getKeyword());
        if (report.getDateFrom() !=null && report.getDateTo() !=null) {
            System.out.println("Dane od: " + report.getDateFrom() + " do: "+report.getDateTo());
        }
        System.out.println("----------------------------------------------------------------------------------");
        System.out.printf("|  %-60s | %-15s|\n", HEADERS[0], HEADERS[1]);
        System.out.println("----------------------------------------------------------------------------------");


//        report.getTasks().forEach((name, hours) -> {
//            System.out.printf("|  %-40s | %-15s|\n",name,  hours);
//        });

        for (Task task: report.getTasks()){
            System.out.printf("|  %-60s | %-15s|\n", task.getName() , task.getHours());
        }
        System.out.println("----------------------------------------------------------------------------------");

    }

    public void printToExcel(String excelFilePath) {
        ExcelExporter excelExporter = new ExcelExporter(excelFilePath, "report5",
                report.getTitle(),
                HEADERS,
                report.getEmployeeName(),
                report.getDateFrom(),
                report.getDateTo());
        excelExporter.setColumnsWidths(COLUMNS_WIDTHS);

//        for (Task task : ) {
//            String key = entry.getKey();
//            BigDecimal value = entry.getValue();
//
//            excelExporter.addRow();
//            excelExporter.addCell(0, key);
//            excelExporter.addCell(1, value);
//        }

        excelExporter.saveToFile();
    }
}
