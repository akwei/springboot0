package akwei.app;

import lombok.SneakyThrows;
import org.apache.hc.client5.http.async.methods.*;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.http.message.StatusLine;
import org.apache.hc.core5.io.CloseMode;
import org.apache.hc.core5.reactor.IOReactorConfig;
import org.apache.hc.core5.util.Timeout;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class HttpClient5Test {

    @SneakyThrows
    @Test
    public void test1() {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("https://httpbin.org/get");
            // The underlying HTTP connection is still held by the response object
            // to allow the response content to be streamed directly from the network socket.
            // In order to ensure correct deallocation of system resources
            // the user MUST call CloseableHttpResponse#close() from a finally clause.
            // Please note that if response content is not fully consumed the underlying
            // connection cannot be safely re-used and will be shut down and discarded
            // by the connection manager.
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                System.out.println(response.getCode() + " " + response.getReasonPhrase());
                HttpEntity entity = response.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                EntityUtils.consume(entity);
            }

            HttpPost httpPost = new HttpPost("http://httpbin.org/post");
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("username", "vip"));
            nvps.add(new BasicNameValuePair("password", "secret"));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                System.out.println(response.getCode() + " " + response.getReasonPhrase());
                HttpEntity entity = response.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                EntityUtils.consume(entity);
            }
        }
    }

    @SneakyThrows
    @Test
    public void async() {
        TimeUnit.SECONDS.sleep(5);
        final IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                .setSoTimeout(Timeout.ofSeconds(5))
                .build();

        final CloseableHttpAsyncClient client = HttpAsyncClients.custom()
                .setIOReactorConfig(ioReactorConfig)
                .build();

        client.start();

        final HttpHost target = new HttpHost("httpbin.org");
        final String[] requestUris = new String[]{"/"
//                , "/ip", "/user-agent", "/headers"
        };

        for (final String requestUri : requestUris) {
            final SimpleHttpRequest request = SimpleRequestBuilder.get()
                    .setHttpHost(target)
                    .setPath(requestUri)
                    .build();

            System.out.println("Executing request " + request);
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
            future.get();
        }

        System.out.println("Shutting down");
        client.close(CloseMode.GRACEFUL);
    }
}
