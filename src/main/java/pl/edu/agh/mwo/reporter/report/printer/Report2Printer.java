package pl.edu.agh.mwo.reporter.report.printer;


import pl.edu.agh.mwo.reporter.model.report.Report2;


public class Report2Printer implements IReportPrinter {

    private final String[] headers = {"Nazwa projektu", "Liczba godzin"};
    private final Report2 report;

    public Report2Printer(Report2 report2) {
        this.report = report2;
    }

    public void printToConsole() {
        System.out.printf("%-40s %-15s\n", headers[0], headers[1]);

        report.getHoursPerProject().forEach((projectName, hours) -> {
            System.out.printf("%-40s %-15s\n", projectName, hours);
        });

    }

    public void printToExcel(String excelFilePath) {

    }
}
