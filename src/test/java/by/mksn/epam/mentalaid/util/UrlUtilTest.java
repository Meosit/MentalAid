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
    public void getRequestUrlTest() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getQueryString()).thenReturn("cmd=profile&username=admin");
        when(request.getAttribute(JSP_REQUEST_URI_ATTRIBUTE)).thenReturn(null);
        when(request.getRequestURI()).thenReturn("/MentalAid/controller");

        assertEquals("/MentalAid/controller?cmd=profile&username=admin", UrlUtil.getRequestUrl(request));
    }

    @Test
    public void getServletUrlTest() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("/MentalAid");
        when(request.getAttribute(JSP_SERVLET_PATH_ATTRIBUTE)).thenReturn("/controller");

        assertEquals("/MentalAid/controller", UrlUtil.getServletUrl(request));
    }

    @Test
    public void encodeUrlTest() throws Exception {
        String decodedUrl = "/MentalAid/controller?cmd=profile&username=admin";
        String expected = "%2FMentalAid%2Fcontroller%3Fcmd%3Dprofile%26username%3Dadmin";

        assertEquals(expected, UrlUtil.encodeUrl(decodedUrl));
    }

    @Test
    public void decodeUrlTest() throws Exception {
        String encodedUrl = "%2FMentalAid%2Fcontroller%3Fcmd%3Dprofile%26username%3Dadmin";
        String expected = "/MentalAid/controller?cmd=profile&username=admin";

        assertEquals(expected, UrlUtil.decodeUrl(encodedUrl));
    }

    @Test
    public void getBackRedirectUrlTest1() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(Constants.FROM_URL_PARAMETER)).thenReturn(null);

        when(request.getContextPath()).thenReturn("/MentalAid");
        when(request.getServletPath()).thenReturn("/controller");

        assertEquals(UrlUtil.getServletUrl(request), UrlUtil.getBackRedirectUrl(request));
    }

    @Test
    public void getBackRedirectUrlTest2() throws Exception {
        String fromParameter = "/MentalAid/";

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(Constants.FROM_URL_PARAMETER)).thenReturn(fromParameter);

        assertEquals(fromParameter, UrlUtil.getBackRedirectUrl(request));
    }

    @Test
    public void removeParameterFromUrl() throws Exception {
        String urlWithParameter = "/MentalAid/controller?cmd=test";
        String urlWithoutParameter = "/MentalAid/controller";

        assertEquals(urlWithoutParameter, UrlUtil.removeParameterFromUrl(urlWithParameter, "cmd"));
    }

    @Test
    public void addParameterToUrl() throws Exception {
        String expected = "/MentalAid/controller?test1=test&test2=test";

        String urlWithoutParameter = "/MentalAid/controller";
        String actual = UrlUtil.addParameterToUrl(urlWithoutParameter, "test1", "test");
        actual = UrlUtil.addParameterToUrl(actual, "test2", "test");

        assertEquals(expected, actual);
    }

}