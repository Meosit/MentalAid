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
    private static final String BLOCK_HEADER = "<div class='stars' data-init-value='#initValue' data-mark-count='#markCount'>";
    private static final String STAR_ELEMENT_TEMPLATE = "<i data-init-value='#percent' data-index='#index' class='cfi cfi--star stars__out #canVote'><i class='cfi cfi--star stars__in' style='width:#percent%;'></i></i>";
    private static final String NUMERIC_ELEMENT_TEMPLATE = "<i class='cfi value'>#value</i>";
    private static final String STATUS_ELEMENT = " <i class='cfi status glyphicon'></i>";
    private static final String BLOCK_FOOTER = "</div>";
    private float rating = 0;
    private int markCount;
    private int starCount = 5;
    private boolean canVote = false;
    private boolean showLabel = true;

    public void setValue(float value) {
        rating = value < 0 ? 0 : value;
    }

    public void setStarCount(int count) {
        starCount = count < 0 ? 0 : count;
    }

    public void setMarkCount(int markCount) {
        this.markCount = markCount;
    }

    public void setCanVote(boolean canVote) {
        this.canVote = canVote;
    }

    public void setShowLabel(boolean showLabel) {
        this.showLabel = showLabel;
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
            JspWriter out = getJspContext().getOut();
            if (starCount > 0) {
                out.write(getBlockHeader());
                for (int i = 1; i <= starCount; i++) {
                    out.write(getStarElement(i - rating <= 0 ? 1 : rating - i + 1, i));
                }
                if (showLabel) {
                    out.write(getNumericElement(rating));
                }
                out.write(STATUS_ELEMENT);
                out.write(BLOCK_FOOTER);
            }
        } catch (Exception e) {
            logger.error("Cannot generate stars block", e);
            throw new SkipPageException("Exception in generating stars block. ", e);
        }
    }

    private String getBlockHeader() {
        return BLOCK_HEADER
                .replace("#markCount", String.valueOf(markCount))
                .replace("#initValue", String.valueOf(rating));
    }

    private String getStarElement(float percent, int index) {
        percent = percent < 0 ? 0 : percent;
        percent = (float) (100 * (Math.asin(2 * percent - 1) / Math.PI + 0.5));
        percent = percent - 5;
        percent = percent < 0 ? 0 : percent;
        return STAR_ELEMENT_TEMPLATE
                .replace("#percent", String.format(Locale.ROOT, "%.2f", percent))
                .replace("#canVote", (canVote ? "can-vote" : ""))
                .replace("#index", String.valueOf(index));
    }

    private String getNumericElement(float value) {
        return NUMERIC_ELEMENT_TEMPLATE.replace("#value", String.format(Locale.ROOT, "%.1f", value));
    }
}
