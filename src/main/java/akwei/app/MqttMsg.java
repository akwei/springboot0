package akwei.app;

import lombok.Data;

@Data
public class MqttMsg {
    private String deviceType;
    private String thingsId;
    private String eventType;
    private String eventName;
    private String tenant;
    private String dataBase64;
}
