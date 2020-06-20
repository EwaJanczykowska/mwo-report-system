package pl.edu.agh.mwo.reporter.reports;

import pl.edu.agh.mwo.reporter.model.Company;

public interface ReportGenerator {

    void printToConsole(Company company);

    void printToExcel(Company company);
}
