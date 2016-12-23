package by.mksn.epam.mentalaid.tag;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import static by.mksn.epam.mentalaid.util.NullUtil.isNullDefault;

/**
 * Tag to format price, more simple and shorter than fmt:numberFormat
 */
public class PriceFormatTag extends SimpleTagSupport {

    private static final Logger logger = Logger.getLogger(PriceFormatTag.class);

    private static final String PATTERN = "#,###.####";
    private static final DecimalFormat FORMAT;
    private static final String DEFAULT_CURRENCY_SIGN = "$";

    static {
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator(' ');
        FORMAT = new DecimalFormat(PATTERN, symbols);
    }


    private BigDecimal price;
    private String currencySign;

    public void setValue(BigDecimal value) {
        price = value;
    }

    public void setCurrencySign(String currencySign) {
        this.currencySign = currencySign;
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
            String formattedPrice = FORMAT.format(price);
            formattedPrice = isNullDefault(currencySign, DEFAULT_CURRENCY_SIGN) + formattedPrice;
            getJspContext().getOut().write(formattedPrice);
        } catch (Exception e) {
            logger.warn("Cannot format price (" + price + ")", e);
            throw new SkipPageException("Exception in formatting price. ", e);
        }
    }
}
