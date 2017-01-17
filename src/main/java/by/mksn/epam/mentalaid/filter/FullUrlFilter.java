package by.mksn.epam.mentalaid.filter;

import by.mksn.epam.mentalaid.command.resource.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static by.mksn.epam.mentalaid.util.UrlUtil.encodeUrl;
import static by.mksn.epam.mentalaid.util.UrlUtil.getRequestUrl;

/**
 * Filter sets to request attribute with it's encoded full url including parameters
 */
@WebFilter(urlPatterns = {"/*"})
public class FullUrlFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setAttribute(Constants.FULL_URL_ATTRIBUTE,
                encodeUrl(getRequestUrl((HttpServletRequest) servletRequest)));
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
