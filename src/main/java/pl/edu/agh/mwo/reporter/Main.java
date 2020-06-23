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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;

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
            String datefilter = cmd.getOptionValue("datefilter");

            LocalDate dateFrom = null;
            LocalDate dateTo = null;
            boolean isWrongFormat = false;
            if (datefilter != null) {
                LocalDate[] receivedDates = dateFilter(datefilter);
                dateFrom = receivedDates[0];
                dateTo = receivedDates[1];
                if (dateFrom == null) {
                    isWrongFormat = true;
                }
            }

            ReaderExcelFiles f = new ReaderExcelFiles();
            ArrayList<Path> allFiles = f.getAllFiles(directory);
            DataLoader dataLoader = new DataLoader();
            Company company = dataLoader.loadData(allFiles);
//            Company company = dataLoader.loadData(allFiles, dateFrom, dateTo);
            IReportGenerator reportGenerator = new ReportGenerator(company);

            if (!isWrongFormat) {
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
                    default:
                        System.out.println("Incorrect report type\nChoose between 1,2,3,4,5");
                }
            }
        } catch (MissingArgumentException a) {
            System.out.println("Argument for either:\n-source\n-rtype\n-datefilter\n-employeefilter\nnot found.");
        } catch (DateTimeParseException b) {
            System.out.println("Wrong dates provided, for example 13 month, 32 day etc.");
        }

    }

    private static LocalDate[] dateFilter(String inputDate) {
        LocalDate dateFrom;
        LocalDate dateTo;
        String datefiltercheck = inputDate.replaceAll("[0-9]", "x");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        formatter = formatter.withLocale(Locale.ENGLISH);

        if (datefiltercheck.equals("xxxx/xx/xx-xxxx/xx/xx")) {
            String[] twoDates = inputDate.split("-");
            dateFrom = LocalDate.parse(twoDates[0], formatter);
            dateTo = LocalDate.parse(twoDates[1], formatter);
        } else if (datefiltercheck.equals("-xxxx/xx/xx")) {
            String dateToString = inputDate.replace("-", "");
            dateTo = LocalDate.parse(dateToString, formatter);
            String dateFromString = "1900/01/01";
            dateFrom = LocalDate.parse(dateFromString, formatter);
        } else if (datefiltercheck.equals("xxxx/xx/xx-")) {
            String dateFromString = inputDate.replace("-", "");
            dateFrom = LocalDate.parse(dateFromString, formatter);
            dateTo = LocalDate.now();
        } else {
            System.out.println("Wrong date format provided, report will not be generated");
            dateTo = null;
            dateFrom = null;
        }
        return new LocalDate[]{dateFrom, dateTo};
    }

}