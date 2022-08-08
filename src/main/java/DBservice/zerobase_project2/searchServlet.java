package DBservice.zerobase_project2;

import APIservice.ApiService;
import DBservice.DbService;
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

@WebServlet(name = "searchServlet", value = "/search-servlet")
public class searchServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Wifi> wifis = new ArrayList<>();
        response.setContentType("text/html");

        String LAT = request.getParameter("LAT");
        String LNT = request.getParameter("LNT");
        System.out.println(LAT + ", " + LNT);


        DbService dbService = new DbService();

        if(LAT.length() != 0 && LNT.length() != 0) {
            dbService.addToHistory(LAT, LNT); //히스토리에 조회기록 남기기.
        }

        wifis = dbService.getList(LAT, LNT); // 이걸 jsp 파일에 뿌려줘야한다.
        request.setAttribute("wifis", wifis);

        RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
        rd.forward(request, response);


    }

    public void destroy() {
    }
}