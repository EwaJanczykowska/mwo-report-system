package pl.edu.agh.mwo.reporter.reports;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.edu.agh.mwo.reporter.model.Company;
import pl.edu.agh.mwo.reporter.model.Person;
import pl.edu.agh.mwo.reporter.model.Task;

import java.io.FileOutputStream;
import java.io.IOException;


public class ReportTest implements Report {
    private Company company;

    public ReportTest(Company company) {
        this.company = company;
    }

    public void printToConsole() {
    }


    public void printToExcel() throws IOException {

        final String[] header = {"Nazwisko i imie", "Liczba godzin"};
        String excelFileName = "resources\\Reports.xlsm";//name of excel file
        String sheetName = "Report1";//name of sheet

        try {
            //jesli plik istnieje to
            // XSSFWorkbook wb = new XSSFWorkbook(excelFileName);
            // w przeciwnym wypadku
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet(sheetName);
            // XSSFSheet sheet = wb.getSheet(sheetName);


            //Header
            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < header.length; i++) {
                // kazda kolumna 20 znakow
                sheet.setColumnWidth(i, 20 * 256);
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(header[i]);
            }
            /* Header ends*/

            int rowNum = 1;

            for (Person person : company.getPersons()) {
                int hours = 0;
                XSSFRow row = sheet.createRow(rowNum++);

                for (Task task : person.getTasks()) {
                    hours += task.getHours();
                }
                System.out.println();
                row.createCell(0).setCellValue(person.getName());
                row.createCell(1).setCellValue((Integer) hours);
            }

            FileOutputStream fileOut = new FileOutputStream(excelFileName);
            //zapisz workbook do Outputstream.
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();

        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}

