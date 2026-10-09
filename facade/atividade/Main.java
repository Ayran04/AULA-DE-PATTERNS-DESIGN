public class Main {

    public static void main(String[] args) {

        HomeTheaterFacade homeTheater = new HomeTheaterFacade(
                new Projector(),
                new SoundSystem(),
                new StreamingPlayer(),
                new RoomLights(),
                new ProjectionScreen());

        System.out.println("=== Starting session ===");
        homeTheater.watchMovie("Design Patterns: The Movie");

        System.out.println("=== Ending session ===");
        homeTheater.endMovie();
    }
}
