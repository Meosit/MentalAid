package by.mksn.epam.mentalaid.tag;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Tag to format price, more simple and shorter than fmt:numberFormat
 */
public class BootstrapPaginationBlockTag extends SimpleTagSupport {

    private static final Logger logger = Logger.getLogger(BootstrapPaginationBlockTag.class);

    private static final String BLOCK_HEADER = "<nav aria-label='Page navigation'><ul class='pagination'>";
    private static final String BLOCK_FOOTER = "</ul></nav>";
    private static final String BLOCK_PAGE_NUMBER_TEMPLATE = "<li class='#active'><a href='#url'>#number</a></li>";
    private static final String BLOCK_PREV_TEMPLATE = "<li><a href='#url' aria-label='Previous'><span aria-hidden='true'>&larr;</span></a></li>";
    private static final String BLOCK_NEXT_TEMPLATE = "<li><a href='#url' aria-label='Next'><span aria-hidden='true'>&rarr;</span></a></li>";
    private static final String BLOCK_NUMBER_RANGE_PLACEHOLDER = "<li><a>...</a></li>";

    private String baseUrl;
    private int currentPageIndex;
    private int pageCount;

    private static String fastReplace(String str, String target, String replacement) {
        int targetLength = target.length();
        if (targetLength == 0) {
            return str;
        }
        int idx2 = str.indexOf(target);
        if (idx2 < 0) {
            return str;
        }
        StringBuilder buffer = new StringBuilder(targetLength > replacement.length() ? str.length() : str.length() * 2);
        int idx1 = 0;
        do {
            buffer.append(str, idx1, idx2);
            buffer.append(replacement);
            idx1 = idx2 + targetLength;
            idx2 = str.indexOf(target, idx1);
        } while (idx2 > 0);
        buffer.append(str, idx1, str.length());
        return buffer.toString();
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
            JspWriter out = getJspContext().getOut();
            out.write(BLOCK_HEADER);
            if (pageCount == 1) {
                out.write(getPageNumberBlock(1));
            } else {
                if (currentPageIndex != 1) {
                    out.write(getPrevBlock());
                }

                if (currentPageIndex >= 3) {
                    out.write(getPageNumberBlock(1));
                }

                if (currentPageIndex >= 4) {
                    out.write(BLOCK_NUMBER_RANGE_PLACEHOLDER);
                }

                out.write(getCurrentPageBlockAndNearest());

                if (pageCount - currentPageIndex >= 3) {
                    out.write(BLOCK_NUMBER_RANGE_PLACEHOLDER);
                }

                if (pageCount - currentPageIndex >= 2) {
                    out.write(getPageNumberBlock(pageCount));
                }

                if (currentPageIndex != pageCount) {
                    out.write(getNextBlock());
                }
            }
            out.write(BLOCK_FOOTER);
        } catch (Exception e) {
            logger.error(e);
            throw new SkipPageException("Exception in formatting price. ", e);
        }
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPageIndex = currentPage;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    private String getCurrentPageBlockAndNearest() {
        String result = "";
        if (currentPageIndex != 1) {
            result += getPageNumberBlock(currentPageIndex - 1);
        }
        result += getPageNumberBlock(currentPageIndex);
        if (currentPageIndex != pageCount) {
            result += getPageNumberBlock(currentPageIndex + 1);
        }
        return result;
    }

    private String getPageNumberBlock(int pageIndex) {
        String block = fastReplace(BLOCK_PAGE_NUMBER_TEMPLATE, "#number", pageIndex + "");
        block = fastReplace(block, "#active", (currentPageIndex == pageIndex) ? "active" : "");
        return fastReplace(block, "#url", baseUrl + pageIndex);
    }

    private String getPrevBlock() {
        String url = baseUrl + ((currentPageIndex == 1) ? 1 : (currentPageIndex - 1));
        return fastReplace(BLOCK_PREV_TEMPLATE, "#url", url);
    }

    private String getNextBlock() {
        String url = baseUrl + ((currentPageIndex == pageCount) ? pageCount : (currentPageIndex + 1));
        return fastReplace(BLOCK_NEXT_TEMPLATE, "#url", url);
    }

}
