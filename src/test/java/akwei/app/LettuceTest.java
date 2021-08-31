package akwei.app;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import lombok.SneakyThrows;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class LettuceTest {

    @Test
    public void cluster() throws Exception {
        RedisURI node0 = RedisURI.create("localhost", 7000);
        RedisURI node1 = RedisURI.create("localhost", 7001);
        RedisURI node2 = RedisURI.create("localhost", 7002);
//        RedisURI node3 = RedisURI.create("localhost", 7003);
//        RedisURI node4 = RedisURI.create("localhost", 7004);
//        RedisURI node5 = RedisURI.create("localhost", 7005);

        RedisClusterClient clusterClient = RedisClusterClient.create(Arrays.asList(node0, node1, node2
//                , node3, node4, node5
        ));
        StatefulRedisClusterConnection<String, String> connection = clusterClient.connect();
        RedisAdvancedClusterCommands<String, String> syncCommands = connection.sync();
        syncCommands.set("key", "wei");

        String value = syncCommands.get("key");
        System.out.println("cluster get: " + value);

        Thread.sleep(1000000);
        connection.close();
        clusterClient.shutdown();
    }

    @SneakyThrows
    @Test
    public void one() {
        TimeUnit.SECONDS.sleep(3);
        RedisURI node1 = RedisURI.create("localhost", 6379);
        RedisClient redisClient = RedisClient.create(node1);
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();

        syncCommands.set("key", "Hello, Redis!");

        connection.close();
        redisClient.shutdown();
    }
}
