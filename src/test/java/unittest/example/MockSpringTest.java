package unittest.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import unittest.common.AbstractMockTestBuilder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by tony on 15/7/15.
 */
public class MockSpringTest {

    @Mock private MockedInterface mockedInterface;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testUsingTestBuilder() {
        new AbstractMockTestBuilder.DefaultMockTestBuilder() {
            private String result;

            @Override
            public void buildSetup() {
                when(mockedInterface.hello()).thenReturn("hello123");
            }

            @Override
            public void buildExecutation() {
                result = mockedInterface.hello();
            }

            @Override
            public void buildVerify() {
                assertEquals(result , "hello123");
            }
        }.runTest();
    }
}
