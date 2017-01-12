package by.mksn.epam.mentalaid.util;

import by.mksn.epam.mentalaid.command.resource.Constants;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import static by.mksn.epam.mentalaid.util.NullUtil.isNull;
import static by.mksn.epam.mentalaid.util.NullUtil.isNullDefault;

/**
 * Util class for simpler working with URL's
 */
public final class UrlUtil {

    private static final Logger logger = Logger.getLogger(UrlUtil.class);
    /**
     * Url encoding/decoding charset
     */
    private static final String URL_CHARSET = "UTF-8";
    /**
     * Attribute which contains client uri (in jsp page request object)
     */
    private static final String JSP_REQUEST_URI_ATTRIBUTE = "javax.servlet.forward.request_uri";

    private UrlUtil() {
    }

    /**
     * Returns real request url with all parameters
     *
     * @param request {@link HttpServletRequest} from where this url will be taken
     * @return real request url with all parameters
     */
    public static String getRequestUrl(HttpServletRequest request) {
        String parameters = request.getQueryString();
        String uri = (String) request.getAttribute(JSP_REQUEST_URI_ATTRIBUTE);
        uri = isNullDefault(uri, request.getRequestURI());
        return uri + (isNull(parameters) ? "" : ("?" + parameters));
    }

    /**
     * Encodes string to pass them as a GET parameter in address line
     *
     * @param string string to encode
     * @return encoded string
     */
    public static String encodeUrl(String string) {
        try {
            return URLEncoder.encode(string, URL_CHARSET);
        } catch (UnsupportedEncodingException e) {
            logger.error("Unsupported character encoding: " + URL_CHARSET);
        }
        return null;
    }

    /**
     * Decodes string, received from GET parameter in address line, to normal string
     *
     * @param string string to decode
     * @return decoded string
     */
    public static String decodeUrl(String string) {
        try {
            return URLDecoder.decode(string, URL_CHARSET);
        } catch (UnsupportedEncodingException e) {
            logger.error("Unsupported character encoding: " + URL_CHARSET);
        }
        return null;
    }

    /**
     * Get's parameter with name {@link Constants#FROM_URL_PARAMETER} and
     * decodes them for future back redirecting.<br>
     * If there is no {@link Constants#FROM_URL_PARAMETER} in this request,
     * {@link #getRequestUrl(HttpServletRequest)} of this request will be returned.
     *
     * @param request {@link HttpServletRequest} from where url will be taken
     * @return url for back-redirecting
     */
    public static String getBackRedirectUrl(HttpServletRequest request) {
        String fromParameter = request.getParameter(Constants.FROM_URL_PARAMETER);
        String redirectUrl;
        if (!StringUtil.isNullOrEmpty(fromParameter)) {
            redirectUrl = decodeUrl(fromParameter);
        } else {
            redirectUrl = getRequestUrl(request);
        }
        return redirectUrl;
    }

}
