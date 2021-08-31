package akwei.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class RabbitMqMsgListener {


    @RabbitListener(queues = "queue1")
    public void processMessage(Message message) {
        String s = new String(message.getBody(), StandardCharsets.UTF_8);
        RabbitMqMsg rabbitMqMsg = JsonUtil.fromJson(s, RabbitMqMsg.class);
        log.info("\n\n=================\n\nreceived message: {}. headers: {}\n\n=================\n\n",
                rabbitMqMsg, message.getMessageProperties().getHeaders());
    }
}
