package by.mksn.epam.bidbuy.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter used to initialize default locale
 */
@WebFilter(urlPatterns = {"/*"}, initParams = {@WebInitParam(name = "defaultLocale", value = "ru")})
public class LocaleFilter implements Filter {

    private String defaultLocale;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        defaultLocale = filterConfig.getInitParameter("defaultLocale");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        if (session.getAttribute("locale") == null) {
            session.setAttribute("locale", defaultLocale);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        defaultLocale = null;
    }
}