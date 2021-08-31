package akwei.app;

import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.StringCodec;
import io.lettuce.core.masterreplica.MasterReplica;
import io.lettuce.core.masterslave.MasterSlave;
import io.lettuce.core.masterslave.StatefulRedisMasterSlaveConnection;
import io.lettuce.core.sentinel.api.StatefulRedisSentinelConnection;
import io.lettuce.core.sentinel.api.sync.RedisSentinelCommands;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

public class LettuceMain {

    public static void main(String[] args) throws Exception {
        main2();
    }

    public static void main0() throws Exception {
        RedisURI redisURI = RedisURI.create("localhost", 6379);
        RedisClient redisClient = RedisClient.create(redisURI);
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();
        String reply = syncCommands.set("key", "Hello, Redis!");
        System.out.println("result: " + reply);

//        async
        RedisAsyncCommands<String, String> asyncCommands = connection.async();
        RedisFuture<String> future = asyncCommands.set("key", "yuan");
        future.get();

        //reactive
        RedisReactiveCommands<String, String> reactiveCommands = connection.reactive();
        Mono<String> mono = reactiveCommands.get("key");
        mono.subscribe(System.out::println);

        Flux<KeyValue<String, String>> flux = reactiveCommands.mget("key", "123");
        flux.subscribe(kv -> System.out.println(kv.getKey() + " - " + kv.getValue()));

        Thread.sleep(10000000);
        redisClient.shutdown();
    }

    public static void main1() throws Exception {
        RedisURI redisURI = RedisURI.create("localhost", 6379);
        RedisClient redisClient = RedisClient.create(redisURI);
//        ConnectionFuture<StatefulRedisConnection<String, String>> connectFuture = redisClient.connectAsync(StringCodec.UTF8, redisURI);
//
//        connectFuture.thenAccept(stringStringStatefulRedisConnection -> {
//            System.out.println("\n\n\n=========================\n\n\n");
//            System.out.println(stringStringStatefulRedisConnection);
//            System.out.println("\n\n\n=========================\n\n\n");
//        });

        CompletableFuture<StatefulRedisSentinelConnection<String, String>> connectSentinelAsync = redisClient.connectSentinelAsync(StringCodec.UTF8, redisURI);
//        connectSentinelAsync.thenAccept(connection -> {
//            RedisSentinelCommands<String, String> commands = connection.sync();
//            RedisSentinelAsyncCommands<String, String> asyncCommands = connection.async();
//            RedisSentinelReactiveCommands<String, String> reactiveCommands = connection.reactive();
//            System.out.println("finish");
//        });

        StatefulRedisSentinelConnection<String, String> sentinelConnection = connectSentinelAsync.get();
        RedisSentinelCommands<String, String> commands = sentinelConnection.sync();
        MasterReplica.connect(redisClient, StringCodec.UTF8, redisURI);
        Thread.sleep(10000000);
        redisClient.shutdown();
    }

    public static void main2() throws Exception {
        RedisURI redisURI = RedisURI.create("localhost", 6379);
        RedisClient redisClient = RedisClient.create(redisURI);
        StatefulRedisMasterSlaveConnection<String, String> connection = MasterSlave.connect(redisClient, StringCodec.UTF8,
                RedisURI.create("redis://localhost"));
        connection.setReadFrom(ReadFrom.MASTER_PREFERRED);
        System.out.println("Connected to Redis");

        RedisCommands<String, String> commands = connection.sync();
        String result = commands.get("key");
        System.out.println(result);

        connection.close();
        redisClient.shutdown();

        Thread.sleep(10000000);
        redisClient.shutdown();
    }

}
