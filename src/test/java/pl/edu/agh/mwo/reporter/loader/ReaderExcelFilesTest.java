package pl.edu.agh.mwo.reporter.loader;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ReaderExcelFilesTest {

    @Test
    public void getAllFiles_shouldReturnXlsFiles() {
        ReaderExcelFiles readerExcelFiles = new ReaderExcelFiles();

        List<Path> allFiles = readerExcelFiles.getAllFiles("resources/2012");

        Assert.assertEquals(3, allFiles.size());
        Assert.assertTrue(allFiles.contains(Paths.get("resources/2012/01/Kowalski_Jan.xls")));
        Assert.assertTrue(allFiles.contains(Paths.get("resources/2012/01/Nowak_Piotr.xls")));
        Assert.assertTrue(allFiles.contains(Paths.get("resources/2012/02/Kowalski_Jan.xls")));
    }

}