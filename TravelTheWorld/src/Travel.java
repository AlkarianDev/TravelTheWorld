public class Travel {
    private int id;
    private String accessType;
    private String parkName;
    private String cityStart;
    private String cityEnd;

    // Constructor
    public Travel(int id, String accessType, String parkName, String cityStart, String cityEnd) {
        this.id = id;
        this.accessType = accessType;
        this.parkName = parkName;
        this.cityStart = cityStart;
        this.cityEnd = cityEnd;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getCityStart() {
        return cityStart;
    }

    public void setCityStart(String cityStart) {
        this.cityStart = cityStart;
    }

    public String getCityEnd() {
        return cityEnd;
    }

    public void setCityEnd(String cityEnd) {
        this.cityEnd = cityEnd;
    }
}