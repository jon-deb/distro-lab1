package servlet;

import bo.CartHandler;
import bo.User;
import ui.ItemInfo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * CartServlet — manages cart actions and displays cart content.
 * Handles add/remove/set/clear actions via POST and forwards to cart.jsp on GET.
 */

@WebServlet(name = "cartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login?return=" + req.getContextPath() + "/cart");
            return;
        }

        String action = req.getParameter("action");
        String itemIdStr = req.getParameter("itemId");
        String qtyStr = req.getParameter("qty");

        int itemId = 0;
        int qty = 1;

        try {
            if (itemIdStr != null) itemId = Integer.parseInt(itemIdStr);
            if (qtyStr != null) qty = Integer.parseInt(qtyStr);
        } catch (NumberFormatException ignore) {}

        ItemInfo itemStub = (itemId > 0) ? new ItemInfo("", "", itemId, 0, 0.0) : null;

        if ("add".equalsIgnoreCase(action) && itemStub != null) {
            if (qty < 1) qty = 1;
            for (int i = 0; i < qty; i++) CartHandler.add(user, itemStub);

        } else if ("remove".equalsIgnoreCase(action) && itemStub != null) {
            CartHandler.remove(user, itemStub);

        } else if ("setQty".equalsIgnoreCase(action) && itemId > 0) {
            CartHandler.setQty(user, itemId, qty);

        } else if ("clear".equalsIgnoreCase(action)) {
            CartHandler.clear(user);
        }

        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login?return=" + req.getContextPath() + "/cart");
            return;
        }

        List<ItemInfo> items = CartHandler.listItems(user);
        Map<Integer, Integer> qtyMap = CartHandler.quantities(user);
        double total = CartHandler.totalPrice(user);

        req.setAttribute("items", items);
        req.setAttribute("qtyMap", qtyMap);
        req.setAttribute("total", total);

        req.getRequestDispatcher("/WEB-INF/view/cart.jsp").forward(req, resp);
    }
}
