public class HtmlReport extends ReportGenerator {

    @Override
    protected String formatData(String[] data) {
        return "<ul><li>" + String.join("</li><li>", data) + "</li></ul>";
    }

    @Override
    protected void saveReport(String content) {
        System.out.println("Saving report.html");
        System.out.println(content);
    }
}
