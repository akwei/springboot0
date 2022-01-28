package akwei.app;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.event.CommandFailedEvent;
import com.mongodb.event.CommandListener;
import com.mongodb.event.CommandStartedEvent;
import com.mongodb.event.CommandSucceededEvent;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@Slf4j
@Configuration
public class MongoDBConfig {

//    @Bean
//    public ReactiveMongoTemplate reactiveMongoTemplate() {
//        MongoClientSettings settings = MongoClientSettings.builder()
//                .applyConnectionString(new ConnectionString("mongodb://localhost:27017"))
//                .addCommandListener(new CommandListener() {
//                    @Override
//                    public void commandStarted(CommandStartedEvent event) {
//                        log.info("akwei commandStarted");
//                    }
//
//                    @Override
//                    public void commandSucceeded(CommandSucceededEvent event) {
//                        log.info("akwei commandSucceeded");
//                    }
//
//                    @Override
//                    public void commandFailed(CommandFailedEvent event) {
//                        log.info("akwei commandFailed");
//                    }
//                }).build();
//        MongoClient mongoClient = MongoClients.create(settings);
//        return new ReactiveMongoTemplate(mongoClient, "iot_mapping");
//    }
}
