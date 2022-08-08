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

@WebServlet(name = "getWifiServlet", value = "/getWifi-servlet")
public class getWifiServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Wifi wifi = new Wifi();
        response.setContentType("text/html");
        ApiService api = new ApiService();


        DbService dbService = new DbService();
        int count = dbService.saveAll();

        request.setAttribute("count", count);
//        wifi = dbService.getList();
//
//        System.out.println(wifi.getMGR_NO());



        RequestDispatcher rd = request.getRequestDispatcher("/getWifi.jsp");
        rd.forward(request, response);

    }

    public void destroy() {
    }
}