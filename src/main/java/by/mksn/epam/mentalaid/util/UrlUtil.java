package by.mksn.epam.mentalaid.util;

import by.mksn.epam.mentalaid.command.resource.Constants;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import static by.mksn.epam.mentalaid.command.resource.Constants.*;
import static by.mksn.epam.mentalaid.util.NullUtil.ifNullDefault;
import static by.mksn.epam.mentalaid.util.NullUtil.isNull;
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
    /**
     * Attribute which contains servlet path in (jsp page request object)
     */
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
        uri = ifNullDefault(uri, request.getRequestURI());
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
        url += ifNullDefault((String) request.getAttribute(JSP_SERVLET_PATH_ATTRIBUTE), request.getServletPath());
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
            return URLEncoder.encode(ifNullDefault(string, ""), URL_CHARSET);
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
            return URLDecoder.decode(ifNullDefault(string, ""), URL_CHARSET);
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
     * @return url without parameter
     */
    public static String removeParameterFromUrl(String url, String parameterName) {
        if (!isNullOrEmpty(url)) {
            url = url.replaceAll("&?" + parameterName + "=[^&]*", "")
                    .replaceAll("\\?&", "?")
                    .replaceAll("\\?$", "");
        }
        return url;
    }

    /**
     * Removes GET parameters from the url
     *
     * @param url           url where parameters will be removed
     * @param parameterNames array of parameter names to remove
     * @return url without parameters
     */
    public static String removeParametersFromUrl(String url, String... parameterNames) {
        for (String parameterName : parameterNames) {
            url = removeParameterFromUrl(url, parameterName);
        }
        return url;
    }

    /**
     * Adds GET parameter to the url query string
     *
     * @param url            url to add new parameter
     * @param parameterName  name of new parameter
     * @param parameterValue new parameter value
     * @return url with added parameter
     */
    public static String addParameterToUrl(String url, String parameterName, String parameterValue) {
        if (!isNullOrEmpty(url)) {
            url += url.contains("?")
                    ? (url.trim().charAt(url.length() - 1) != '?') ? "&" : ""
                    : "?";
            url += parameterName + "=" + parameterValue;
        }
        return url;
    }

    /**
     * Removes temp parameters such as <br>{@link Constants#PAGE_INDEX_PARAMETER},
     * <br>{@link Constants#SEARCH_QUERY_PARAMETER},
     * <br>{@link Constants#QUESTION_DELETED_PARAMETER}
     * <br>from the specified url
     *
     * @param request request from which url will be generated
     * @return base url
     */
    public static String getBaseUrl(HttpServletRequest request) {
        String baseUrl = getRequestUrl(request);
        return removeParametersFromUrl(baseUrl,
                SEARCH_QUERY_PARAMETER,
                QUESTION_DELETED_PARAMETER,
                PAGE_INDEX_PARAMETER);
    }

}
