package pl.edu.agh.mwo.reporter;

import pl.edu.agh.mwo.reporter.loader.DataLoader;
import pl.edu.agh.mwo.reporter.model.Company;
import pl.edu.agh.mwo.reporter.reports.Report;
import pl.edu.agh.mwo.reporter.reports.Report1;

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
    }

}
