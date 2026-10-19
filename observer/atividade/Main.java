public class Main {

    public static void main(String[] args) {

        System.out.println("=== Station 1: display and alert ===");
        WeatherStation station = new WeatherStation();
        CurrentConditionsDisplay display = new CurrentConditionsDisplay();
        TemperatureAlert alert = new TemperatureAlert();

        station.addObserver(display);
        station.addObserver(alert);
        station.addObserver(display);

        setTemperature(station, 28.0);
        setTemperature(station, 36.0);

        System.out.println("-- Removing alert --");
        station.removeObserver(alert);
        setTemperature(station, 38.0);

        System.out.println("-- Removing alert again and display --");
        station.removeObserver(alert);
        station.removeObserver(display);
        setTemperature(station, 40.0);

        System.out.println();
        System.out.println("=== Station 2: average display ===");
        WeatherStation station2 = new WeatherStation();
        station2.addObserver(new AverageTemperatureDisplay());

        setTemperature(station2, 20.0);
        setTemperature(station2, 30.0);
        setTemperature(station2, 40.0);
    }

    private static void setTemperature(WeatherStation station, double temperature) {
        System.out.println("Setting temperature to " + temperature + " C");
        station.setTemperature(temperature);
    }
}
