public class TemperatureAlert implements TemperatureObserver {

    private static final double HIGH_TEMPERATURE = 35.0;

    @Override
    public void update(double temperature) {
        if (temperature >= HIGH_TEMPERATURE) {
            System.out.println("High temperature alert: " + temperature + " C");
        }
    }
}
