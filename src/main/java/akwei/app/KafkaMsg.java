package akwei.app;

import lombok.Data;

@Data
public class KafkaMsg {
    private String key;
    private String data;
}
