package by.mksn.epam.mentalaid.util;

import by.mksn.epam.mentalaid.command.resource.Constants;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UrlUtilTest {
    /**
     * Attribute which contains client uri (in jsp page request object)
     */
    private static final String JSP_REQUEST_URI_ATTRIBUTE = "javax.servlet.forward.request_uri";
    /**
     * Attribute which contains servlet path in (jsp page request object)
     */
    private static final String JSP_SERVLET_PATH_ATTRIBUTE = "javax.servlet.forward.servlet_path";

    @Test
    public void getRequestUrlFromJspRequestObjectTest() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getQueryString()).thenReturn("cmd=profile&username=admin");
        when(request.getAttribute(JSP_REQUEST_URI_ATTRIBUTE)).thenReturn("/MentalAid/controller");

        String expected = "/MentalAid/controller?cmd=profile&username=admin";
        String actual = UrlUtil.getRequestUrl(request);
        assertEquals(expected, actual);
    }

    @Test
    public void getRequestUrlFromServletRequestObjectTest() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getQueryString()).thenReturn("cmd=profile&username=admin");
        when(request.getAttribute(JSP_REQUEST_URI_ATTRIBUTE)).thenReturn(null);
        when(request.getRequestURI()).thenReturn("/MentalAid/controller");

        String expected = "/MentalAid/controller?cmd=profile&username=admin";
        String actual = UrlUtil.getRequestUrl(request);
        assertEquals(expected, actual);
    }

    @Test
    public void getServletUrlFromJspRequestObjectTest() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("/MentalAid");
        when(request.getAttribute(JSP_SERVLET_PATH_ATTRIBUTE)).thenReturn("/controller");

        String expected = "/MentalAid/controller";
        String actual = UrlUtil.getServletUrl(request);
        assertEquals(expected, actual);
    }

    @Test
    public void getServletUrlFromServletRequestObjectTest() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("/MentalAid");
        when(request.getAttribute(JSP_SERVLET_PATH_ATTRIBUTE)).thenReturn(null);
        when(request.getServletPath()).thenReturn("/controller");

        String expected = "/MentalAid/controller";
        String actual = UrlUtil.getServletUrl(request);
        assertEquals(expected, actual);
    }

    @Test
    public void encodeUrlTest() throws Exception {
        String decodedUrl = "/MentalAid/controller?cmd=profile&username=admin";

        String expected = "%2FMentalAid%2Fcontroller%3Fcmd%3Dprofile%26username%3Dadmin";
        String actual = UrlUtil.encodeUrl(decodedUrl);
        assertEquals(expected, actual);
    }

    @Test
    public void decodeUrlTest() throws Exception {
        String encodedUrl = "%2FMentalAid%2Fcontroller%3Fcmd%3Dprofile%26username%3Dadmin";

        String expected = "/MentalAid/controller?cmd=profile&username=admin";
        String actual = UrlUtil.decodeUrl(encodedUrl);
        assertEquals(expected, actual);
    }

    @Test
    public void getBackRedirectUrlFromNullParameterTest() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(Constants.FROM_URL_PARAMETER)).thenReturn(null);

        when(request.getContextPath()).thenReturn("/MentalAid");
        when(request.getServletPath()).thenReturn("/controller");

        String expected = UrlUtil.getServletUrl(request);
        String actual = UrlUtil.getBackRedirectUrl(request);
        assertEquals(expected, actual);
    }

    @Test
    public void getBackRedirectUrlNonNullParameterTest() throws Exception {
        String expected = "/MentalAid/";

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(Constants.FROM_URL_PARAMETER)).thenReturn(expected);

        String actual = UrlUtil.getBackRedirectUrl(request);
        assertEquals(expected, actual);
    }

    @Test
    public void removeOneParameterFromUrlWithOneParameterTest() throws Exception {
        String urlWithParameter = "/MentalAid/controller?cmd=test";

        String expected = "/MentalAid/controller";
        String actual = UrlUtil.removeParameterFromUrl(urlWithParameter, "cmd");
        assertEquals(expected, actual);
    }

    @Test
    public void removeOneParameterFromUrlWithSeveralParametersTest() throws Exception {
        String urlWithParameter = "/MentalAid/controller?cmd=test&question_deleted=success";

        String expected = "/MentalAid/controller?question_deleted=success";
        String actual = UrlUtil.removeParameterFromUrl(urlWithParameter, "cmd");
        assertEquals(expected, actual);
    }

    @Test
    public void removeSeveralParametersFromUrlWithoutParametersTest() throws Exception {
        String urlWithoutParameters = "/MentalAid/controller";
        String[] parameterNames = {"param1", "param2", "param3"};

        String expected = "/MentalAid/controller";
        String actual = UrlUtil.removeParametersFromUrl(urlWithoutParameters, parameterNames);
        assertEquals(expected, actual);
    }

    @Test
    public void removeSeveralParametersFromUrlWithNotAllMatchedParametersTest() throws Exception {
        String urlWithoutParameters = "/MentalAid/controller?param1=val1&param3=val3";
        String[] parameterNames = {"param1", "param2", "param3"};

        String expected = "/MentalAid/controller";
        String actual = UrlUtil.removeParametersFromUrl(urlWithoutParameters, parameterNames);
        assertEquals(expected, actual);
    }

    @Test
    public void removeSeveralParametersFromUrlWithRandomOrderedParametersTest() throws Exception {
        String urlWithoutParameters = "/MentalAid/controller?param2=val2&param1=val1&param3=val3";
        String[] parameterNames = {"param1", "param2", "param3"};

        String expected = "/MentalAid/controller";
        String actual = UrlUtil.removeParametersFromUrl(urlWithoutParameters, parameterNames);
        assertEquals(expected, actual);
    }

    @Test
    public void addOneParameterToUrlWithoutParameters() throws Exception {
        String urlWithoutParameter = "/MentalAid/controller";
        String parameterName = "test1";
        String parameterValue = "test";

        String expected = "/MentalAid/controller?test1=test";
        String actual = UrlUtil.addParameterToUrl(urlWithoutParameter, parameterName, parameterValue);
        assertEquals(expected, actual);
    }

    @Test
    public void addOneParameterToUrlWithParameter() throws Exception {
        String urlWithoutParameter = "/MentalAid/controller?test2=test";
        String parameterName = "test1";
        String parameterValue = "test";

        String expected = "/MentalAid/controller?test2=test&test1=test";
        String actual = UrlUtil.addParameterToUrl(urlWithoutParameter, parameterName, parameterValue);
        assertEquals(expected, actual);
    }

    @Test
    public void getBaseUrlTest() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getQueryString()).thenReturn("cmd=profile&username=admin&page=23&query=test");
        when(request.getAttribute(JSP_REQUEST_URI_ATTRIBUTE)).thenReturn("/MentalAid/controller");

        String expected = "/MentalAid/controller?cmd=profile&username=admin";
        String actual = UrlUtil.getBaseUrl(request);
        assertEquals(expected, actual);
    }
}