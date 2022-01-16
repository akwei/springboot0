package akwei.app;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.hc.client5.http.async.methods.*;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.message.StatusLine;
import org.apache.hc.core5.io.CloseMode;
import org.apache.hc.core5.reactor.IOReactorConfig;
import org.apache.hc.core5.util.Timeout;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class HelloCtrl {


    @Value("${app.name}")
    private String appName;

    @Value("${app.age}")
    private String appAge;

    @Resource
    private UserService userService;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private MchService mchService;

//    @Resource
//    private KafkaTemplate<String, String> kafkaTemplate;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private WebClient webClient;

    @Resource
    private RestHighLevelClient restHighLevelClient;

//    @Resource
//    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private MongoTemplate mongoTemplate;

    @RequestMapping("/")
    public String index() {
        List<User> users = this.userService.getUsers();
        return this.appName + " - " + this.appAge;
    }

    @SneakyThrows
    @RequestMapping("/es-search")
    public GetResponse esGet() {
        GetRequest getRequest = new GetRequest("local-data");
        getRequest.id("1");
        return this.restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
    }

    @SneakyThrows
    @RequestMapping("/es-index")
    public String esIndex() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("name", "akwei");
        IndexRequest indexRequest = new IndexRequest("local-data").id("1").source(dataMap);
        this.restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        return this.appName + " - " + this.appAge;
    }

    @RequestMapping("/hello")
    public String hello() {
        return this.appName + " - " + this.appAge;
    }

    @RequestMapping("/{userId}/list")
    public List<User> userList(@PathVariable("userId") Integer userId) {
        log.info("userList invoke");
        String resp = this.restTemplate.getForObject("https://httpbin.org/get", String.class);
        return this.userService.getUsers();
    }

    @RequestMapping("/list-rest")
    public String restGet() {
        log.info("userList invoke");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Customer", "rest-" + System.currentTimeMillis());
        org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange("https://httpbin.org/get", HttpMethod.GET, entity, String.class);
        return response.getBody();
//        return this.restTemplate.getForObject("https://httpbin.org/get", String.class, entity);
    }

    @RequestMapping("/{userId}/list-feign")
    public String feign0(@PathVariable("userId") Integer userId) {
        log.info("feign0 invoke");
        return mchService.getAllArea(0L, System.currentTimeMillis() + "");
    }

    @RequestMapping("/{userId}/list-webclient")
    public void list4WebClient(@PathVariable("userId") Integer userId) {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.scheme("https")
                        .host("httpbin.org")
                        .path("/get")
                        .queryParam("parentAreaCodeId", 0)
                        .build())
                .header("name", "ok-wei")
                .retrieve().bodyToMono(String.class).block();
    }

    @RequestMapping("/add")
    public User add() {
        return this.userService.addUser("akwei" + System.currentTimeMillis(), new Date());
    }

    @RequestMapping("/redis-set")
    public void redisSet(@RequestParam(value = "key") String key,
                         @RequestParam(value = "value") String value
    ) {
        this.redisTemplate.opsForValue().set(key, value);
    }

//    @RequestMapping("/redis-cluster-set")
//    public void redisSet(@RequestParam(value = "key") String key,
//                         @RequestParam(value = "value") String value
//    ) {
//        this.redisTemplate.clu.opsForValue().set(key, value);
//    }

    @RequestMapping("/redis-get")
    public String redisGet(@RequestParam(value = "key") String key) {
        return this.redisTemplate.opsForValue().get(key);
    }

    @RequestMapping("/redis-multi-get")
    public List<String> redisMultiGet(@RequestParam(value = "keys") String keyStr) {
        String[] split = keyStr.split(",");
        List<String> keys = new ArrayList<>(Arrays.asList(split));
        return this.redisTemplate.opsForValue().multiGet(keys);
    }

    @RequestMapping("/redis-del")
    public void redisDel(@RequestParam(value = "keys") String keyStr) {
        String[] split = keyStr.split(",");
        List<String> keys = new ArrayList<>(Arrays.asList(split));
        this.redisTemplate.delete(keys);
    }


    @RequestMapping("/mongo-insert")
    public void mongoInsert(@RequestParam(value = "userId", defaultValue = "0") Integer userId) {
        User user = User.builder().userId(userId).name("akweiwei").createTime(new Date()).build();
        this.mongoTemplate.insert(user, "batch_data");
        System.out.println("doc:" + user);
    }


    //reactive
