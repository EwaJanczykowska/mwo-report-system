package pl.edu.agh.mwo.reporter;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.edu.agh.mwo.reporter.model.Company;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExportToExcel {

    public void writeXLSXFile(Company company) throws IOException {

        String excelFileName = "resources\\Reports.xlsm";//name of excel file

        String sheetName = "Report1";//name of sheet

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName) ;


        for (int r=0;r < 5; r++ )
        {
            XSSFRow row = sheet.createRow(r);

            //iterating c number of columns
            for (int c=0;c < 5; c++ )
            {
                XSSFCell cell = row.createCell(c);

                cell.setCellValue("Cell "+r+" "+c);
            }
        }

        //iterating r number of rows
//        for (int r=0;r < 5; r++ )
//        {
//            XSSFRow row = sheet.createRow(r);
//
//            //iterating c number of columns
//            for (int c=0;c < 5; c++ )
//            {
//                XSSFCell cell = row.createCell(c);
//
//                cell.setCellValue("Cell "+r+" "+c);
//            }
//        }

        FileOutputStream fileOut = new FileOutputStream(excelFileName);

        //write this workbook to an Outputstream.
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();
    }



}
