import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseHandler {

    private Connection connection;

    public DatabaseHandler(String url, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
    }

    public void insertData(String data) throws SQLException {
        String query = "INSERT INTO my_table(data) VALUES(?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, data);
            statement.executeUpdate();
        }
    }
    
    public ArrayList<Park> getParksTrain() throws SQLException {
        ArrayList<Park> parks = new ArrayList<>();

        String sqlSelect = "SELECT p.* FROM Parks p JOIN City c ON p.city = c.name WHERE c.train = 1";
        try (Statement stmt = connection.createStatement();
             ResultSet res = stmt.executeQuery(sqlSelect)) {
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                String city = res.getString("city");
                Park park = new Park(id, name, city);
                parks.add(park);
            }
        }

        return parks;
    }

    public ArrayList<Park> getParksPlane() throws SQLException {
        ArrayList<Park> parks = new ArrayList<>();

        String sqlSelect = "SELECT p.* FROM Parks p JOIN City c ON p.city = c.name WHERE c.plane = 1";
        try (Statement stmt = connection.createStatement();
             ResultSet res = stmt.executeQuery(sqlSelect)) {
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                String city = res.getString("city");
                Park park = new Park(id, name, city);
                parks.add(park);
            }
        }

        return parks;
    }

    public ArrayList<TrainStation> getCityByTrain(String payload) throws SQLException {
        ArrayList<TrainStation> trainStations = new ArrayList<>();

        String sqlSelect = "SELECT ts.id, ts.city_a, ts.city_b, c_b.name AS from_city " +
                           "FROM City c " +
                           "JOIN TrainStation ts ON (c.id = ts.city_a) " +
                           "JOIN City c_b ON (c_b.id = ts.city_b) " +
                           "JOIN Parks p ON p.id = ? AND p.city = c.name";
        try (PreparedStatement stmt = connection.prepareStatement(sqlSelect)) {
            stmt.setString(1, payload);
            try (ResultSet res = stmt.executeQuery()) {
                while (res.next()) {
                    int id = res.getInt("id");
                    int city_a = res.getInt("city_a");
                    int city_b = res.getInt("city_b");
                    String from_city = res.getString("from_city");
                    TrainStation trainStation = new TrainStation(id, city_a, city_b, from_city);
                    trainStations.add(trainStation);
                }
            }
        }

        return trainStations;
    }

    public ArrayList<Airport> getCityByPlane(String payload) throws SQLException {
        ArrayList<Airport> airports = new ArrayList<>();

        String sqlSelect = "SELECT ap.id, ap.city_a, ap.city_b, c_b.name AS from_city " +
                           "FROM City c " +
                           "JOIN Airport ap ON (c.id = ap.city_a) " +
                           "JOIN City c_b ON (c_b.id = ap.city_b) " +
                           "JOIN Parks p ON p.id = ? AND p.city = c.name";
        try (PreparedStatement stmt = connection.prepareStatement(sqlSelect)) {
            stmt.setString(1, payload);
            try (ResultSet res = stmt.executeQuery()) {
                while (res.next()) {
                    int id = res.getInt("id");
                    int city_a = res.getInt("city_a");
                    int city_b = res.getInt("city_b");
                    String from_city = res.getString("from_city");
                    Airport airport = new Airport(id, city_a, city_b, from_city);
                    airports.add(airport);
                }
            }
        }

        return airports;
    }
    
    
    public ArrayList<Travel> getTravel() throws SQLException {
        ArrayList<Travel> travels = new ArrayList<>();
        String sqlSelect = "SELECT * FROM Travel";
        
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlSelect)) {
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String accessType = resultSet.getString("access_type");
                String parkName = resultSet.getString("park_name");
                String cityStart = resultSet.getString("city_start");
                String cityEnd = resultSet.getString("city_end");
                
                Travel travel = new Travel(id, accessType, parkName, cityStart, cityEnd);
                travels.add(travel);
            }
        }
        
        return travels;
    }
        
    public ArrayList<Travel> getLastXTravels(int number) throws SQLException {
        ArrayList<Travel> last10Travels = new ArrayList<>();
        String sqlSelect = "SELECT * FROM Travel ORDER BY id DESC LIMIT " + number;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlSelect)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String accessType = resultSet.getString("access_type");
                String parkName = resultSet.getString("park_name");
                String cityStart = resultSet.getString("city_start");
                String cityEnd = resultSet.getString("city_end");

                Travel travel = new Travel(id, accessType, parkName, cityStart, cityEnd);
                last10Travels.add(travel);
            }
        }

        return last10Travels;
    }
    
    public void setNewTravel(int cityA, int cityB, String accessType) throws SQLException {
        String cityNameA = findNameCity(cityA);
        String cityNameB = findNameCity(cityB);
        String parkName = findNameOfParkByCity(cityB);

        String sqlInsertTravel = "INSERT INTO travel(access_type, park_name, city_start, city_end) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sqlInsertTravel);
        stmt.setString(1, accessType);
        stmt.setString(2, parkName);
        stmt.setString(3, cityNameA);
        stmt.setString(4, cityNameB);
        stmt.executeUpdate();
    }
    
    public String findNameCity(int id) throws SQLException {
    	String CityName = "";
    	
    	String sqlSelectParkName = "SELECT name FROM City WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlSelectParkName)) {
            stmt.setInt(1, id);
            try (ResultSet res = stmt.executeQuery()) {
                if (res.next()) {
                	CityName = res.getString("name");
                }
            }
        }

        return CityName;
    }

    public String findNameOfParkByCity(int city_b) throws SQLException {
        String parkName = "";

        String sqlSelectParkName = "SELECT p.name FROM Parks p JOIN City c ON p.city = c.name WHERE c.id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlSelectParkName)) {
            stmt.setInt(1, city_b);
            try (ResultSet res = stmt.executeQuery()) {
                if (res.next()) {
                    parkName = res.getString("name");
                }
            }
        }

        return parkName;
    }
    
    public void deleteAllTravels() throws SQLException {
        String sqlDelete = "DELETE FROM Travel";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlDelete);
        }
    }

    public void close() throws SQLException {
        connection.close();
    }
}
