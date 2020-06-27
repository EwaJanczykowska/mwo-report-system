package pl.edu.agh.mwo.reporter;

import org.apache.commons.cli.*;
import pl.edu.agh.mwo.reporter.loader.DataLoader;
import pl.edu.agh.mwo.reporter.loader.ReaderExcelFiles;
import pl.edu.agh.mwo.reporter.model.Company;
import pl.edu.agh.mwo.reporter.model.report.*;
import pl.edu.agh.mwo.reporter.report.generator.IReportGenerator;
import pl.edu.agh.mwo.reporter.report.generator.ReportGenerator;
import pl.edu.agh.mwo.reporter.report.printer.*;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;

public class Main {
    
    //-source resources -rtype 1 -export results.xls -employeefilter Kowalski_Jan -datefilter 2011/01/01-2019/12/31
    public static void main(String[] args) throws IOException, ParseException {

        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption("source", true, "source of data files");//-source resources
        options.addOption("rtype", true, "report type"); //-rtype 1
        options.addOption("export", true, "export report to excel"); //-export results.xls
        options.addOption("datefilter", true, "date filter"); //-datefilter 2011/01/01-2019/12/31
        options.addOption("employeefilter", true, "employee name filter"); //-employeefilter Kowalski_Jan
        options.addOption("taskfilter", true, "task name filter"); //-taskfilter spotkanie
        options.addOption("h", false, "help"); //-h
        try {
            CommandLine cmd = parser.parse(options, args);
            String directory = cmd.getOptionValue("source");
            String rType = cmd.getOptionValue("rtype");
            String dateFilter = cmd.getOptionValue("datefilter");
            String employeeFilter = cmd.getOptionValue("employeefilter");
            String outputPath = cmd.getOptionValue("export");
            String taskFilter = cmd.getOptionValue("taskfilter");

            LocalDate dateFrom = null;
            LocalDate dateTo = null;
            String employee = null;
            String task = null;

            boolean isError = false;

            if (!cmd.hasOption("source")) {
                isError = true;
                System.out.println("No source specified\nUse -source <path> to specify");
            }
            if (!cmd.hasOption("rtype")) {
                isError = true;
                System.out.println("No report type specified\nUse -rtype <numbers 1 to 5> to specify");
            }
            if (cmd.hasOption("h")) {
                printHelp();
            }

            if (outputPath != null) {
                String correctFormat = ".xls";
                int dotIndex = outputPath.indexOf('.');
                if (dotIndex > 0) {
                    outputPath = outputPath.split("\\.")[0] + correctFormat;
                } else {
                    outputPath = outputPath + correctFormat;
                }
            }

            if (dateFilter != null) {
                LocalDate[] receivedDates = dateFilter(dateFilter);
                if (receivedDates == null) {
                    isError = true;
                }
                else {
                    dateFrom = receivedDates[0];
                    dateTo = receivedDates[1];
                }
            }

            if (employeeFilter != null) {
                employee = employeeFilter(employeeFilter);
                if (employee == null) {
                    isError = true;
                }
            }

            if (taskFilter != null) {
                if (rType.equals("5")) {
                    task = taskFilter;
                } else {
                    isError = true;
                    System.out.println("Task filtration can be used only with -rtype 5");
                }
            }
            else if (rType != null && rType.equals("5")) {
                isError = true;
                System.out.println("Missing -taskfilter argument for report 5");
            }

            if (!isError) {
                ReaderExcelFiles f = new ReaderExcelFiles();
                ArrayList<Path> allFiles = f.getAllFiles(directory);
                DataLoader dataLoader = new DataLoader();

                Company company = dataLoader.loadData(allFiles, dateFrom, dateTo, employee);

                if (company.getPersons().size() < 1) {
                    System.out.println("No data for specified filter");
                    return;
                }

                IReportGenerator reportGenerator = new ReportGenerator(company, employeeFilter, dateFrom, dateTo, task);
                switch (rType) {
                    case "1":
                        Report1 report1 = reportGenerator.generateReport1();
                        IReportPrinter printer = new Report1Printer(report1);
                        printer.printToConsole();
                        if (cmd.hasOption("export")) {
                            printer.printToExcel(outputPath);
                        }
                        break;
                    case "2":
                        Report2 report2 = reportGenerator.generateReport2();
                        IReportPrinter printer2 = new Report2Printer(report2);
                        printer2.printToConsole();
                        if (cmd.hasOption("export")) {
                            printer2.printToExcel(outputPath);
                        }
                        break;
                    case "3":
                        Report3 report3 = reportGenerator.generateReport3();
                        IReportPrinter printer3 = new Report3Printer(report3);
                        printer3.printToConsole();
                        if (cmd.hasOption("export")) {
                            printer3.printToExcel(outputPath);
                        }
                        break;
                    case "4":
                        Report4 report4 = reportGenerator.generateReport4();
                        IReportPrinter printer4 = new Report4Printer(report4);
                        printer4.printToConsole();
                        if (cmd.hasOption("export")) {
                            printer4.printToExcel(outputPath);
                        }
                        break;
                    case "5":
                        Report5 report5 = reportGenerator.generateReport5();
                        IReportPrinter printer5 = new Report5Printer(report5);
                        printer5.printToConsole();
                        if (cmd.hasOption("export")) {
                            printer5.printToExcel(outputPath);
                        }
                        break;
                    default:
                        System.out.println("Incorrect report type\nChoose between 1,2,3,4,5");
                }
            }
        } catch (MissingArgumentException a) {
            System.out.println("Argument for either:\n-source\n-rtype\n-datefilter\n-employeefilter\n-export" +
                    "\n-taskfilter\nnot found.");
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
            dateFrom = null;
        } else if (datefiltercheck.equals("xxxx/xx/xx-")) {
            String dateFromString = inputDate.replace("-", "");
            dateFrom = LocalDate.parse(dateFromString, formatter);
            dateTo = LocalDate.now();
        } else {
            System.out.println("Wrong date format provided, report will not be generated");
            return null;
        }
        return new LocalDate[]{dateFrom, dateTo};
    }

