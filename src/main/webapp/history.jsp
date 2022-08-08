<%@ page import="java.util.List" %>
<%@ page import="DTO.Wifi" %>
<%@ page import="DTO.History" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<style>
    table {width: 100%; border: 1px solid black; border-collapse: collapse;}
    th {border: 1px solid black; border-collapse: collapse; text-align: center; font-weight: 500; color: #fff; padding: 5px 0; background-color: mediumseagreen; height: 30px}
    td {border: 1px solid black; border-collapse: collapse; text-align: left; font-weight: 400; height: 30px}
</style>

<script type="text/javascript">
    /*
    현재 위치 가져오기
    */
    function getLocation() {
        navigator.geolocation.getCurrentPosition(function(pos) {
            var latitude = pos.coords.latitude;
            var longitude = pos.coords.longitude;
            document.getElementById("LAT").value = latitude;
            document.getElementById("LNT").value = longitude;
        });
    }

</script>


<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "위치 히스토리 목록" %></h1>

<a href="index.jsp">홈 |</a> <a href="history.jsp">위치 히스토리 목록 |</a> <a href="getWifi-servlet">Open API 와이파이 정보 가져오기</a>
<br/>
<br/>


<table>
    <tr>
        <th>ID</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>조회일자</th>
        <th>비고</th>
    </tr>
    <% List<History> histories = (List<History>) request.getAttribute("histories");
        if(histories != null) {
            for(History history : histories) {
    %>
    <tr>
        <td><%= history.getId() %></td>
        <td><%= history.getLat() %></td>
        <td><%= history.getLon() %></td>
        <td><%= history.getSearch_dt() %></td>
        <td>
            <div style="display:block">
                <form action="history-servlet">
                    <input type="hidden" name="id"value=<%= history.getId() %> />
                    <input style="text-align:center; display:block; margin: 0 auto;" type="submit" value="삭제" />
                </form>
            </div>
        </td>
    </tr>
    <%}%>
    <%} else {%>
    <tr>
        <td colspan="17" style="text-align: center; font-weight: 500; height: 60px">위치 정보를 입력한 후에 조회해 주세요.</td>
    </tr>

    <%}%>
</table>

</body>
</html>