package utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VoteUtils {

    /*
    通过代理投票
     */
    public static int vote(String id, HttpHost proxyHost) {

        long time = System.currentTimeMillis();
        int code = 0;

        CloseableHttpClient client = HttpClients.createDefault();

        try {
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("callback", "jQuery321009318847824570642_1535525041227"));
            formparams.add(new BasicNameValuePair("voteId", "1894"));
            formparams.add(new BasicNameValuePair("id", id));
            formparams.add(new BasicNameValuePair("_", Long.toString(time)));
            HttpEntity reqEntity = new UrlEncodedFormEntity(formparams, "utf-8");

            HttpHost target = new HttpHost("active.163.com", 80, "http");
//        HttpHost proxy = new HttpHost("119.5.1.15", 808, "http");

            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(2000)//一、连接超时：connectionTimeout-->指的是连接一个url的连接等待时间
                    .setSocketTimeout(2000)// 二、读取数据超时：SocketTimeout-->指的是连接上一个url，获取response的返回等待时间
                    .setConnectionRequestTimeout(2000)
                    .setProxy(proxyHost)
                    .build();

            HttpGet get = new HttpGet(String.format("/service/vote/v1/1894/digg.jsonp?voteId=1894&id=%s", id));
            get.setHeader("Host", "active.163.com");
            get.setHeader("Referer", "http://m.house.163.com/fps/frontends/house_special/vote2018/vote.html");
            get.setHeader("Pragma", "no-cache");
            get.setConfig(requestConfig);

            CloseableHttpResponse response = client.execute(target, get);

            try {
                if (response.getStatusLine().getStatusCode() == 200) {
                    HttpEntity resEntity = response.getEntity();
                    String message = EntityUtils.toString(resEntity, "utf-8");
                    System.out.println(message);
                    code = 1;
                } else {
                    System.out.println(response.getStatusLine().getStatusCode());
//            HttpEntity resEntity = response.getEntity();
//            String message = EntityUtils.toString(resEntity, "gb2312");
//            System.out.println(message);
                    System.out.println("请求失败");
                }
            } catch (Exception ioe) {
                System.out.println("Vote got some errors!");
            } finally {
                response.close();
            }

        } catch (Exception ioe) {
            System.out.println("Vote got some errors!");
        } finally {
            try {
                client.close();
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }

        return code;
    }

    /*
    获取票数信息
     */
    public static int getVoteTimes(String id, long time) throws Exception {

        int result = 0;

        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//        formparams.add(new BasicNameValuePair("callback", "jQuery321009318847824570642_1535525041227"));
        formparams.add(new BasicNameValuePair("voteId", "1894"));
        formparams.add(new BasicNameValuePair("id", id));
        formparams.add(new BasicNameValuePair("_", Long.toString(time)));
//        formparams.add(new BasicNameValuePair("_", "1535525041231"));
        HttpEntity reqEntity = new UrlEncodedFormEntity(formparams, "utf-8");

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)//一、连接超时：connectionTimeout-->指的是连接一个url的连接等待时间
                .setSocketTimeout(5000)// 二、读取数据超时：SocketTimeout-->指的是连接上一个url，获取response的返回等待时间
                .setConnectionRequestTimeout(5000)
                .build();

        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost post = new HttpPost("http://active.163.com/service/vote/v1/1894.jsonp");
        post.setEntity(reqEntity);
        post.setConfig(requestConfig);
        HttpResponse response = client.execute(post);

        //获取结果
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity resEntity = response.getEntity();
            String message = EntityUtils.toString(resEntity, "utf-8");

            result = Integer.parseInt((message.substring(message.indexOf("value\":{\"") + 8, message.indexOf("},\"type")).split(":")[1]));
//            System.out.println(message);
        } else {
            System.out.println("请求失败");
        }

        return result;
    }
}
