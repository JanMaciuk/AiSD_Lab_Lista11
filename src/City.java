public class City {
    protected int ID;
    protected int roadLength;
    public int nextCityID;


    public City(int id) {
        this.ID = id;
    }
    public void AddRoad(int nextCityID, int roadLength) {
        this.roadLength = roadLength;
        this.nextCityID = nextCityID;
    }

    public String name() {
        return switch (ID) {
            case 0 -> "Wrocław";
            case 1 -> "Oława";
            case 2 -> "Brzeg";
            case 3 -> "Nysa";
            case 4 -> "Opole";
            default -> "Inne miasto";
        };
    }
}
