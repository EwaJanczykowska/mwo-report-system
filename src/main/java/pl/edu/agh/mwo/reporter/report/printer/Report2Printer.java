package pl.edu.agh.mwo.reporter.report.printer;


import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.edu.agh.mwo.reporter.model.Person;
import pl.edu.agh.mwo.reporter.model.report.Report2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class Report2Printer implements IReportPrinter {

    private final String[] headers = {"Nazwa projektu", "Liczba godzin"};
    private final Report2 report;

    public Report2Printer(Report2 report2) {
        this.report = report2;
    }

    public void printToConsole() {
        System.out.printf("%-40s %-15s\n", headers[0], headers[1]);

        report.getHoursPerProject().forEach((projectName, hours) -> {
            System.out.printf("%-40s %-15s\n", projectName, hours);
        });

    }

    public void printToExcel(String excelFilePath) {

        try {


            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("Report1");

            //title report

            XSSFRow titleRow = sheet.createRow(0);
            //  Cell celltitle = titleRow.createCell(title);
            titleRow.createCell(0).setCellValue(report.getDescription());

            //Header
            XSSFRow headerRow = sheet.createRow(2);
            for (int i = 0; i < headers.length; i++) {
                // kazda kolumna 20 znakow
                sheet.setColumnWidth(i, 20 * 256);
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            /* Header ends*/

            int startRowNum = 3;

            for(Map.Entry<String, BigDecimal> entry : report.getHoursPerProject().entrySet()) {
                String key = entry.getKey();
                BigDecimal value = entry.getValue();

                XSSFRow row = sheet.createRow(startRowNum++);
                row.createCell(0).setCellValue(key);
                row.createCell(1).setCellValue(value.doubleValue());

            }


            FileOutputStream fileOut = new FileOutputStream(excelFilePath);
            //zapisz workbook do Outputstream.
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();

        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
