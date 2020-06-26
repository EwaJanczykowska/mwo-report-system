package pl.edu.agh.mwo.reporter.loader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ReaderExcelFiles {

    public ArrayList<Path> getAllFiles(String path) {
        ArrayList<Path> filesPaths = new ArrayList<>();
        try {
            Files.walk(Paths.get(path)).filter(Files::isRegularFile).filter(p -> p.toString().endsWith(".xls"))
                    .forEach(filesPaths::add);
        } catch (NoSuchFileException e) {
            System.out.println("Błąd - podana ścieżka nie istnieje: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Błąd: " + e);
            System.exit(1);
        }
        if (filesPaths.isEmpty()) {
            System.out.println("Błąd: Podana scieżka nie zawiera plikow Excel");
            System.exit(1);
        }
        return filesPaths;
    }


}
