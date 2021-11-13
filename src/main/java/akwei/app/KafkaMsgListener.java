package akwei.app;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
//@Component
public class KafkaMsgListener {

    @KafkaListener(topics = "wei-topic-00", groupId = "1")
    public void processMessage(ConsumerRecord<String, String> consumerRecord) {
        log.info("\n\n=================\n\nreceived kafka msg: {}\n\n=================\n\n", consumerRecord);
    }
}
