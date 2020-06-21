package pl.edu.agh.mwo.reporter.report.printer;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pl.edu.agh.mwo.reporter.model.Person;
import pl.edu.agh.mwo.reporter.model.report.Report1;

public class Report1Printer implements IReportPrinter {

    private final String[] headers = {"Nazwisko i imie", "Liczba godzin"};
    private final Report1 report;

    public Report1Printer(Report1 report1) {
        this.report = report1;
    }

    public void printToConsole() {
        System.out.printf("%-40s %-15s\n", headers[0], headers[1]);

        report.getHoursPerPerson().forEach((person, hours) -> {
            System.out.printf("%-40s %-15s\n", person.getName(), hours);
        });

    }

    public void printToExcel(String excelFilePath) {

        try {
//            //jesli plik istnieje to
//
//            // XSSFWorkbook wb = new XSSFWorkbook(excelFileName);
//            // w przeciwnym wypadku
//            // Ensure if file exist or not
//            if (file.isFile() && file.exists()) {
//                System.out.println("Reports.xlsm  is open");
//            }
//            else {
//                System.out.println("Reports.xlsm not exist"
//                        + " or can't open");
//            }

            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("Report1");
            // XSSFSheet sheet = wb.getSheet(sheetName);
            //title report

            XSSFRow titleRow = sheet.createRow(0);
            //  Cell celltitle = titleRow.createCell(title);
            titleRow.createCell(0).setCellValue(report.getDescription());

            //Header
            XSSFRow headerRow = sheet.createRow(2);
            for (int i = 0; i < headers.length; i++) {
                // kazda kolumna 20 znakow
                sheet.setColumnWidth(i, 20 * 256);
//                sheet.setColumnWidth(0, 2560);
//                sheet.setColumnWidth(1, 2560);
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            /* Header ends*/

            int startRowNum = 3;
            for (Person person : report.getPersons()) {
                XSSFRow row = sheet.createRow(startRowNum++);
                row.createCell(0).setCellValue(person.getName());
                row.createCell(1).setCellValue(report.getHoursForPerson(person));
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
