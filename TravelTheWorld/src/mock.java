import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class mock {
    public static void main(String[] args) {
        String url = "jdbc:mariadb://localhost:3306/travel";
        String user = "traveler";
        String password = "world1997";

        try {
            // Load the MariaDB JDBC driver
            Class.forName("org.mariadb.jdbc.Driver");

            // Connect to the database
            Connection conn = DriverManager.getConnection(url, user, password);

            // Create a statement object
            Statement stmt = conn.createStatement();
            
            
            /////////////////// Creation of Tables
            // Create Parks table
            String sqlCreateParks = "CREATE TABLE IF NOT EXISTS Parks ("
                    + "id INT NOT NULL AUTO_INCREMENT,"
                    + "name VARCHAR(255),"
                    + "city VARCHAR(255),"
                    + "PRIMARY KEY (id)"
                    + ")";
            stmt.executeUpdate(sqlCreateParks);
            // Create City table
            String sqlCreateCity = "CREATE TABLE IF NOT EXISTS City ("
                    + "id INT NOT NULL AUTO_INCREMENT,"
                    + "name VARCHAR(255),"
                    + "train BOOLEAN,"
                    + "plane BOOLEAN,"
                    + "PRIMARY KEY (id)"
                    + ")";
            stmt.executeUpdate(sqlCreateCity);
            
            
            // Create Airport table
            String sqlCreateAirport = "CREATE TABLE IF NOT EXISTS Airport ("
                    + "id INT NOT NULL AUTO_INCREMENT,"
                    + "city_a INT,"
                    + "city_b INT,"
                    + "PRIMARY KEY (id),"
                    + "FOREIGN KEY (city_a) REFERENCES City(id),"
                    + "FOREIGN KEY (city_b) REFERENCES City(id)"
                    + ")";
            stmt.executeUpdate(sqlCreateAirport);
            
            // Create TrainStation table
            String sqlCreateTrainStation = "CREATE TABLE IF NOT EXISTS TrainStation ("
                    + "id INT NOT NULL AUTO_INCREMENT,"
                    + "city_a INT,"
                    + "city_b INT,"
                    + "PRIMARY KEY (id),"
                    + "FOREIGN KEY (city_a) REFERENCES City(id),"
                    + "FOREIGN KEY (city_b) REFERENCES City(id)"
                    + ")";
            stmt.executeUpdate(sqlCreateTrainStation);

            // Create travel table
            String sqlCreateTravel = "CREATE TABLE IF NOT EXISTS travel ("
                    + "id INT NOT NULL AUTO_INCREMENT,"
                    + "access_type VARCHAR(255),"
                    + "park_name VARCHAR(255),"
                    + "city_start VARCHAR(255),"
                    + "city_end VARCHAR(255),"
                    + "PRIMARY KEY (id)"
                    + ")";
            stmt.executeUpdate(sqlCreateTravel);
            
            // Insert some data into the table
            // Check if there are any rows in the Parks table
            String sqlCountParks = "SELECT COUNT(*) FROM Parks";
            ResultSet rsParks = stmt.executeQuery(sqlCountParks);
            rsParks.next();
            int countParks = rsParks.getInt(1);

            // If there are no rows, insert the parks
            if (countParks < 1) {
                String sqlInsertPark1 = "INSERT INTO Parks (name, city) "
                        + "VALUES ('Disneyland Park', 'Anaheim')";
                stmt.executeUpdate(sqlInsertPark1);

                String sqlInsertPark2 = "INSERT INTO Parks (name, city) "
                        + "VALUES ('Walt Disney Studios Park', 'Marne-la-Vallee')";
                stmt.executeUpdate(sqlInsertPark2);

                String sqlInsertPark3 = "INSERT INTO Parks (name, city) "
                        + "VALUES ('Hong Kong Disneyland Park', 'Lantau Island')";
                stmt.executeUpdate(sqlInsertPark3);

                String sqlInsertPark4 = "INSERT INTO Parks (name, city) "
                        + "VALUES ('Shanghai Disneyland Park', 'Pudong New District')";
                stmt.executeUpdate(sqlInsertPark4);

                String sqlInsertPark5 = "INSERT INTO Parks (name, city) "
                        + "VALUES ('Tokyo Disneyland Park', 'Urayasu')";
                stmt.executeUpdate(sqlInsertPark5);
            } else {
                System.out.println("Parks table already has data. Skipping insert.");
            }



            // Insert some data into the City table
            String sqlInsertCity1 = "INSERT INTO City (name, train, plane) "
                    + "VALUES ('Anaheim', true, false)";
            stmt.executeUpdate(sqlInsertCity1);

            String sqlInsertCity2 = "INSERT INTO City (name, train, plane) "
                    + "VALUES ('Marne-la-Vallee', true, true)";
            stmt.executeUpdate(sqlInsertCity2);

            String sqlInsertCity3 = "INSERT INTO City (name, train, plane) "
                    + "VALUES ('Lantau Island', false, true)";
            stmt.executeUpdate(sqlInsertCity3);

            String sqlInsertCity4 = "INSERT INTO City (name, train, plane) "
                   
                    + "VALUES ('Pudong New District', true, true)";
            stmt.executeUpdate(sqlInsertCity4);

            String sqlInsertCity5 = "INSERT INTO City (name, train, plane) "
                    + "VALUES ('Urayasu', true, false)";
            stmt.executeUpdate(sqlInsertCity5);



            // Insert some data into the Airport table
            String sqlInsertAirport1 = "INSERT INTO Airport (city_a, city_b) "
                    + "VALUES (2, 3)";
            stmt.executeUpdate(sqlInsertAirport1);

            String sqlInsertAirport2 = "INSERT INTO Airport (city_a, city_b) "
                    + "VALUES (2, 4)";
            stmt.executeUpdate(sqlInsertAirport2);
            
            String sqlInsertAirport3 = "INSERT INTO Airport (city_a, city_b) "
                    + "VALUES (3, 2)";
            stmt.executeUpdate(sqlInsertAirport3);
            
            String sqlInsertAirport4 = "INSERT INTO Airport (city_a, city_b) "
                    + "VALUES (4, 2)";
            stmt.executeUpdate(sqlInsertAirport4);
            
            String sqlInsertAirport5 = "INSERT INTO Airport (city_a, city_b) "
                    + "VALUES (3, 4)";
            stmt.executeUpdate(sqlInsertAirport5);
            
            String sqlInsertAirport6 = "INSERT INTO Airport (city_a, city_b) "
                    + "VALUES (4, 3)";
            stmt.executeUpdate(sqlInsertAirport6);


            
            // Insert some data into the TrainStation table
            String sqlInsertTrainStation1 = "INSERT INTO TrainStation (city_a, city_b) "
                    + "VALUES (1, 2)";
            stmt.executeUpdate(sqlInsertTrainStation1);
            
            String sqlInsertTrainStation2 = "INSERT INTO TrainStation (city_a, city_b) "
                    + "VALUES (2, 1)";
            stmt.executeUpdate(sqlInsertTrainStation2);
            //
            String sqlInsertTrainStation3 = "INSERT INTO TrainStation (city_a, city_b) "
                    + "VALUES (1, 4)";
            stmt.executeUpdate(sqlInsertTrainStation3);
            
            String sqlInsertTrainStation4 = "INSERT INTO TrainStation (city_a, city_b) "
                    + "VALUES (4, 1)";
            stmt.executeUpdate(sqlInsertTrainStation4);
            
            //
            String sqlInsertTrainStation5 = "INSERT INTO TrainStation (city_a, city_b) "
                    + "VALUES (1, 5)";
            stmt.executeUpdate(sqlInsertTrainStation5);
            
            String sqlInsertTrainStation6 = "INSERT INTO TrainStation (city_a, city_b) "
                    + "VALUES (5, 1)";
            stmt.executeUpdate(sqlInsertTrainStation6);
            
            //
            String sqlInsertTrainStation7 = "INSERT INTO TrainStation (city_a, city_b) "
                    + "VALUES (2, 4)";
            stmt.executeUpdate(sqlInsertTrainStation7);
            
            String sqlInsertTrainStation8 = "INSERT INTO TrainStation (city_a, city_b) "
                    + "VALUES (4, 2)";
            stmt.executeUpdate(sqlInsertTrainStation8);
            
            //
            String sqlInsertTrainStation9 = "INSERT INTO TrainStation (city_a, city_b) "
                    + "VALUES (2, 5)";
            stmt.executeUpdate(sqlInsertTrainStation9);
            
            String sqlInsertTrainStation10 = "INSERT INTO TrainStation (city_a, city_b) "
                    + "VALUES (5, 2)";
            stmt.executeUpdate(sqlInsertTrainStation10);
            
            //
            String sqlInsertTrainStation11 = "INSERT INTO TrainStation (city_a, city_b) "
                    + "VALUES (4, 5)";
            stmt.executeUpdate(sqlInsertTrainStation11);
            
            String sqlInsertTrainStation12 = "INSERT INTO TrainStation (city_a, city_b) "
                    + "VALUES (5, 4)";
            stmt.executeUpdate(sqlInsertTrainStation12);
            

            // Close the resources
            rsParks.close();
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
