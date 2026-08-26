public class UserService {

    public void connect() {
        AppConfig config = AppConfig.getInstance();
        System.out.println("Connecting to " + config.getDatabaseUrl());
    }
}
