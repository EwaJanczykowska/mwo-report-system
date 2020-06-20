package pl.edu.agh.mwo.reporter.report.printer;

import pl.edu.agh.mwo.reporter.model.report.Report1;

public class Report1Printer implements IReportPrinter {

    private Report1 report;

    public Report1Printer(Report1 report1) {
        this.report = report1;
    }

    public void printToConsole() {
        System.out.printf("%-40s %-15s\n", "Imie nazwisko", "Liczba godzin");

        report.getHoursPerPerson().forEach((person, hours) -> {
            System.out.printf("%-40s %-15d\n", person.getName(), hours);
        });

    }

    public void printToExcel() {
    }
}
