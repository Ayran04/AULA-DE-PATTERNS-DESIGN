public class Main {

    public static void main(String[] args) {

        GUIFactory factory = new LinuxFactory();
        Application application = new Application(factory);

        application.render();
    }
}