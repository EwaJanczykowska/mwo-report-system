package pl.edu.agh.mwo.reporter.report.generator;

import pl.edu.agh.mwo.reporter.model.report.Report1;
import pl.edu.agh.mwo.reporter.model.report.Report2;
import pl.edu.agh.mwo.reporter.model.report.Report3;

public interface IReportGenerator {
    Report1 generateReport1();
    Report2 generateReport2();
    Report3 generateReport3();
}
