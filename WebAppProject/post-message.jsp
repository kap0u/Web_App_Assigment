<%@ page import="java.sql.*, javax.servlet.http.*, javax.servlet.*" %>
<%
Integer userId = (Integer) session.getAttribute("userId");
if (userId == null) {
    response.sendRedirect("login.html");
    return;
}

Class.forName("com.mysql.cj.jdbc.Driver");
Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectdb", "root", "root");
PreparedStatement stmt = conn.prepareStatement("SELECT * FROM topics ORDER BY id");
ResultSet rs = stmt.executeQuery();
%>

<html>
<head><title>Post Message</title></head>
<body>
<h2>Post a Message</h2>
<a href="new-topic.html" class="form-button">➕ Create New Topic</a>
<br><br>
<form method="post" action="post-message">
  <label>Topic:</label>
  <select name="topic_id">
    <% while (rs.next()) { %>
      <option value="<%= rs.getInt("id") %>"><%= rs.getString("name") %></option>
    <% } %>
  </select><br>
  <textarea name="msg" rows="4" cols="50" placeholder="Write your message"></textarea><br>
  <input type="submit" value="Post">
</form>
<link rel="stylesheet" href="style.css">
</body>
</html>

<%
rs.close();
stmt.close();
conn.close();
%>
