package pl.edu.agh.mwo.reporter.report.printer;

public interface IReportPrinter {

    void printToConsole();

    void printToExcel(String excelFilePath);
}
