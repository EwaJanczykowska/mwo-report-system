package pl.edu.agh.mwo.reporter.loader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ReaderExcelFiles {

    // pobierz dane
    public ArrayList<Path> getAllFiles(String path) {
        ArrayList<Path> filesPaths = new ArrayList<Path>();
        try {
            Files.walk(Paths.get(path)).filter(Files::isRegularFile).filter(p -> p.toString().endsWith(".xls"))
                    .forEach(filesPaths::add);
        } catch (IOException e) {

            e.printStackTrace();
        }
        if (filesPaths.isEmpty()) {
        	System.out.println("Błąd: Podana scieżka nie zawiera plikow Excel");
        	System.exit(0);
        }
        return filesPaths;
    }





}
