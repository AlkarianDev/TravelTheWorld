package MqttClient;

import java.util.Scanner;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Second implements MqttCallback {
    
    private static final String BROKER_URL = "tcp://localhost:1883";
    private static final String TOPIC = "disney/request";
    private static final String CLIENT_ID = "SecondApplication";
    
    private IMqttClient mqttClient;
    private Scanner scanner;
    
    public Second() throws Exception {
        mqttClient = new MqttClient(BROKER_URL, CLIENT_ID, new MemoryPersistence());
        mqttClient.setCallback(this);
        mqttClient.connect();
        System.out.println("Connected to MQTT broker at " + BROKER_URL);
        mqttClient.subscribe("disney/response");
        scanner = new Scanner(System.in);
    }
    
    public void run() throws Exception {
    	String Start = "Start";
    	MqttMessage start = new MqttMessage(Start.getBytes());
    	mqttClient.publish(TOPIC, start);
        while(true) {
            
            String message = scanner.nextLine();
            if(message.equals("exit")) {
                break;
            }
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttClient.publish(TOPIC, mqttMessage);
        }
        mqttClient.disconnect();
        scanner.close();
        System.out.println("Disconnected from MQTT broker");
    }
    
    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Disconnected from MQTT broker unexpectedly");
        cause.printStackTrace();
    }
    
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String payload = new String(message.getPayload());
        System.out.println(payload);
        System.out.print("\n > ");
    }
    
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Not used in this example
    }
    
    public static void main(String[] args) {
        try {
        	Second app = new Second();
            app.run();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
