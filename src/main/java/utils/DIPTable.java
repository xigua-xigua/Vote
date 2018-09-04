package utils;

import bean.IPTable;
import mapper.*;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class DIPTable extends DBase{
    /**
     * 通过id获取代理地址信息
     * @param id
     * @return
     */
    public IPTable getIPTable(int id){
        IPTable ipTable = null;
        SqlSession session = openSession();
        try{
            IPTableMapper ipTableMapper = session.getMapper(IPTableMapper.class);
            ipTable = ipTableMapper.selectIPTableById(id);
        }
        finally{
            session.close();
        }

        return ipTable;
    }

    /**
     * 通过used获取代理地址信息
     * @param used
     * @return
     */
    public List<IPTable> getIPTableByUsed(int used){
        List<IPTable> list = null;

        SqlSession session = openSession();
        try{
            IPTableMapper ipTableMapper = session.getMapper(IPTableMapper.class);
            list = ipTableMapper.selectIPTableByUsed(used);
        }
        finally{
            session.close();
        }

        return list;
    }

    /**
     * 新增代理地址
     * @param ipTable
     */
    public void insertIPTable(IPTable ipTable){
        SqlSession session = openSession();
        try{
            IPTableMapper ipTableMapper = session.getMapper(IPTableMapper.class);
            ipTableMapper.insertIPTable(ipTable);
            session.commit();
        }
        catch (Exception e) {
            ;
        }
        finally{
            session.close();
        }
    }

    /**
     * 删除代理地址
     * @param id
     */
    public void delIPTable(int id){
        SqlSession session = openSession();
        try{
            IPTableMapper ipTableMapper = session.getMapper(IPTableMapper.class);
            ipTableMapper.deleteIPTable(id);
            session.commit();
        }
        finally{
            session.close();
        }
    }

    public int updateIPTable(IPTable ipTable) {
        SqlSession session = openSession();
        int ret = 0;

        try {
            IPTableMapper ipTableMapper = session.getMapper(IPTableMapper.class);
            ret = ipTableMapper.updateIPTable(ipTable);
            session.commit();
        }
        finally {
            session.close();
        }

        return ret;
    }
}