<%--
  Created by IntelliJ IDEA.
  User: Jonathan
  Date: 2025-10-03
  Time: 16:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Login</title></head>
<body>
<h2>Log in</h2>

<form method="post" action="${pageContext.request.contextPath}/login">
    <label>Username:
        <input type="text" name="username" required />
    </label><br/>
    <label>Password:
        <input type="password" name="password" required />
    </label><br/>
    <button type="submit">Log in</button>
</form>

<p style="color:red;">
    ${error}
</p>

<p>
    <a href="${pageContext.request.contextPath}/">Tillbaka</a>
</p>
</body>
</html>


