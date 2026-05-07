import javax.servlet.http.*;
import java.io.*;
import org.json.JSONObject;

public class ServletUtil {
    public static JSONObject readJSON(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null)
            sb.append(line);
        return new JSONObject(sb.toString());
    }

    public static void writeJSON(HttpServletResponse response, JSONObject json) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(json.toString());
    }
}