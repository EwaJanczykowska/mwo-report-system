package pl.edu.agh.mwo.reporter.report.printer;

import pl.edu.agh.mwo.reporter.model.report.Report4;

public class Report4Printer implements IReportPrinter {

    private static final String[] HEADERS = {"Nazwa projektu", "Nazwa czynności", "Liczba godzin"};
    private final Report4 report;

    public Report4Printer(Report4 report) {
        this.report = report;
    }

    @Override
    public void printToConsole() {

        System.out.println("\n");
        System.out.println(report.getTitle());
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.printf("|  %-30s  |  %-30s  |  %-15s  |\n", HEADERS[0], HEADERS[1], HEADERS[2]);
        System.out.println("------------------------------------------------------------------------------------------");

        for (Report4.Record record : report.getTop20()) {
            System.out.printf("|  %-30s  |  %-30s  |  %-15s  |\n", record.getTaskName(), record.getProjectName(), record.getHours());
        }

        System.out.println("------------------------------------------------------------------------------------------");
    }

    @Override
    public void printToExcel(String excelFilePath) {

    }
}
