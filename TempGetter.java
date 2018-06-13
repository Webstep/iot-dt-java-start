import java.io.IOException;

public class TempGetter {

    private String projectId = "xxx";
    private String sensorId = "xxx";

    private String apiUrlBase = "https://api.disruptive-technologies.com/v2";
    private String apiDeviceUrl = apiUrlBase + "/projects/" + projectId + "/devices/" + sensorId;

    private Connector connector = new Connector();

    public static void main(String[] args) {
        TempGetter tempGetter = new TempGetter();
        try {
            tempGetter.getState();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getState() throws IOException {
        String response = connector.get(apiDeviceUrl);
        int beginIndex = response.indexOf("value") + 7;
        int endIndex = beginIndex + 5;
        String tempValue = response.substring(beginIndex, endIndex);
        System.out.println("Current temperature for sensor with id " + sensorId + ": " + tempValue);
    }

}
