package test.microserver;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import com.ajaxjson.JSONMessage;
import example.mybatis.lottery.mapper.BetGameIssuesMapper;
import example.youyue.service.SampleController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.PlatformTransactionManager;
import unittest.common.AbstractMockTestBuilder;

/**
 * Created by tony on 15/7/15.
 */
public class SampleControllerTest {

    @InjectMocks private SampleController sampleController;
    @Mock private BetGameIssuesMapper betGameIssuesMapper;
    @Mock private PlatformTransactionManager lotteryTransactionManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSampleHome() {
        new AbstractMockTestBuilder.DefaultMockTestBuilder() {
            private JSONMessage result;
            @Override
            public void buildExpectations() {
                when(betGameIssuesMapper.insert(anyObject())).thenThrow(Exception.class);
            }
            @Override
            public void buildExecutation() {
                result = sampleController.insert();
            }
            @Override
            public void buildVerify() {
                assertThat(result.getStatus(), equalTo("fail"));
            }
        }.runTest();
    }

}
