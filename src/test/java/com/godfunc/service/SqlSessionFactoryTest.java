package com.godfunc.service;


import com.godfunc.entity.User;
import com.godfunc.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.*;
import java.sql.*;
import java.util.List;

public class SqlSessionFactoryTest {

    /**
     * build()的执行
     * @throws IOException
     */
    @Test
    public void buildConfigurationTest() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
    }

    /**
     * openSession() 的执行
     * @throws IOException
     */
    @Test
    @Ignore
    public void openSessionTest() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
    }

    /**
     * select 的执行
     * @throws IOException
     */
    @Test
    @Ignore
    public void executorSelectTest() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectUser(2L);
    }

    @Test
    @Ignore
    public void executorJdbcSelectTest() throws SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mybatis", "root", "q12345678.");
        PreparedStatement preparedStatement = connection.prepareStatement("select * from t_user where id = ?");
        preparedStatement.setLong(1, 1L);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            User user = new User();
            String idLabel = resultSet.getMetaData().getColumnLabel(1);
            Method idSet = user.getClass().getDeclaredMethod("set" + idLabel.substring(0, 1).toUpperCase() + idLabel.substring(1), Long.class);
            long id = resultSet.getLong(1);
            idSet.invoke(user, id);

            String nameLabel = resultSet.getMetaData().getColumnLabel(2);
            Method setName = user.getClass().getDeclaredMethod("set" + nameLabel.substring(0, 1).toUpperCase() + nameLabel.substring(1), String.class);
            String name = resultSet.getString(2);
            setName.invoke(user, name);

            String ageLabel = resultSet.getMetaData().getColumnLabel(3);
            Method setAge = user.getClass().getDeclaredMethod("set" + ageLabel.substring(0, 1).toUpperCase() + ageLabel.substring(1), Integer.class);
            int age = resultSet.getInt(3);
            setAge.invoke(user, age);

        }
    }

    /**
     * 本地缓存和二级缓存
     * @throws IOException
     */
    @Test
    @Ignore
    public void cacheTest() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectUser(2L);

        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
        User user2 = userMapper2.selectUser(2L);
    }

    @Test
    public void test2() {
        Class<DefaultObjectFactory> clazz = DefaultObjectFactory.class;
        Method[] declaredMethods = clazz.getDeclaredMethods();
        Constructor<?>[] constructors = clazz.getConstructors();
        Field[] declaredFields = clazz.getDeclaredFields();
    }

    @Test
    public void test3() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ClassLoader classLoader = SqlSessionFactoryBuilder.class.getClassLoader();
        Class<?> clazz = Class.forName("com.godfunc.entity.User");
        Object o = clazz.getDeclaredConstructors()[0].newInstance();
        System.out.println(clazz);
        System.out.println(o);
    }

    /**
     * jdk动态代理测试
     */
    @Test
    public void jdkProxyTest() {
        UserMapper userMapper = (UserMapper)Proxy.newProxyInstance(UserMapper.class.getClassLoader(), new Class[]{UserMapper.class}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if(method.getName().equals("selectUser")) {
                    System.out.println("selectUser");
                } else  if(method.getName().equals("selectList")) {
                    System.out.println("selectList");
                } else {
                    System.out.println("others");
                    method.invoke(this, args);
                }
                return null;
            }
        });
        User user = userMapper.selectUser(1L);
        List<User> users = userMapper.selectList();
    }


}
