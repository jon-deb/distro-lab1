<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Webshop</title>
    <style>
        html, body { height: 100%; margin: 0; }
        body {
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: system-ui, sans-serif;
        }
        .center { text-align: center; }
        a { margin: 0 10px; }
    </style>
</head>
<body>
<div class="center">
    <h1>Web Shop!</h1>
    <p>
        <a href="${pageContext.request.contextPath}/items">Items</a>
        <a href="${pageContext.request.contextPath}/login">Log in</a>
    </p>
</div>
</body>
</html>
