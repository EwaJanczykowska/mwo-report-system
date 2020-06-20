package pl.edu.agh.mwo.reporter;

import pl.edu.agh.mwo.reporter.loader.DataLoader;
import pl.edu.agh.mwo.reporter.model.Company;
import pl.edu.agh.mwo.reporter.model.report.Report1;
import pl.edu.agh.mwo.reporter.report.generator.IReportGenerator;
import pl.edu.agh.mwo.reporter.report.generator.ReportGenerator;
import pl.edu.agh.mwo.reporter.report.printer.IReportPrinter;
import pl.edu.agh.mwo.reporter.report.printer.Report1Printer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        String directory = args[0];

        ReaderExcelFiles f= new ReaderExcelFiles();
        ArrayList<Path> allFiles = f.getAllFiles(directory);

        DataLoader dataLoader = new DataLoader();
        Company company = dataLoader.loadData(allFiles.get(0));

        IReportGenerator reportGenerator = new ReportGenerator(company);
        Report1 report = reportGenerator.generateReport1();
        IReportPrinter printer = new Report1Printer(report);

        printer.printToConsole();
    }

}
