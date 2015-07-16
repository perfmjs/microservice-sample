package unittest.example;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created by tony on 15/7/16.
 */
@Component
public class TaxDao {

    public BigDecimal getTaxPercentageForYear(long employeeId) {
        return new BigDecimal(1);
    }
}
