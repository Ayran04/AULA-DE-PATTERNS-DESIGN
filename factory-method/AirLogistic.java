public class AirLogistic extends Logistics {
    @Override
    public Transport createTransport() {
        return new Airplane();
    }
}
