public class City {
    private int id;
    private String name;
    private boolean hasTrain;
    private boolean hasPlane;

    // Constructor
    public City(int id, String name, boolean hasTrain, boolean hasPlane) {
        this.id = id;
        this.name = name;
        this.hasTrain = hasTrain;
        this.hasPlane = hasPlane;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasTrain() {
        return hasTrain;
    }

    public void setHasTrain(boolean hasTrain) {
        this.hasTrain = hasTrain;
    }

    public boolean hasPlane() {
        return hasPlane;
    }

    public void setHasPlane(boolean hasPlane) {
        this.hasPlane = hasPlane;
    }
}
