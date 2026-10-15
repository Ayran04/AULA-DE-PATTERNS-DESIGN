public abstract class ReportGenerator {

    public final void generate() {
        String[] data = readData();
        String content = formatData(data);
        saveReport(content);
        afterGeneration();
    }

    protected final String[] readData() {
        System.out.println("Reading sales data");
        return new String[] {"Notebook", "Mouse"};
    }

    protected abstract String formatData(String[] data);

    protected abstract void saveReport(String content);

    protected void afterGeneration() {
        System.out.println("Report generated successfully");
    }
}
