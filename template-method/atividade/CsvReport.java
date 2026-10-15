public class CsvReport extends ReportGenerator {

    @Override
    protected String formatData(String[] data) {
        return "Product\n" + String.join("\n", data);
    }

    @Override
    protected void saveReport(String content) {
        System.out.println("Saving report.csv");
        System.out.println(content);
    }
}
