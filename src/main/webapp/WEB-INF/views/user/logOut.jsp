<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>logOut</title>
</head>
<body>
<%

    session.setAttribute("logIn", null);
    session.invalidate();
    response.sendRedirect("/index.jsp");

%>

</body>
</html>