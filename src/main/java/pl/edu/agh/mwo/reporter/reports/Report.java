package pl.edu.agh.mwo.reporter.reports;

import java.io.IOException;

public interface Report {

    void printToConsole();

    void printToExcel() throws IOException;
}
