package pl.edu.agh.mwo.reporter.report.printer;

import pl.edu.agh.mwo.reporter.model.report.Report4;

public class Report4Printer implements IReportPrinter {

    private static final String[] HEADERS = {"Nazwa czynności", "Liczba godzin"};
    private static final int[] COLUMNS_WIDTHS = {70, 15};
    private final Report4 report;

    public Report4Printer(Report4 report) {
        this.report = report;
    }

    @Override
    public void printToConsole() {

        System.out.println("\n");

        if (report.getEmployeeName() !=null){
            System.out.println(report.getTitle() + " dla dewelopera: "+report.getEmployeeName().replace("_"," "));
        } else {
            System.out.println(report.getTitle());
        }
        if (report.getDateFrom() !=null && report.getDateTo() !=null) {
            System.out.println("Dane od: " + report.getDateFrom() + " do: "+report.getDateTo());
        }

        System.out.println("-------------------------------------------------------------------------------------" +
                "------------");
        System.out.printf("|  %-70s  |  %-15s  |\n", HEADERS[0], HEADERS[1]);
        System.out.println("-------------------------------------------------------------------------------------" +
                "------------");
        for (Report4.Record record : report.getTop20()) {
            System.out.printf("|  %-70s  |  %-15s  |\n", record.getTaskName(), record.getHours());
        }

        System.out.println("------------------------------------------------------------------------------------" +
                "------------");
    }

    @Override
    public void printToExcel(String excelFilePath) {
        ExcelExporter excelExporter = new ExcelExporter(excelFilePath, "report5", report.getTitle() ,
                HEADERS, report.getEmployeeName(), report.getDateFrom(), report.getDateTo(), report.getKeyword());

        excelExporter.setColumnsWidths(COLUMNS_WIDTHS);

        for (Report4.Record record : report.getTop20()) {

            excelExporter.addRow();
            excelExporter.addCell(0, record.getTaskName());
            excelExporter.addCell(1, record.getHours());
//            excelExporter.addCell(2, record.getProjectName());

        }

        excelExporter.saveToFile();
    }
}

