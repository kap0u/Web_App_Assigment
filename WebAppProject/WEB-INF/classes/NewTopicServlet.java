import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.JSONObject;

public class NewTopicServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null)
            sb.append(line);
        JSONObject json = new JSONObject(sb.toString());

        String name = json.getString("name");
        String desc = json.getString("description");

        JSONObject responseJson = new JSONObject();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectdb", "root", "root");

            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO topics VALUES (null, ?, ?)"
            );
            stmt.setString(1, name);
            stmt.setString(2, desc);

            int inserted = stmt.executeUpdate();
            responseJson.put("message", inserted > 0 ? "Topic created!" : "Error!");

            stmt.close();
            conn.close();
        } catch (Exception e) {
            responseJson.put("message", "Error: " + e.getMessage());
        }

        res.setContentType("application/json");
        res.getWriter().write(responseJson.toString());
    }
}