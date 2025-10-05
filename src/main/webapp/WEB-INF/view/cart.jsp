<%--
  Created by IntelliJ IDEA.
  User: Jonathan
  Date: 2025-10-03
  Time: 16:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, ui.ItemInfo" %>
<%
    List<ItemInfo> items = (List<ItemInfo>) request.getAttribute("items");
    Map<Integer,Integer> qtyMap = (Map<Integer,Integer>) request.getAttribute("qtyMap");
    double total = (request.getAttribute("total") != null) ? (Double) request.getAttribute("total") : 0.0;
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cart</title>
</head>
<body>
<h2>Shopping cart</h2>

<p>
    <a href="<%= ctx %>/items">Continue shopping</a> |
    <a href="<%= ctx %>/logout">Logout</a>
</p>

<%
    if (items == null || items.isEmpty()) {
%>
<p>Your cart is empty.</p>
<%
} else {
%>
<table border="1" cellpadding="6" cellspacing="0">
    <tr>
        <th>Name</th>
        <th>Price</th>
        <th>Qty</th>
        <th>Subtotal</th>
        <th>Action</th>
    </tr>
    <%
        for (ItemInfo it : items) {
            int id = it.getId();
            int qty = (qtyMap != null) ? qtyMap.getOrDefault(id, 0) : 0;
            double lineTotal = it.getPrice() * qty;
    %>
    <tr>
        <td><%= it.getName() %></td>
        <td><%= String.format("%.2f", it.getPrice()) %></td>
        <td>
            <form method="post" action="<%= ctx %>/cart" style="display:inline;">
                <input type="hidden" name="action" value="setQty"/>
                <input type="hidden" name="itemId" value="<%= id %>"/>
                <input type="number" name="qty" value="<%= qty %>" min="0" style="width:60px;">
                <button type="submit">Update</button>
            </form>
        </td>
        <td><%= String.format("%.2f", lineTotal) %></td>
        <td>
            <form method="post" action="<%= ctx %>/cart" style="display:inline;">
                <input type="hidden" name="action" value="remove"/>
                <input type="hidden" name="itemId" value="<%= id %>"/>
                <button type="submit">Remove 1</button>
            </form>
        </td>
    </tr>
    <%
        }
    %>
</table>

<p><strong>Total:</strong> <%= String.format("%.2f", total) %></p>

<form method="post" action="<%= ctx %>/cart">
    <input type="hidden" name="action" value="clear"/>
    <button type="submit">Clear cart</button>
</form>
<%
    }
%>
</body>
</html>