//    @RequestMapping("/reactive-redis-get")
//    public void reactive_redisGet(@RequestParam(value = "key") String key) {
//        this.reactiveRedisTemplate.opsForValue().get(key).subscribe(x -> {
//            try {
//                System.out.println("\n\n\nbegin consume redis cache\n\n\n");
//                Thread.sleep(10000);
//            } catch (InterruptedException ignored) {
//            }
//            System.out.println(x);
//            System.out.println("\n\n\nend consume redis cache\n\n\n");
//        });
//    }


//    @PostMapping("/kafka/topics/{topic}")
//    public void kafkaSend(
//            @PathVariable(value = "topic") String topic,
//            @RequestBody KafkaMsg kafkaMsg
//    ) throws Exception {
//        log.info("kafkaSend invoke");
//        ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(topic, kafkaMsg.getKey(), kafkaMsg.getData());
//        SendResult<String, String> sendResult = future.get();
//        System.out.println(sendResult);
//    }

    @PostMapping("/rabbit/queues/{queue}")
    public void rabbitSend(
            @PathVariable(value = "queue") String queue,
            @RequestBody RabbitMqMsg rabbitMqMsg
    ) {
        log.info("rabbitSend invoke");
        this.rabbitTemplate.convertAndSend(queue, rabbitMqMsg);
    }

    @RequestMapping("/async-get")
    public Callable<String> asyncExec() {
        return () -> {
            log.info("begin async invoke call");
            TimeUnit.SECONDS.sleep(3);
            log.info("end async invoke call");
            return "Asnc invoke";
        };
    }

    @SneakyThrows
    @RequestMapping("/httpclient-get")
    public String httpClientGet() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://httpbin.org/get");
        String str;
        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            str = EntityUtils.toString(entity);
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity);
        }
        return str;
    }

    @SneakyThrows
    @RequestMapping("/httpclient5-get-async")
    public String httpClient5GetAsync() {
        final IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                .setSoTimeout(Timeout.ofSeconds(5))
                .build();

        final CloseableHttpAsyncClient client = HttpAsyncClients.custom()
                .setIOReactorConfig(ioReactorConfig)
                .build();

        client.start();

        final HttpHost target = new HttpHost("https", "httpbin.org");

        final SimpleHttpRequest request = SimpleRequestBuilder.get()
                .setHttpHost(target)
                .setPath("/get")
                .build();

        final Future<SimpleHttpResponse> future = client.execute(
                SimpleRequestProducer.create(request),
                SimpleResponseConsumer.create(),
                new FutureCallback<SimpleHttpResponse>() {

                    @Override
                    public void completed(final SimpleHttpResponse response) {
                        System.out.println(request + "->" + new StatusLine(response));
                        System.out.println(response.getBody());
                    }

                    @Override
                    public void failed(final Exception ex) {
                        System.out.println(request + "->" + ex);
                    }

                    @Override
                    public void cancelled() {
                        System.out.println(request + " cancelled");
                    }

                });
        SimpleHttpResponse response = future.get();
        client.close(CloseMode.GRACEFUL);
        return response.getBodyText();
    }

    @SneakyThrows
    @RequestMapping("/okhttp-get")
    public String okhttpGet() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://httpbin.org/get")
                .build();
        Headers headers = request.headers();
        System.out.println(headers);
        try (Response response = client.newCall(request).execute()) {
            return Objects.requireNonNull(response.body()).string();
        }
    }

    @SneakyThrows
    @RequestMapping("/okhttp-get-async")
    public String okhttpGetAsync() {
        final String[] result = {null};
        OkHttpClient client = new OkHttpClient();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Request request = new Request.Builder()
                .url("https://httpbin.org/get")
                .build();
        Headers headers = request.headers();
        System.out.println(headers);
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String s = Objects.requireNonNull(response.body()).string();
                System.out.println(s);
                result[0] = s;
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        return result[0];
    }
}
