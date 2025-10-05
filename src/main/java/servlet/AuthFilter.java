package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;
/**
 * AuthFilter — protects /cart endpoint by checking user session.
 * Redirects unauthenticated users to /login.
 */
@WebFilter(urlPatterns = {"/cart"})
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("user") != null);

        if (loggedIn) chain.doFilter(request, response);
        else ((HttpServletResponse) response).sendRedirect(req.getContextPath() + "/login");
    }
}