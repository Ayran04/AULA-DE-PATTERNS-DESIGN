import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WeatherStation implements TemperatureSubject {

    private double temperature;
    private final List<TemperatureObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(TemperatureObserver observer) {
        Objects.requireNonNull(observer, "Observer must not be null");
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(TemperatureObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (TemperatureObserver observer : observers) {
            observer.update(temperature);
        }
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
        notifyObservers();
    }
}
