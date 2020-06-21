package pl.edu.agh.mwo.reporter.reports;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.edu.agh.mwo.reporter.model.Company;
import pl.edu.agh.mwo.reporter.model.Person;
import pl.edu.agh.mwo.reporter.model.Task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class ReportTest implements Report {

    private Company company;
    private String filename;
    private String sheetName;
    private String[] headers;

    public ReportTest(Company company, String filename, String sheetName, String[] headers) {
        this.company = company;
        this.filename = filename;
        this.sheetName = sheetName;
        this.headers = headers;
    }

    public void printToConsole() {
    }


    public void printToExcel() throws IOException {

      //  final String[] header = {"Nazwisko i imie", "Liczba godzin"};
       // String filename = "resources\\Reports.xlsm";//name of excel file
        File file = new File(filename);
      //  String sheetName = "Report1";//name of sheet

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
            XSSFSheet sheet = wb.createSheet(sheetName);
            // XSSFSheet sheet = wb.getSheet(sheetName);


            //Header
            XSSFRow headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                // kazda kolumna 20 znakow
                sheet.setColumnWidth(i, 20 * 256);
//                sheet.setColumnWidth(0, 2560);
//                sheet.setColumnWidth(1, 2560);
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
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

            FileOutputStream fileOut = new FileOutputStream(filename);
            //zapisz workbook do Outputstream.
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();

        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}

