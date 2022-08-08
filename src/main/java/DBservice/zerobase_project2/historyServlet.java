package DBservice.zerobase_project2;

import DBservice.DbService;
import DTO.History;
import DTO.Wifi;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "historyServlet", value = "/history-servlet")
public class historyServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<History> histories = new ArrayList<>();
        response.setContentType("text/html");

        DbService dbService = new DbService();

        //삭제요청이 있을경우 삭제하기
        String id = request.getParameter("id");
        if (id != null) {
            dbService.deleteHistoryById(Integer.parseInt(id));
        }


        histories = dbService.getHistories();
        request.setAttribute("histories", histories);

        RequestDispatcher rd = request.getRequestDispatcher("/history.jsp");
        rd.forward(request, response);

    }

    public void destroy() {
    }
}