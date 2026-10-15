import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<ReportGenerator> reports = List.of(
                new CsvReport(),
                new HtmlReport(),
                new TextReport()
        );

        for (ReportGenerator report : reports) {
            System.out.println("=== " + report.getClass().getSimpleName() + " ===");
            report.generate();
            System.out.println();
        }
    }
}
