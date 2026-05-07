import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UpdatePasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String username = req.getParameter("username");
        String oldpass = req.getParameter("oldpass");
        String newpass = req.getParameter("newpass1");

        String oldHash = Util.hash(oldpass);
        String newHash = Util.hash(newpass);

        Connection conn = null;
        PreparedStatement selectStmt = null;
        PreparedStatement updateStmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectdb", "root", "root");

            // 1. Έλεγχος αν υπάρχει ο χρήστης με τα δεδομένα που έδωσε
            selectStmt = conn.prepareStatement(
                "SELECT id FROM users WHERE uname = ? AND upasshash = ?"
            );
            selectStmt.setString(1, username);
            selectStmt.setString(2, oldHash);
            rs = selectStmt.executeQuery();

            res.setContentType("text/html");

            if (rs.next()) {
                int userId = rs.getInt("id");

                // 2. Ενημέρωση με τον νέο κωδικό
                updateStmt = conn.prepareStatement(
                    "UPDATE users SET upasshash = ? WHERE id = ?"
                );
                updateStmt.setString(1, newHash);
                updateStmt.setInt(2, userId);

                int updated = updateStmt.executeUpdate();
                res.getWriter().println(updated > 0
                    ? "Password updated successfully!"
                    : "Failed to update password.");

            } else {
                res.getWriter().println("Incorrect username or password.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().println("Error: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (selectStmt != null) selectStmt.close(); } catch (Exception ignored) {}
            try { if (updateStmt != null) updateStmt.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }
    }
}