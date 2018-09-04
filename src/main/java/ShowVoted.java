import bean.Place;
import utils.Console;
import utils.VoteUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ShowVoted {

    public static void main(String[] args) {

        //场地名称:编号:权重
        String[] placeArray = {"北京西打磨场:11:30",
                "深圳Wedo蛇口国际社区:60:30",
                "北京优客工场Thinkplus智能办公空间:45:40",
                "北京长阳共享际:64:30",
                "共享际·鲜鱼口:91:100",
                "优客工场:179:100",
                "共享际:186:100"};

        HashMap<String, Place> placeHashMap = new HashMap<String, Place>();
        String[] strArray = null;

        //准备地点信息
        for (String str : placeArray) {
            strArray = str.split(":");

            Place place = new Place();
            place.setId(strArray[1]);
            place.setName(strArray[0]);

            placeHashMap.put(strArray[1], place);
        }

        Console console = new Console();
        console.init(placeArray.length);

        List<Place> list = new ArrayList<Place>();

        while (true) {

//            System.out.println("------------------------------------------------");
//            System.out.println(new Date());
//            System.out.println("------------------------------------------------");
//            System.out.println(String.format("%-46s%6s", "社区名称", "投票次数"));
//            System.out.println("------------------------------------------------");
            for (Map.Entry<String, Place> entry : placeHashMap.entrySet()) {
                try {
                    int result = VoteUtils.getVoteTimes(entry.getKey(), System.currentTimeMillis());

//                    String str = entry.getValue().getName();
//                    str = str.replaceAll("[a-z0-9A-Z]", "");
//
//                    int length = 50 - str.length();
//                    System.out.println(String.format("%-" + length + "s%10d%n", entry.getValue().getName(), result));

                    //创建场地数据的List对象，用于界面更新。
                    Place place = new Place();
                    place.setName(entry.getValue().getName());
                    place.setTimes(result);
                    list.add(place);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            console.refreshContainer(list);

            list.clear();

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        for (int i = 1; i <= 205; i++) {
//            try {
//                VoteUtils.getVoteTimes(Integer.toString(i), System.currentTimeMillis());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        HttpHost proxyHost = new HttpHost("119.5.1.15", 808, "http");
//        VoteUtils.vote("5", proxyHost);
//
//        String[] strArray = null;
//        String[] pArray = null;
//        HttpHost httpHost = null;
//        IPUtils ipUtil = new IPUtils();
//        List<IPTable> ipTableList = null;
//
//        while (true) {
//            //准备代理地址
//            ipUtil.prepareIPList();
//
//            DIPTable dipTable = new DIPTable();
//            ipTableList = dipTable.getIPTableByUsed(0);
//
//            if (ipTableList != null) {
//                System.out.println(String.format("共 %d 轮投票\n", ipTableList.size()));
//
//                //遍历获取到的IP端口列表
//                for (int i = 0; i < ipTableList.size(); i++) {
//                    int code = 0;
//                    IPTable ipTable = ipTableList.get(i);
//                    System.out.println(String.format("当前是第 %d 轮投票, 代理地址为: %s:%s\n", i + 1, ipTable.getIp(), ipTable.getPort()));
//                    //创建代理对象
//                    httpHost = new HttpHost(ipTable.getIp(), Integer.parseInt(ipTable.getPort()), "http");
//
//                    Random r = new Random();
//
//                    //遍历待投票数据:
//                    for (String item : placeArray) {
//                        //拆分 场地:场地编号:权重
//                        pArray = item.split(":");
//                        System.out.println(pArray[0]);
//
//                        //随机到权重值范围内进行投票
//                        if (r.nextInt(100) < Integer.parseInt(pArray[2])) {
//                            code += VoteUtils.vote(pArray[1], httpHost);
//                            //投票后暂停 2 - 4 秒, 模拟真实用户.
//                            Thread.sleep((int) ((Math.random() + 1) * 1000));
//                        } else {
//                            System.out.println("本次投票排除");
//                        }
//
//                    }
//                    //投票完成,代理地址标记为已使用.
//                    ipTable.setGood(code);
//                    ipTable.setUsed(1);
//                    dipTable.updateIPTable(ipTable);
//
//                    System.out.println();
//                }
//                ipTableList.clear();
//            }
////            Thread.sleep(2000);
//        }

    }

}