    private static String employeeFilter(String inputEmployee) {
        String employeefiltercheck = polishLettersNormalizer(inputEmployee.toLowerCase()).replaceAll("[a-z]", " ").trim();
        String employee = null;

        if (employeefiltercheck.equals("_")) {
            employee = inputEmployee.toLowerCase().trim();
        } else {
            System.out.println("Wrong employee format provided, report will not be generated");
        }
        return employee;
    }

    private static String polishLettersNormalizer(String inputString) {
        for (int i = 0; i < inputString.length(); i++) {
            switch (inputString.charAt(i)) {
                case 'ą':
                    inputString = inputString.replace('ą', 'a');
                    break;
                case 'ć':
                    inputString = inputString.replace('ć', 'c');
                    break;
                case 'ę':
                    inputString = inputString.replace('ę', 'e');
                    break;
                case 'ł':
                    inputString = inputString.replace('ł', 'l');
                    break;
                case 'ń':
                    inputString = inputString.replace('ń', 'n');
                    break;
                case 'ó':
                    inputString = inputString.replace('ó', 'o');
                    break;
                case 'ś':
                    inputString = inputString.replace('ś', 's');
                    break;
                case 'ź':
                    inputString = inputString.replace('ź', 'z');
                    break;
                case 'ż':
                    inputString = inputString.replace('ż', 'z');
                    break;
            }
        }
        return inputString;
    }

    private static void printHelp() {
        System.out.println("Available commands:\n" +
                "-source <place path of source files here>\n" +
                "-rtype <choose between 1,2,3,4,5>\n" +
                "-datefilter <yyyy/mm/dd-yyyy/mm/dd>\n" +
                "-export <path\\filename.xls>\n" +
                "-employeefilter <surname_name>\n" +
                "-taskfilter <task>\n" +
                "-h for help\n" +
                "check ReadMe file for more information");
    }

}