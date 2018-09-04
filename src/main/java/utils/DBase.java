package utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class DBase{
    private static SqlSessionFactory factory = null;
    public DBase(){
        if (factory != null) return;

//        InputStream is = this.getClass().getClassLoader().getResourceAsStream("mybatis.cfg.xml");
        try {
            Reader reader = Resources.getResourceAsReader("mybatis.cfg.xml");
            factory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取SqlSession实例，使用后需调用实例的close()函数释放资源
     * @return
     */
    protected SqlSession openSession(){
        return factory.openSession();
    }
}