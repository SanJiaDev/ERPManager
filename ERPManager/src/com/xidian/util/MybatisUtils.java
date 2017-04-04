package com.xidian.util;
import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


public class MybatisUtils {
	private static String conf = "conf.xml";
	private static SqlSessionFactory ssf = null;

	public static SqlSession getSqlSession(boolean flag){
		//加载mybatis的配置文件（它也加载关联的映射文件）
		InputStream is = MybatisUtils.class.getClassLoader().getResourceAsStream(conf);
		//构建sqlSession的工厂
		ssf = new SqlSessionFactoryBuilder().build(is);
		//创建能执行映射文件中sql的sqlSession 设置为true，自动提交事务
		SqlSession ss = ssf.openSession(flag);

		return ss;
	}
}
