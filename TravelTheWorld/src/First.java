import java.util.ArrayList;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class First implements MqttCallback {
    
    private static final String BROKER_URL = "tcp://localhost:1883";
    private static final String REQUEST_TOPIC = "disney/request";
    private static final String RESPONSE_TOPIC = "disney/response";
    private static final String CLIENT_ID = "FirstApplication";
    
    private IMqttClient mqttClient;
    private DatabaseHandler databaseHandler;
    private String currentState;
    

    
    public First() throws Exception {
        mqttClient = new MqttClient(BROKER_URL, CLIENT_ID, new MemoryPersistence());
        mqttClient.setCallback(this);
        mqttClient.connect();
        System.out.println("Connected to MQTT broker at " + BROKER_URL);
        mqttClient.subscribe(REQUEST_TOPIC);
        databaseHandler = new DatabaseHandler("jdbc:mariadb://localhost:3306/travel", "traveler", "world1997");
        currentState = "";
    }
    
    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Disconnected from MQTT broker unexpectedly");
        cause.printStackTrace();
    }
    

    
    String acces_type = "";
    String city_a = "";
    String city_b = "";
    

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String payload = new String(message.getPayload());
        
        if (payload.equalsIgnoreCase("Start")) {
            MqttMessage StartMessage = new MqttMessage(("\n" + "Welcome to TravelTheWorld ! Disney Edition ! \n" +
             "What type of transport you want to take from the first destination ? " + "\n (train/plane) - tape your choice then press enter"
             		+ "\n If you wish start again tape 'Start ' \n other commands exit, help, history  ").getBytes());
            mqttClient.publish(RESPONSE_TOPIC, StartMessage);
        }

        
        if (topic.equals(REQUEST_TOPIC)) {
            if (payload.equals("train")) {
            	var parksTrain = databaseHandler.getParksTrain();
            	StringBuilder messageBuilder = new StringBuilder();
            	messageBuilder.append("List of Parks accessible by Train:\n");
            	for (Park park : parksTrain) {
            	    messageBuilder.append("ID: ").append(park.getId()).append(", Name: ").append(park.getName())
            	            .append(", City: ").append(park.getCity()).append("\n");
            	}
            	messageBuilder.append("\nChoose one of the IDs to plan your journey :");
            	String responseMessage = messageBuilder.toString();

            	MqttMessage response = new MqttMessage(responseMessage.getBytes());
            	mqttClient.publish(RESPONSE_TOPIC, response);
            	
            	acces_type = "train";
                currentState = "trainCityChoice";
            } else if (currentState.equals("trainCityChoice")) {
                if(payload != null) {
					var CityTrain = databaseHandler.getCityByTrain(payload);
					
					//message build 
					StringBuilder messageBuilder = new StringBuilder();
					messageBuilder.append("List of city from which the park is accesible:\n");
					messageBuilder.append("Chose one of the City from which you will travel \n");
					for (TrainStation trainstation: CityTrain) {
						messageBuilder.append(", City B: ").append(trainstation.getCityB()).append(", Name: ").append(trainstation.getFrom_city()).append("\n");
					}
					// finish build and send back
	            	String responseMessage = messageBuilder.toString();
	            	MqttMessage response = new MqttMessage(responseMessage.getBytes());
	            	mqttClient.publish(RESPONSE_TOPIC, response);
				}
                
                
                city_b = payload;
                // Continue with further logic based on the destination choice
                currentState = "next"; // put it on next and setting 
            } else if (payload.equalsIgnoreCase("plane")) {
            	var parksPlane = databaseHandler.getParksPlane();
            	StringBuilder messageBuilder = new StringBuilder();
            	messageBuilder.append("List of Parks accessible by Plane:\n");
            	for (Park park : parksPlane) {
            	    messageBuilder.append("ID: ").append(park.getId()).append(", Name: ").append(park.getName())
            	            .append(", City: ").append(park.getCity()).append("\n");
            	}
            	messageBuilder.append("\nChoose one of the IDs to plan your journey :");
            	String responseMessage = messageBuilder.toString();

            	MqttMessage response = new MqttMessage(responseMessage.getBytes());
            	mqttClient.publish(RESPONSE_TOPIC, response);
            	
            	acces_type = "plane";
            	
                currentState = "planeCityChoice";
            } else if (currentState.equals("planeCityChoice")) {
                if(payload != null) {
					var CityPlane = databaseHandler.getCityByPlane(payload);
					
					//message build 
					StringBuilder messageBuilder = new StringBuilder();
					messageBuilder.append("List of city from which the park is accesible:\n");
					messageBuilder.append("Chose one of the City from which you will travel \n");
					for (Airport airport: CityPlane) {
						messageBuilder.append(", City B: ").append(airport.getCityB()).append(", Name: ").append(airport.getFrom_city()).append("\n");
					}
					// finish build and send back
	            	String responseMessage = messageBuilder.toString();
	            	MqttMessage response = new MqttMessage(responseMessage.getBytes());
	            	mqttClient.publish(RESPONSE_TOPIC, response);
				}
                
                city_b = payload;
                // Continue with further logic based on the destination choice
                currentState = "next"; // put it on next and setting 
            } else if (currentState.equals("next")) {
            	city_a = payload;
            	databaseHandler.setNewTravel(Integer.parseInt(city_a), Integer.parseInt(city_b), acces_type);
				StringBuilder messageBuilder = new StringBuilder();
				messageBuilder.append("your first journey will look like this :\n");
				
				//messageBuilder.append("City Start : " + city_a + " city Destination : " + city_b + "Acces Type " + acces_type + "\n");
				ArrayList<Travel> travels = databaseHandler.getLastXTravels(1);
				
				for (Travel travel : travels) {
				    messageBuilder.append("Access Type: ").append(travel.getAccessType())
				                  .append(", Park Name: ").append(travel.getParkName())
				                  .append(", City Start: ").append(travel.getCityStart())
				                  .append(", City End: ").append(travel.getCityEnd())
				                  .append("\n");
				}
				messageBuilder.append("The journey has been saved, you can see last 10 by taping history \n");
				messageBuilder.append("Choice the next type of transport for the next journey (train/plane) \n");
				String nextresponseMessage = messageBuilder.toString();
            	MqttMessage response = new MqttMessage(nextresponseMessage.getBytes());
            	mqttClient.publish(RESPONSE_TOPIC, response);
            	currentState = ""; 
            } else if (payload.equalsIgnoreCase("help")) {
            	StringBuilder messageBuilder = new StringBuilder();
            	// Content of Help
				messageBuilder.append("Welcome to Travel the World, Disney Help  :\n");
				messageBuilder.append("- 'Start' for back to the beggining of application \n");
				messageBuilder.append("- 'history' to see last 10 journey planned if there is something  \n");
				messageBuilder.append("- 'exit' to exit the application \n");
				messageBuilder.append("- 'delete' to delete All travels from history !!! \n");
				messageBuilder.append("To start first journey chose your prefered travel type train or plane\n");
				messageBuilder.append("Then follow the instruction given and enjoy your planned vacation ! \n");
				
				//
				String helpresponseMessage = messageBuilder.toString();
            	MqttMessage response = new MqttMessage(helpresponseMessage.getBytes());
            	mqttClient.publish(RESPONSE_TOPIC, response);
            } else if (payload.equalsIgnoreCase("delete")) {
            	databaseHandler.deleteAllTravels();
                MqttMessage StartMessage = new MqttMessage(("All travels deleted !").getBytes());
                       mqttClient.publish(RESPONSE_TOPIC, StartMessage);
            } else if (payload.equalsIgnoreCase("history")) {
            	StringBuilder messageBuilder = new StringBuilder();
				ArrayList<Travel> travels = databaseHandler.getLastXTravels(10);
								
				for (Travel travel : travels) {
				    messageBuilder.append("Travel by ").append(travel.getAccessType())
				                  .append(", To Park : ").append(travel.getParkName())
				                  .append(", From City : ").append(travel.getCityStart())
				                  .append(", To City : ").append(travel.getCityEnd())
				                  .append("\n");
				}
				String responseMessage = messageBuilder.toString();
            	MqttMessage response = new MqttMessage(responseMessage.getBytes());
            	mqttClient.publish(RESPONSE_TOPIC, response);
            } else if (payload.equalsIgnoreCase("exit")) {
                // Handle exit request
            	
            } else {
                
            }
        }
    }
    
    
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Not used in this example
    	// Obligatoire a implanter meme si il y a rien
    }
    
    public static void main(String[] args) {
        try {
        	First app = new First();
        } catch(Exception e) {
        	
            e.printStackTrace();
        }
    }
}
