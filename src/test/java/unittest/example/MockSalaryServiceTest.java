package unittest.example;

/**
 * Created by tony on 15/7/16.
 * 参考：
 * http://rdafbn.blogspot.com/2014/01/testing-spring-components-with-mockito.html
 * http://docs.mockito.googlecode.com/hg/latest/org/mockito/Spy.html
 * http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html
 * https://www.jiwhiz.com/blogs/Unit_Test_RESTful_API_with_Spring_MVC_Test_Framework
 */
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import unittest.common.AbstractMockTestBuilder;
import unittest.common.AbstractSpringMockitoTestSupport;

@ContextConfiguration(classes = {MockSalaryServiceTest.class})
public class MockSalaryServiceTest extends AbstractSpringMockitoTestSupport {

    private static final long UserId = 123l;

    @InjectMocks
    private SalaryService salaryService;
    @Mock
    private EmployeeDAO employeeDAO;
    @Mock
    private TaxCalculator taxCalculator;

    @Test
    public void testMinimumSalary() {
        new AbstractMockTestBuilder.DefaultMockTestBuilder() {
            private BigDecimal actual;

            @Override
            public void buildSetup() {
            }

            @Override
            public void buildExecutation() {
                BigDecimal annualSalary = new BigDecimal(10000);
                when(employeeDAO.getAnnualSalary(UserId)).thenReturn(annualSalary);
                when(taxCalculator.calculateTaxes(annualSalary)).thenReturn(new BigDecimal(1000));
                actual = salaryService.getNetSalary(UserId);
            }

            @Override
            public void buildVerify() {
                assertThat(actual.compareTo(new BigDecimal(10000)), is(0));
            }
        }.runTest();
    }

    @Test
    public void testMaximumSalary() {
        new AbstractMockTestBuilder.DefaultMockTestBuilder() {
            private BigDecimal actual;

            @Override
            public void buildSetup() {
                super.buildSetup();
            }

            @Override
            public void buildExpectations() {
                super.buildExpectations();
            }

            @Override
            public void buildExecutation() {
                BigDecimal annualSalary = new BigDecimal(80000);
                when(employeeDAO.getAnnualSalary(UserId)).thenReturn(annualSalary);
                when(taxCalculator.calculateTaxes(annualSalary)).thenReturn(new BigDecimal(8000));
                actual = salaryService.getNetSalary(UserId);
            }

            @Override
            public void buildVerify() {
                assertThat(actual.compareTo(new BigDecimal(72000)), is(0));
            }
        }.runTest();

    }
}