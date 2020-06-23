package pl.edu.agh.mwo.reporter.report.printer;


import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

public class ExcelExporter {

    private HSSFWorkbook workbook;

    private HSSFSheet sheet;

    private int rowsCount;

    private Row lastRow;

    public boolean openFile(String excelFilePath) {
        try {
            File fileOut = new File(excelFilePath);

            if (fileOut.exists()){
                FileInputStream fileInputStream = new FileInputStream(excelFilePath);
                workbook = new HSSFWorkbook(new FileInputStream(fileOut));
                sheet = workbook.getSheetAt(0);
            //    workbook.close();

            } else {
                workbook = new HSSFWorkbook();
                sheet = workbook.createSheet("Report1");

            }
        } catch (EncryptedDocumentException | IOException e ) {
            System.out.println("Błąd przy otwieraniu pliku: " + excelFilePath);
            return false;
        }
//        catch (InvalidFormatException e){
//            System.out.println("Niewłaściwy format pliku: " + excelFilePath);
//        }
        return true;
    }

    public ExcelExporter(String excelFilePath, String reportName, String title, String[] headers) {
        boolean flag;
        System.out.println();
        flag = openFile(excelFilePath);
        if (flag) {
            addRow();
            addCell(0, title);

            addRow();
            addRow();
            for (int i = 0; i < headers.length; i++) {
                addCell(i, headers[i]);
            }
        }
    }

    public void addRow() {
        lastRow = sheet.createRow(rowsCount);
        rowsCount++;
    }

    public void addCell(int columnIndex, String value) {
        Cell cell = lastRow.createCell(columnIndex);
        cell.setCellValue(value);
    }

    public void addCell(int columnIndex, BigDecimal value) {
        Cell cell = lastRow.createCell(columnIndex);
        cell.setCellValue(value.doubleValue());
    }

    /**
     * @param widths List of widths as count of chars
     */
    public void setColumnsWidths(int[] widths) {
        for (int i = 0; i < widths.length; i++) {
            sheet.setColumnWidth(i, widths[i] * 256);
        }
    }

    public void saveToFile(String excelFilePath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(excelFilePath);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (EncryptedDocumentException | IOException e) {
            System.out.println("Błąd przy zapisywaniu pliku: " + excelFilePath);
        }
    }
}
