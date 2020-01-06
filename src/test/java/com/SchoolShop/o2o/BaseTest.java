package com.SchoolShop.o2o;


import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//用来配置spring和Junit整合，junit启动时加载SpringIOC容器
@RunWith(SpringJUnit4ClassRunner.class)
//告诉Junitspring配置文件的位置
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class BaseTest {
	
}
