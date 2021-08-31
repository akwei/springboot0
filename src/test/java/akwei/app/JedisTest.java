package akwei.app;

import org.junit.Test;
import redis.clients.jedis.Jedis;

public class JedisTest {

    @Test
    public void invoke1() {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.set("foo", "bar");
        String value = jedis.get("foo");
        System.out.println(value);
    }
}
