package by.mksn.epam.mentalaid.tag;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Locale;

public class StarRatingTag extends SimpleTagSupport {

    private static final Logger logger = Logger.getLogger(StarRatingTag.class);
    private static final String BLOCK_HEADER = "<div class='stars'>";
    private static final String STAR_ELEMENT_BEFORE_VALUE = "<i class='cfi cfi--star stars__out'><i class='cfi cfi--star stars__in' style='width: ";
    private static final String STAR_ELEMENT_AFTER_VALUE = "%;'></i></i> ";
    private static final String NUMERIC_ELEMENT_BEFORE_VALUE = "<i class='cfi value'>";
    private static final String NUMERIC_ELEMENT_AFTER_VALUE = "</i>";
    private static final String BLOCK_FOOTER = "</div>";
    private float rating = 0;
    private int starCount = 5;

    public void setValue(float value) {
        rating = value < 0 ? 0 : value;
    }

    public void setStarCount(int count) {
        starCount = count < 0 ? 0 : count;
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
            JspWriter out = getJspContext().getOut();
            if (starCount > 0) {
                out.write(BLOCK_HEADER);
                for (int i = 1; i <= starCount; i++) {
                    out.write(getStarElement(i - rating <= 0 ? 1 : rating - i + 1));
                }
                out.write(getNumericElement(rating));
                out.write(BLOCK_FOOTER);
            }
        } catch (Exception e) {
            logger.error("Cannot generate stars block", e);
            throw new SkipPageException("Exception in generating stars block. ", e);
        }
    }

    private String getStarElement(float percent) {
        percent = percent < 0 ? 0 : percent;
        percent = (float) (100 * (Math.asin(2 * percent - 1) / Math.PI + 0.5));
        percent = percent - 5;
        return STAR_ELEMENT_BEFORE_VALUE
                + String.format(Locale.ROOT, "%.2f", percent)
                + STAR_ELEMENT_AFTER_VALUE;
    }

    private String getNumericElement(float value) {
        return NUMERIC_ELEMENT_BEFORE_VALUE
                + String.format(Locale.ROOT, "%.1f", value)
                + NUMERIC_ELEMENT_AFTER_VALUE;
    }
}
