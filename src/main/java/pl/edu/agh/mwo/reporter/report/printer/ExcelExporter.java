package pl.edu.agh.mwo.reporter.report.printer;


import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.math.BigDecimal;

public class ExcelExporter {

    private HSSFWorkbook workbook;

    private HSSFSheet sheet;

    private int rowsCount;

    private Row lastRow;

    private final String exportFilePath;

    public ExcelExporter(String exportFilePath, String reportName, String title, String[] headers) {
        this.exportFilePath = exportFilePath;
        initializeWorkbook();
        addRow();
        addCell(0, title);

        addRow();
        addRow();
        for (int i = 0; i < headers.length; i++) {
            addCell(i, headers[i]);
        }

    }

    private void initializeWorkbook() {
        try {
            InputStream templateInputStream = this.getClass().getResourceAsStream("/template.xls");
            workbook = new HSSFWorkbook(templateInputStream);
            sheet = workbook.getSheetAt(0);
            templateInputStream.close();
        } catch (IOException e) {
            System.out.println("Błąd przy otwieraniu szablonu");
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

    public void saveToFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream(exportFilePath);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
            System.out.println("Exportowano raport do pliku: " + exportFilePath);
        } catch (EncryptedDocumentException | IOException e) {
            System.out.println("Błąd przy zapisywaniu pliku: " + exportFilePath);
        }
    }
}
