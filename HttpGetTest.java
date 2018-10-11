package approve.integrate;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.Random;

public class HttpGetTest implements Runnable {

    private static void doGet(String getUrl) {
        //创建HttpClientBuilder
        try {
            Random random = new Random();
            int i = (random.nextInt(99)) + 1;
            String ip = "10.22.40." + String.valueOf(i);
            System.getProperties().setProperty("http.proxyHost", ip);
            System.out.println("ip" + ip);
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
            HttpGet httpGet = new HttpGet(getUrl);
            httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            httpGet.setHeader("Accept-Encoding", "gzip, deflate");
            httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
            httpGet.setHeader("Cache-Control", "max-age=0");
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3534.4 Safari/537.36");
            CloseableHttpResponse execute = closeableHttpClient.execute(httpGet);
            System.out.println("status返回:" + execute.getStatusLine());
            closeableHttpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Get异常");
        }
    }

    @Override
    public void run() {
        String postUrl = "http://www.docin.com/p-665252143.html";
        int i = 0;
        while (true) {
            i++;
            System.out.println("当前线程" + Thread.currentThread().getName() + "请求开始第" + i + "次");
            doGet(postUrl);
            System.out.println("当前线程" + Thread.currentThread().getName() + "请求第" + i + "次成功!!!!");
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            HttpGetTest httpPostTest = new HttpGetTest();
            Thread thread = new Thread(httpPostTest);
            thread.start();
        }
    }
}
