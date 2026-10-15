public class TextReport extends ReportGenerator {

    @Override
    protected String formatData(String[] data) {
        return String.join("\n", data);
    }

    @Override
    protected void saveReport(String content) {
        System.out.println("Saving report.txt");
        System.out.println(content);
    }

    @Override
    protected void afterGeneration() {
        super.afterGeneration();
        System.out.println("Notification: report.txt is now available");
    }
}
