package unittest.example;

/**
 * Created by tony on 15/7/16.
 */
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaxCalculator {

    @Autowired
    private TaxDao taxDao;

    public BigDecimal calculateTaxes(BigDecimal salary) {
        BigDecimal result = salary.multiply(taxDao.getTaxPercentageForYear(2014));
        // some other weird calculation ....
        return result;
    }
}