package unittest.common;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import unittest.example.EmployeeDAO;

/**
 * 
 * 纯spring支持的测试
 * 约定的命名不用@Test注解在方法上: 测试类名后要带Test字符,方法名前要带test，否则不被junit运行(如方法名为TODO_testDummy不被运行)
 * @project benchmark
 * @author tony.shen
 * @date 2012-9-21 下午8:07:19
 * Copyright (C) 2010-2012 www.xxx.com Inc. All rights reserved.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
//@ContextConfiguration(classes = {targetTest.class})
public abstract class AbstractSpringMockitoTestSupport extends TestCase {

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
    }

    @Bean
    public BeanFactory mockedBeanFactory() {
        return Mockito.mock(BeanFactory.class);
    }

}
