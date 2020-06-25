package pl.edu.agh.mwo.reporter.report.printer;

import java.util.List;

import pl.edu.agh.mwo.reporter.model.report.Report3;

public class Report3Printer implements IReportPrinter {

    private static final String[] HEADERS = {"Nazwisko i imie", "Ogólna liczba godzin"};
    private final Report3 report;

    public Report3Printer(Report3 report) {
        this.report = report;
    }

    @Override
    public void printToConsole() {
        List<String> projectNames = report.getProjectNames();
        List<String> personNames = report.getPersonNames();

        System.out.println("\n");
        System.out.println(report.getTitle());
        System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.printf("|  %-30s  |", HEADERS[0]);
        projectNames.forEach(project -> System.out.printf("  %-15s  |", project));
        System.out.printf("  %-20s  |\n", HEADERS[1]);
        System.out.println("------------------------------------------------------------------------------------------------------");

        for (String personName : personNames) {
            Report3.Record record = report.getRecordForPerson(personName);

            System.out.printf("|  %-30s  |", record.getPersonName());
            record.getHoursPerProject().values().forEach(hours -> System.out.printf("  %-15s  |", hours));
            System.out.printf("  %-20s  |\n", record.getTotalNumberOfHours());

        }

        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    @Override
    public void printToExcel(String excelFilePath) {

    }
}
