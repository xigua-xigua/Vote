import bean.IPTable;
import utils.DIPTable;
import utils.IPUtils;
import utils.VoteUtils;
import com.alibaba.fastjson.JSONArray;
import org.apache.http.HttpHost;

import java.util.List;
import java.util.Random;

public class Client {

    public static void main(String[] args) throws Exception {

        //场地名称:编号:权重
        String[] placeArray = {"北京西打磨场:11:30",
                "深圳Wedo蛇口国际社区:60:30",
                "北京优客工场Thinkplus智能办公空间:45:30",
                "北京长阳共享际:64:30",
                "共享际·鲜鱼口:91:100",
                "优客工场:179:100",
                "共享际:186:100",
                "北京西打磨场:11:100"};

//        String[] placeArray = {"共享际:186:100"};

//        for (int i = 1; i <= 205; i++) {
//            getMessage(Integer.toString(i), System.currentTimeMillis());
//        }
//        HttpHost proxyHost = new HttpHost("119.5.1.15", 808, "http");
//        VoteUtils.vote("5", proxyHost);

        String[] strArray = null;
        String[] pArray = null;
        HttpHost httpHost = null;
        IPUtils ipUtil = new IPUtils();
        List<IPTable> ipTableList = null;

        while (true) {
            //准备代理地址
            ipUtil.prepareIPList();

            DIPTable dipTable = new DIPTable();
            ipTableList = dipTable.getIPTableByUsed(0);

            if (ipTableList != null) {
                System.out.println(String.format("共 %d 轮投票\n", ipTableList.size()));

                //遍历获取到的IP端口列表
                for (int i = 0; i < ipTableList.size(); i++) {
                    int code = 0;
                    IPTable ipTable = ipTableList.get(i);
                    System.out.println(String.format("当前是第 %d 轮投票, 代理地址为: %s:%s\n", i + 1, ipTable.getIp(), ipTable.getPort()));
                    //创建代理对象
                    httpHost = new HttpHost(ipTable.getIp(), Integer.parseInt(ipTable.getPort()), "http");

                    Random r = new Random();

                    //遍历待投票数据:
                    for (String item : placeArray) {
                        //拆分 场地:场地编号:权重
                        pArray = item.split(":");
                        System.out.println(pArray[0]);

                        //随机到权重值范围内进行投票
                        if (r.nextInt(100) < Integer.parseInt(pArray[2])) {
                            code += VoteUtils.vote(pArray[1], httpHost);
                            //投票后暂停 1 - 2 秒, 模拟真实用户.
                            Thread.sleep((int) ((Math.random() + 1) * 1000));
                        } else {
                            System.out.println("本次投票排除");
                        }

                    }
                    //投票完成,代理地址标记为已使用.
                    ipTable.setGood(code);
                    ipTable.setUsed(1);
                    dipTable.updateIPTable(ipTable);

                    System.out.println();
                }
                ipTableList.clear();
            }
            Thread.sleep(5000);
        }

    }

}  