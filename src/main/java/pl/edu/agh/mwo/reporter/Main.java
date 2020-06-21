package pl.edu.agh.mwo.reporter;

import org.apache.commons.cli.*;
import pl.edu.agh.mwo.reporter.loader.DataLoader;
import pl.edu.agh.mwo.reporter.loader.ReaderExcelFiles;
import pl.edu.agh.mwo.reporter.model.Company;
import pl.edu.agh.mwo.reporter.model.report.Report1;
import pl.edu.agh.mwo.reporter.model.report.Report2;
import pl.edu.agh.mwo.reporter.report.generator.IReportGenerator;
import pl.edu.agh.mwo.reporter.report.generator.ReportGenerator;
import pl.edu.agh.mwo.reporter.report.printer.IReportPrinter;
import pl.edu.agh.mwo.reporter.report.printer.Report1Printer;
import pl.edu.agh.mwo.reporter.report.printer.Report2Printer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {

    final static String OUTPUT_PATH = "resources\\Reports.xlsm";

    public static void main(String[] args) throws IOException, ParseException {

        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption("source", true, "source of data files");
        options.addOption("rtype", true, "report type");
        options.addOption("export", false, "export report to excel");
        options.addOption("datefilter", true, "date filter");
        options.addOption("employeefilter", true, "employee name filter");
        try {
            CommandLine cmd = parser.parse(options, args);
            String directory = cmd.getOptionValue("source");
            String rType = cmd.getOptionValue("rtype");

            ReaderExcelFiles f = new ReaderExcelFiles();
            ArrayList<Path> allFiles = f.getAllFiles(directory);

            DataLoader dataLoader = new DataLoader();
            Company company = dataLoader.loadData(allFiles);

            IReportGenerator reportGenerator = new ReportGenerator(company);

            switch (rType) {
                case "1":
                    Report1 report1 = reportGenerator.generateReport1();
                    IReportPrinter printer = new Report1Printer(report1);
                    printer.printToConsole();
                    if (cmd.hasOption("export")) {
                        printer.printToExcel(OUTPUT_PATH);
                    }
                    break;
                case "2":
                    Report2 report2 = reportGenerator.generateReport2();
                    IReportPrinter printer2 = new Report2Printer(report2);
                    printer2.printToConsole();
                    if (cmd.hasOption("export")) {
                        printer2.printToExcel(OUTPUT_PATH);
                    }
                    break;
//                case "3":
//                    Report3 report3 = reportGenerator.generateReport3();
//                    IReportPrinter printer3 = new Report1Printer(report3);
//                    printer3.printToConsole();
//                    if (cmd.hasOption("export")) {
//                        // excel printer here
//                    }
//                    break;
                default:
                    System.out.println("Incorrect report type\nChoose between 1,2,3,4,5");
            }
        } catch (MissingArgumentException a) {
            System.out.println("Argument for either:\n-source\n-rtype\n-datefilter\n-employeefilter\nnot found.");
        }

    }

}
