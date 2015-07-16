package unittest.example;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.BeanFactory;
import unittest.common.AbstractMockTestBuilder;
import unittest.common.AbstractMockTestCase;

public class MockitoJUnitTest extends AbstractMockTestCase {

    @Mock private BeanFactory mockedBeanFactory;
    @Mock private MockedInterface mockedInterface;
    
    @Before
    public void setUp() {
        super.setUp();
    }
    
    @After
    public void tearDown() {
        super.tearDown();
    }
    
    @Test
    public void testUsingMockito() {
        new AbstractMockTestBuilder.DefaultMockTestBuilder() {
            @Override
            public void buildSetup() {
            }
            @Override
            public void buildExpectations() {
                when(mockedBeanFactory.getBean("mockedInterface", MockedInterface.class)).thenReturn(mockedInterface);
            }
            @Override
            public void buildExecutation() {
            }
            @Override
            public void buildVerify() {
                Assert.assertSame(mockedBeanFactory.getBean("mockedInterface", MockedInterface.class), mockedInterface);
                verify(mockedBeanFactory).getBean("mockedInterface", MockedInterface.class);
            }
        }.runTest();
    }
    
    @Test
    public void testUsingTestBuilder() {
         new AbstractMockTestBuilder.DefaultMockTestBuilder() { 
            private MockedInterface mockedIF;
            
            @Override
            public void buildSetup() {
                mockedIF = mockInterfaceByMockito(MockedInterface.class);
            }
            @Override
            public void buildExecutation() {
                mockedIF.hello();
            }
            @Override
            public void buildVerify() {
                verify(mockedIF).hello();
            }
        }.runTest();
    }
   
}
