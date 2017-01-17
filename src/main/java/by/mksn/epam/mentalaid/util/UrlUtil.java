package by.mksn.epam.mentalaid.util;

import by.mksn.epam.mentalaid.command.resource.Constants;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import static by.mksn.epam.mentalaid.util.NullUtil.isNull;
import static by.mksn.epam.mentalaid.util.NullUtil.isNullDefault;
import static by.mksn.epam.mentalaid.util.StringUtil.isNullOrEmpty;

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
    private static final String JSP_SERVLET_PATH_ATTRIBUTE = "javax.servlet.forward.servlet_path";

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
     * Returns servlet url such as {@code /ContextPath/ServletUrl}
     * Works at the JSP as well.
     *
     * @param request {@link HttpServletRequest} from where this url will be taken
     * @return real servlet url
     */
    public static String getServletUrl(HttpServletRequest request) {
        String url = request.getContextPath();
        url += isNullDefault((String) request.getAttribute(JSP_SERVLET_PATH_ATTRIBUTE), request.getServletPath());
        return url;
    }

    /**
     * Encodes string to pass them as a GET parameter in address line
     *
     * @param string string to encode
     * @return encoded string
     */
    public static String encodeUrl(String string) {
        try {
            return URLEncoder.encode(isNullDefault(string, ""), URL_CHARSET);
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
            return URLDecoder.decode(isNullDefault(string, ""), URL_CHARSET);
        } catch (UnsupportedEncodingException e) {
            logger.error("Unsupported character encoding: " + URL_CHARSET);
        }
        return null;
    }

    /**
     * Get's parameter with name {@link Constants#FROM_URL_PARAMETER} and
     * decodes them for future back redirecting.<br>
     * If there is no {@link Constants#FROM_URL_PARAMETER} in this request,
     * result of {@link #getServletUrl(HttpServletRequest)} will be returned.
     *
     * @param request {@link HttpServletRequest} from where url will be taken
     * @return url for back-redirecting
     */
    public static String getBackRedirectUrl(HttpServletRequest request) {
        String fromParameter = request.getParameter(Constants.FROM_URL_PARAMETER);
        String redirectUrl;
        if (!isNullOrEmpty(fromParameter)) {
            redirectUrl = fromParameter;
        } else {
            redirectUrl = getServletUrl(request);
        }
        return redirectUrl;
    }

    /**
     * Removes GET parameter from the url
     *
     * @param url           url where parameter will be removed
     * @param parameterName name of the parameter to remove
     * @return url without parameter;
     */
    public static String removeParameterFromUrl(String url, String parameterName) {
        if (!isNullOrEmpty(url)) {
            return url
                    .replaceAll(parameterName + "=[^&]*?[&]?", "")
                    .replace("&&", "&");
        } else {
            return "";
        }
    }

}
