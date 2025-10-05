package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import bo.ItemHandler;
import ui.ItemInfo;
/**
 * ItemServlet — displays all available items and allows adding to cart.
 * Handles both guest and logged-in users with login redirect when needed.
 */
@WebServlet(name = "itemServlet", urlPatterns = {"/items"})
public class ItemServlet extends HttpServlet {

    private static String esc(String s) {
        if (s == null) return "";
        return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Collection<ItemInfo> items = ItemHandler.getAllItems();

        HttpSession session = req.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("user") != null);

        String ctx = req.getContextPath();
        String returnUrl = ctx + "/items";
        String loginUrl = ctx + "/login?return=" + URLEncoder.encode(returnUrl, StandardCharsets.UTF_8);

        resp.setContentType("text/html; charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Items</title>");
            out.println("<script>function pleaseLogin(url){ alert('Log in to add to cart'); window.location=url; }</script>");
            out.println("</head><body>");

            if(loggedIn) {
                out.println("<p><a href='" + ctx + "/cart'>Cart</a> | "
                        + "<a href='" + ctx + "/logout'>Logout</a></p>");
            }
            else {
                out.println("<p><a href='" + ctx + "/cart'>Cart</a> | "
                        + "<a href='" + ctx + "/login'>Login</a> | ");
            }

            out.println("<h2>All items</h2><ul>");
            for (ItemInfo it : items) {
                out.println("<li>");
                out.printf("%d — <strong>%s</strong> — %.2f — %s",
                        it.getId(), esc(it.getName()), it.getPrice(), esc(it.getDescription()));
                if (loggedIn) {
                    out.println("<form method='post' action='" + ctx + "/cart' style='display:inline;margin-left:10px;'>");
                    out.println("<input type='hidden' name='action' value='add'/>");
                    out.println("<input type='hidden' name='itemId' value='" + it.getId() + "'/>");
                    out.println("<input type='number' name='qty' value='1' min='1' style='width:60px;'/>");
                    out.println("<button type='submit'>Add to cart</button>");
                    out.println("</form>");
                } else {
                    out.println("<button style='margin-left:10px;' onclick=\"pleaseLogin('" + loginUrl + "')\">Add to cart</button>");
                }

                out.println("</li>");
            }
            out.println("</ul>");
            out.println("</body></html>");
        }
    }
}
