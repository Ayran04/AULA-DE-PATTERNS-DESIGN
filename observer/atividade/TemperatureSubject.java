public interface TemperatureSubject {
    void addObserver(TemperatureObserver observer);

    void removeObserver(TemperatureObserver observer);

    void notifyObservers();
}
