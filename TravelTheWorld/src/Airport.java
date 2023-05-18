public class Airport {
    private int id;
    private int cityA;
    private int cityB;
    private String from_city;
    
    // Constructor
    public Airport(int id, int cityA, int cityB, String from_city) {
        this.id = id;
        this.cityA = cityA;
        this.cityB = cityB;
        this.from_city = from_city;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityA() {
        return cityA;
    }

    public void setCityA(int cityA) {
        this.cityA = cityA;
    }

    public int getCityB() {
        return cityB;
    }

    public void setCityB(int cityB) {
        this.cityB = cityB;
    }
    
    public void setFrom_city(String from_city) {
    	this.from_city = from_city;
    }
    
    public String getFrom_city() {
        return from_city;
    }
}
