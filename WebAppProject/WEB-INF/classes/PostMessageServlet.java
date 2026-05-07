import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class PostMessageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            res.sendRedirect("login.html");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        int topicId = Integer.parseInt(req.getParameter("topic_id"));
        String msg = req.getParameter("msg");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectdb", "root", "root");

            PreparedStatement insertStmt = conn.prepareStatement(
                "INSERT INTO messages VALUES (null, ?, ?, ?, null)"
            );
            insertStmt.setInt(1, topicId);
            insertStmt.setInt(2, userId);
            insertStmt.setString(3, msg);
            insertStmt.executeUpdate();

            PreparedStatement countStmt = conn.prepareStatement(
                "SELECT count(*) FROM messages WHERE topic_id = ?"
            );
            countStmt.setInt(1, topicId);
            ResultSet rs = countStmt.executeQuery();

            res.setContentType("text/html");
            if (rs.next()) {
                int count = rs.getInt(1);
                res.getWriter().println("Message posted! Total messages for this topic: " + count);
            }

            rs.close();
            insertStmt.close();
            countStmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().println("Error: " + e.getMessage());
        }
    }
}