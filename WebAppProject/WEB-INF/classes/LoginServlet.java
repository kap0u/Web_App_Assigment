import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String hash = Util.hash(password);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectdb", "root", "root");

            PreparedStatement stmt = conn.prepareStatement(
                "SELECT id FROM users WHERE uname = ? AND upasshash = ?"
            );
            stmt.setString(1, username);
            stmt.setString(2, hash);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");
                HttpSession session = req.getSession();
                session.setAttribute("userId", userId);
                res.sendRedirect("post-message.jsp");
            } else {
                res.sendRedirect("login.html");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().println("Error: " + e.getMessage());
        }
    }
}