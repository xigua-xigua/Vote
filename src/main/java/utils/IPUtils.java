package utils;

import bean.IPTable;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

public class IPUtils {

    /*
    KY 2018.8.29

    调动快代理API获取IP地址, 以下链接每次获取10个IP, 当日IP不重复. 返回结果为JSON

    http://dev.kdlapi.com/api/getproxy/?orderid=973554528529784&num=100&area=%E5%9B%BD%E5%86%85&b_pcchrome=1&b_pcie=1&b_pcff=1&b_android=1&b_iphone=1&b_ipad=1&protocol=1&method=1&an_an=1&an_ha=1&quality=1&dedup=1&format=json&sep=1
     */

    //    private static String apiUrl = "http://dev.kdlapi.com/api/getproxy/?orderid=973554528529784&num=100&area=%E5%9B%BD%E5%86%85&b_pcchrome=1&b_pcie=1&b_pcff=1&b_android=1&b_iphone=1&b_ipad=1&protocol=1&method=1&an_an=1&an_ha=1&quality=1&dedup=1&format=json&sep=1"; //api链接
    private static String apiUrl = "http://dev.kdlapi.com/api/getproxy/?orderid=983619897899137&num=10&area=%E5%9B%BD%E5%86%85&b_pcchrome=1&b_pcie=1&b_pcff=1&b_android=1&b_iphone=1&b_ipad=1&protocol=1&method=1&an_an=1&an_ha=1&sort=2&dedup=1&format=json&sep=1";

    public JSONArray getIPList() {

        JSONArray ipList = null;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(apiUrl);

            System.out.println("Executing request " + httpget.getURI());
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine()); //获取Reponse的返回码
//                System.out.println(EntityUtils.toString(response.getEntity())); //获取API返回内容

                String json = EntityUtils.toString(response.getEntity());
//                System.out.println(json);
//                System.out.println();

                int code = JSON.parseObject(json).getShort("code");

                if (code == 3) {
                    System.out.println("暂时没有代理可用");
                } else {
                    JSONObject jsonObject = JSON.parseObject(json).getObject("data", JSONObject.class);
//                int count = jsonObject.getShort("count");
//                System.out.println(count);
                    ipList = (jsonObject.getJSONArray("proxy_list"));
                    jsonObject.clear();
                }

            } finally {
                response.close();
            }
        } catch (IOException ioe) {
            System.out.println("Get IP error!");
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ipList;
    }

    //取出未使用的IP地址
    public List<IPTable> getUseableIPList() {

        //准备数据库操作对象
        DIPTable dipTable = new DIPTable();
        List<IPTable> ipTableList = dipTable.getIPTableByUsed(0);

        return ipTableList;
    }

    //获取代理地址并存入数据库, IP去重.
    public int prepareIPList() {

        JSONArray ipList = null;
        int count = 0;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(apiUrl);

            System.out.println(String.format("Executing request %s", httpget.getURI()));
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                System.out.println("----------------------------------------");
                System.out.println("正在准备代理");
                System.out.println(response.getStatusLine()); //获取Reponse的返回码

                String json = EntityUtils.toString(response.getEntity());

                int code = JSON.parseObject(json).getShort("code");

                if (code == 3) {
                    System.out.println("暂时没有代理可用");
                } else {
                    JSONObject jsonObject = JSON.parseObject(json).getObject("data", JSONObject.class);
                    ipList = (jsonObject.getJSONArray("proxy_list"));
                    jsonObject.clear();

                    String[] strArray = null;

                    //准备数据库操作对象
                    DIPTable dipTable = new DIPTable();

                    for (Object object : ipList) {
                        IPTable ipTable = new IPTable();
                        strArray = object.toString().split(":");

                        ipTable.setIp(strArray[0]);
                        ipTable.setPort(strArray[1]);
                        ipTable.setUsed(0);

                        dipTable.insertIPTable(ipTable);

                        count++;
                    }
                }

            } finally {
                response.close();
            }
        } catch (IOException ioe) {
            System.out.println("Get IP error!");
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return count;
    }
}
