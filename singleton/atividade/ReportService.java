public class ReportService {

    public void generate() {
        AppConfig config = AppConfig.getInstance();
        System.out.println("Environment: " + config.getEnvironment());
    }
}
