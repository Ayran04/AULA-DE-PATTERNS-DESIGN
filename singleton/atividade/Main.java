public class Main {

    public static void main(String[] args) throws InterruptedException {

        UserService userService = new UserService();
        ReportService reportService = new ReportService();

        userService.connect();
        reportService.generate();

        AppConfig config = AppConfig.getInstance();
        AppConfig otherReference = AppConfig.getInstance();

        System.out.println("Mesma instancia (UserService/ReportService/Main): " + (config == otherReference));

        // simula acesso concorrente de múltiplas threads à instância única
        int totalThreads = 10;
        AppConfig[] instances = new AppConfig[totalThreads];
        Thread[] threads = new Thread[totalThreads];

        for (int i = 0; i < totalThreads; i++) {
            int index = i;
            threads[i] = new Thread(() -> instances[index] = AppConfig.getInstance());
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        boolean todasIguais = true;
        for (AppConfig instance : instances) {
            if (instance != config) {
                todasIguais = false;
            }
        }

        System.out.println("Todas as threads obtiveram a mesma instancia: " + todasIguais);
    }
}
