        <%@ page import="java.util.Collection" %>
<%@ page import="ui.ItemInfo" %>
<%@ page import="bo.ItemHandler" %>
<%@ page import="java.util.Iterator" %><%--
  Created by IntelliJ IDEA.
  User: Jonathan
  Date: 2025-10-03
  Time: 16:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<% Collection<ItemInfo> items = ItemHandler.getAllItems();
    Iterator<ItemInfo> it = items.iterator();
    ItemInfo item = null;
    for (; it.hasNext(); ) {
        item = it.next();
    }%>
<%=item.getName()%> :
<%=item.getDescription()%><br>
<}%>
    }

</body>
</html>
