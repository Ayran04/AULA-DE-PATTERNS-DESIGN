public class AverageTemperatureDisplay implements TemperatureObserver {

    private double sum;
    private int count;

    @Override
    public void update(double temperature) {
        sum += temperature;
        count++;
        System.out.println("Average temperature: " + (sum / count) + " C");
    }
}
