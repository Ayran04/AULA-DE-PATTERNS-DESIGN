public class AppConfig {

    // volatile garante visibilidade da escrita entre threads e evita
    // que o double-checked locking seja quebrado por reordenação de instruções
    private static volatile AppConfig instance;

    private String databaseUrl;
    private String environment;

    private AppConfig() {
        this.databaseUrl = "jdbc:mysql://localhost/app";
        this.environment = "development";
    }

    public static AppConfig getInstance() {

        if (instance == null) {
            synchronized (AppConfig.class) {
                if (instance == null) {
                    instance = new AppConfig();
                }
            }
        }

        return instance;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }
}
