package pl.edu.agh.mwo.reporter.report.printer;


import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ExcelExporter {

    private final String exportFilePath;
    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private int rowsCount;
    private Row lastRow;
    private final String employeeName;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;
    private final String keyword;

    public ExcelExporter(String exportFilePath, String reportName, String title, String[] headers,
                         String employeeName, LocalDate dateFrom, LocalDate dateTo, String keyword) {
        this.exportFilePath = exportFilePath;
        this.employeeName = employeeName;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.keyword=keyword;

        initializeWorkbook();
        addRow();
        if (getEmployeeName() != null) {
            addCell(0, title + " dla: "+ getEmployeeName().replace("_"," "));
        } else {
            addCell(0, title);
        }

        addRow();
        if (getDateFrom() != null) {
            addCell(0, "Dane od: " + getDateFrom() + " do: " + getDateTo());
        }

        addRow();
        addRow();
        for (int i = 0; i < headers.length; i++) {
            addCell(i, headers[i]);
        }

    }

    public String getKeyword() {
        return keyword;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
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
