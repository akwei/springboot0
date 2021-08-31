package akwei.app;

import lombok.SneakyThrows;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class OkHttpTest {

    @Test
    public void prev() {
        System.out.println(OkHttpClient.Builder.class.getName());
    }

    @SneakyThrows
    @Test
    public void test1() {
        TimeUnit.SECONDS.sleep(3);
        OkHttpClient client = new OkHttpClient();
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

            }
        });

        try (Response response = call.execute()) {
            String s = Objects.requireNonNull(response.body()).string();
            System.out.println(s);
        }
    }

    @SneakyThrows
    @Test
    public void async() {
        TimeUnit.SECONDS.sleep(3);
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
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
    }
}
