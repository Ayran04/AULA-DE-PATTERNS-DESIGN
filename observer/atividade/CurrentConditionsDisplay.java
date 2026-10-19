public class CurrentConditionsDisplay implements TemperatureObserver {

    @Override
    public void update(double temperature) {
        System.out.println("Current temperature: " + temperature + " C");
    }
}
