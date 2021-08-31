package akwei.app;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ConfigTest {

    @Test
    public void createKafka() {
        Map<String, Object> map = new HashMap<>();
        map.put("urls", new String[]{"localhost:9000"});
        String json = JsonUtil.toJson(map);
        System.out.println(json);
    }

    @Test
    public void createJdbc() {
        Map<String, Object> map = new HashMap<>();
        map.put("urls", new String[]{"jdbc:mysql://localhost:330611/db_demo?useUnicode=true&characterEncoding=utf-8&autoReconnectForPools=true&autoReconnect=true"});
        String json = JsonUtil.toJson(map);
        System.out.println(json);
    }

    @Test
    public void createRedis() {
        Map<String, Object> map = new HashMap<>();
        map.put("urls", new String[]{"localhost:6379"});
        String json = JsonUtil.toJson(map);
        System.out.println(json);
    }

    @Test
    public void createRedisCluster() {
        Map<String, Object> map = new HashMap<>();
        map.put("urls", new String[]{"localhost:7000", "localhost:7001", "localhost:7002"});
        String json = JsonUtil.toJson(map);
        System.out.println(json);
    }

    @Test
    public void createRabbitMq() {
        Map<String, Object> map = new HashMap<>();
        map.put("urls", new String[]{"localhost:5672"});
        String json = JsonUtil.toJson(map);
        System.out.println(json);
    }
}
