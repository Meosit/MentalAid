package by.mksn.epam.mentalaid.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static by.mksn.epam.mentalaid.command.resource.Constants.LOCALE_ATTRIBUTE;

/**
 * Filter used to initialize default locale
 */
@WebFilter(urlPatterns = {"/*"}, initParams = {
        @WebInitParam(name = "defaultLocale", value = "en"),
        @WebInitParam(name = "supportedLocales", value = "en,ru")
})
public class LocaleFilter implements Filter {

    private String defaultLocale;
    private List<String> supportedLocales;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        defaultLocale = filterConfig.getInitParameter("defaultLocale");
        supportedLocales = Arrays.asList(filterConfig.getInitParameter("supportedLocales").split(","));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        String locale = (String) session.getAttribute(LOCALE_ATTRIBUTE);
        if (locale == null || !supportedLocales.contains(locale)) {
            session.setAttribute(LOCALE_ATTRIBUTE, defaultLocale);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        defaultLocale = null;
    }
}