import bean.IPTable;
import org.apache.http.HttpHost;
import utils.DIPTable;
import utils.IPUtils;
import utils.VoteBufanbizUtils;
import utils.VoteUtils;

import java.util.List;
import java.util.Random;

public class BufanbizClient {

    public static void main(String[] args) throws Exception {

        //场地名称:编号:权重
        String[] placeArray = {"优客工场:85:100"};

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
                            int totalVoteCount = (int) (Math.random() * 20) + 10;
                            for (int voteCount = 0; voteCount < totalVoteCount; voteCount++) {
                                System.out.print(voteCount + "/" + totalVoteCount + ":");
                                int retCode = VoteBufanbizUtils.voteThread(pArray[1], httpHost);
                                code += retCode;
                                //投票后暂停 1 - 2 秒, 模拟真实用户.
                                Thread.sleep((int) ((Math.random() + 1) * 1000));
                                if (retCode == 0) {
                                    break;
                                }
                            }
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
            Thread.sleep(2000);
        }

    }

}  