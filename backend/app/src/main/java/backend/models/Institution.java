package backend.models;

public class Institution {
    public String id;
    public String name;
    public double latitude;
    public double longitude;

    public Institution(String id, String name, double latitude, double longitude){
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
