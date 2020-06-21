package pl.edu.agh.mwo.reporter;

import pl.edu.agh.mwo.reporter.loader.DataLoader;
import pl.edu.agh.mwo.reporter.loader.ReaderExcelFiles;
import pl.edu.agh.mwo.reporter.model.Company;
import pl.edu.agh.mwo.reporter.reports.Report;
import pl.edu.agh.mwo.reporter.reports.Report1;
import pl.edu.agh.mwo.reporter.reports.ReportTest;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        String directory = args[0];

        ReaderExcelFiles f= new ReaderExcelFiles();
        ArrayList<Path> allFiles = f.getAllFiles(directory);

        DataLoader dataLoader = new DataLoader();
        Company company = dataLoader.loadData(allFiles.get(0));

        Report report1 = new Report1(company);
        report1.printToConsole();
//================= ========
//Raport 1 - do Excela
       String[]   header = {"Nazwisko i imie", "Liczba godzin"};
        ReportTest raport1 = new ReportTest(company,
                "resources\\Reports.xlsm",
                "Report1",
                header
                );//name of sheet);
        raport1.printToExcel();

   //=====================================================
    }

}
