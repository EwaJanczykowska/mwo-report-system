package pl.edu.agh.mwo.reporter.report.printer;

import java.time.LocalDate;

public interface IReportPrinter {

    void printToConsole();

    void printToExcel(String excelFilePath);

//    public String getEmployeeName();
//    public LocalDate getDateFrom();
//    public LocalDate getDateTo();
}
