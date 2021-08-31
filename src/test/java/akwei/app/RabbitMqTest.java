package akwei.app;

import com.rabbitmq.client.*;
import lombok.SneakyThrows;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RabbitMqTest {
    ConnectionFactory connectionFactory;
    Connection con;
    String queueName = "queue1";

    @SneakyThrows
    @Before
    public void before() {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(9000);
        con = connectionFactory.newConnection();
    }

    @After
    public void after() throws Exception {
        Objects.requireNonNull(con).close();
    }

    @SneakyThrows
    @Test
    public void publish0() {
        Channel channel = con.createChannel();
        channel.queueDeclare(queueName, false, false, false, null);
        String message = "hello body - " + System.currentTimeMillis();
        Map<String, Object> headers = new HashMap<>();
        headers.put("wei-h1", "wei-123");
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .headers(headers)
                .build();


        channel.basicPublish("", queueName, properties, message.getBytes(StandardCharsets.UTF_8));
        System.out.println("finish publish");
    }


    @SneakyThrows
    @Test
    public void publish1() {
        Channel channel = con.createChannel();
        channel.exchangeDeclare("logs", "fanout");
        String message = "hello body publish1 - " + System.currentTimeMillis();
        channel.basicPublish("logs", "", null, message.getBytes(StandardCharsets.UTF_8));
    }

    @SneakyThrows
    @Test
    public void bind1() {
        Channel channel = con.createChannel();
        channel.queueBind(queueName, "logs", "");
    }


    @SneakyThrows
    @Test
    public void consume() {
        Channel channel = con.createChannel();

        String message = "hello body - " + System.currentTimeMillis();
        Map<String, Object> headers = new HashMap<>();
        headers.put("wei-h1", "wei-123");
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .headers(headers)
                .build();

        channel.basicPublish("", queueName, properties, message.getBytes(StandardCharsets.UTF_8));

        channel.basicConsume(queueName, true, new DeliverCallback() {
            @SneakyThrows
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                String msg = new String(message.getBody(), StandardCharsets.UTF_8);
                Thread.sleep(3000);
                System.out.println("Received msg: " + msg + " - thread: " + Thread.currentThread());
            }
        }, consumerTag -> {

        });
        Thread.sleep(30000);
        System.out.println("end");
    }
}
