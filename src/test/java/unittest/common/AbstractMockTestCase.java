package unittest.common;


import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/** 
 * Mock测试抽象父类,模拟接口行为很方便
 * @project webclient
 * @author tony.shen
 * @date 2010-11-22
 * Copyright (C) 2010-2012 www.xxx.com Inc. All rights reserved.
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractMockTestCase {

    protected Mockery context;

    @Before
    public void setUp() {
        context = new JUnit4Mockery();
    }
    
    @After
    public void tearDown() {
        context = null;
    }
    
    public <T> T mockInterface(Mockery context, Class<T> typeToMock) {
        return context.mock(typeToMock);
    }

    public <T> T mockInterface(Mockery context, Class<T> typeToMock, String name) {
        return context.mock(typeToMock, name);
    }

    public <T> T mockInterfaceByMockito(Class<T> classToMock) {
        return Mockito.mock(classToMock);
    }

    public <T> T mockInterfaceByMockito(Class<T> classToMock, String name) {
        return Mockito.mock(classToMock, name);
    }

    /**
     * 测试真实对象
     * @param <T>
     * @param object
     * @return
     */
    public <T> T mockSpyObjectByMockito(T object) {
        return Mockito.spy(object);
    }

}
