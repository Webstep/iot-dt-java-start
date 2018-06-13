import java.io.IOException;
import java.net.URLEncoder;

public class TouchTrigger {

    private String projectId = "xxx";

    private String apiUrlBase = "https://api.disruptive-technologies.com/v2";
    private String emulatorUrlBase = "https://emulator.disruptive-technologies.com/v2";
    private String apiDeviceUrl = apiUrlBase + "/projects/" + projectId + "/devices";
    private String emulatedDeviceUrl = emulatorUrlBase + "/projects/" + projectId + "/devices";
    private String codeExampleSensorDisplayName = "Java Code Example Touch Sensor";
    private String createEmulatedSensorJSON =
            "{\n" + "  \"type\": \"touch\",\n" + "  \"labels\": {\n" + "    \"name\": \"" + codeExampleSensorDisplayName + "\",\n" + "    \"virtual-sensor\": \"\"\n" + "  }\n" +
                    "}\n" + "publishEmulatedTouchJSON = {\n" + "  \"touch\": {\n" + "    \"touch\": {\n" + "    }\n" + "  }\n" + "}";
    private String publishEmulatedTouchJSON = "{\n" + "  \"touch\": {\n" + "    \"touch\": {\n" + "    }\n" + "  }\n" + "}";

    private Connector connector = new Connector();

    public static void main(String[] args) {
        TouchTrigger touchTrigger = new TouchTrigger();
        try {
            String devicePath = touchTrigger.getOrCreateTouchSensor();
            touchTrigger.generateTouchEvents(devicePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getOrCreateTouchSensor() throws IOException {
        System.out.println("Using apiDeviceUrl=" + apiDeviceUrl);
        String response = connector.get(apiDeviceUrl + "?label_filters=" + URLEncoder.encode("name=" + codeExampleSensorDisplayName, "UTF-8"));
        String devicePath;
        //Java has no built-in JSON parser, hard coding the check
        String devices = response.substring(response.indexOf("[") + 1, response.indexOf("]"));
        if (!devices.trim().contains(codeExampleSensorDisplayName)) {
            System.out.println("Creating touch sensor with name " + codeExampleSensorDisplayName);
            response = connector.post(emulatedDeviceUrl, createEmulatedSensorJSON);
            devicePath = getDevicePath(response);
        } else {
            System.out.println("Found already existing touch sensor with name " + codeExampleSensorDisplayName);
            devicePath = getDevicePath(devices);
        }

        return devicePath;
    }

    private void generateTouchEvents(String devicePath) throws IOException, InterruptedException {
        System.out.println("Starting to generate one emulated TouchEvent per second... (press CTRL-C to abort)");
        while (true) {
            Thread.sleep(1000);
            System.out.println("Touching Sensor");
            connector.post(emulatorUrlBase + "/" + devicePath + ":publish", publishEmulatedTouchJSON);
        }
    }

    private String getDevicePath(String response) {
        //Java has no built-in JSON parser, hard coding the check
        return response.substring(response.indexOf("projects"), response.indexOf("\",\"type\":"));
    }

}
