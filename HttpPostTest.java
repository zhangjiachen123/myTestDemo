package approve.integrate;


import com.alibaba.druid.support.json.JSONUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:Zhang jc
 * @date: 2018/9/27 13:40
 * @description:
 */
public class HttpPostTest implements Runnable {

    private static void doPost(String postUrl, String data) {
        //创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(postUrl);
        StringEntity entity = new StringEntity(data, Charset.forName("UTF-8"));
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        try {
            //执行post请求
            CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpPost);
            System.out.println("返回status" + httpResponse.getStatusLine());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("post异常");
        } finally {
            try {
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        Map param = new HashMap();
        param.put("userId", "123");
        String data = JSONUtils.toJSONString(param);
        String postUrl = "http://www.baidu.com";
        int i = 0;
        while (true) {
            i++;
            System.out.println("当前线程" + Thread.currentThread().getName() + "请求开始第" + i + "次");
            doPost(postUrl, data);
            System.out.println("当前线程" + Thread.currentThread().getName() + "请求第" + i + "次成功!!!!");
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            HttpPostTest httpPostTest = new HttpPostTest();
            Thread thread = new Thread(httpPostTest);
            thread.start();
        }
    }
}
