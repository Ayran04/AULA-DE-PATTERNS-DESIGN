public class Main {

    public static void main(String[] args) {

        Logistics roadLogistics = new RoadLogistics();
        roadLogistics.planDelivery();

        System.out.println();

        Logistics seaLogistics = new SeaLogistics();
        seaLogistics.planDelivery();

        System.out.println();

        Logistics airLogistics = new AirLogistic();
        airLogistics.planDelivery();
    }
}