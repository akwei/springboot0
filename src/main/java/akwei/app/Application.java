package akwei.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import tk.mybatis.spring.annotation.MapperScan;

@EnableFeignClients
@SpringBootConfiguration
@SpringBootApplication(scanBasePackages = "akwei.app")
@MapperScan(basePackages = {
        "akwei.app.**.dao"
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            System.out.println("commandLineRunner run1");
            System.out.println("commandLineRunner run2");
        };
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

//    @Bean
//    public JedisConnectionFactory redisConnectionFactory() {
//        return new JedisConnectionFactory();
//    }

    @Bean
    public <K, V> RedisTemplate<K, V> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<K, V> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(RedisSerializer.string());
        return redisTemplate;
    }

    @Bean
    public WebClient webClient() {
        WebClient.Builder builder = WebClient.builder();
        return WebClient.builder()
                .filter((request, next) -> {
                    ClientRequest clientRequest = ClientRequest.from(request).build();
                    return next.exchange(clientRequest);
                })
                .build();
    }


//    @Bean
//    ReactiveRedisTemplate<String, String> redisOperations(ReactiveRedisConnectionFactory factory) {
//        RedisSerializationContext.RedisSerializationContextBuilder<String, String> builder =
//                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
//        RedisSerializationContext<String, String> context = builder.build();
//        return new ReactiveRedisTemplate<>(factory, context);
//    }

}
