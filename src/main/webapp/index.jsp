<%@ page import="java.util.List" %>
<%@ page import="DTO.Wifi" %>
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
    <h1><%= "와이파이 정보 구하기" %></h1>

    <a href="index.jsp">홈 |</a> <a href="history-servlet">위치 히스토리 목록 |</a> <a href="getWifi-servlet">Open API 와이파이 정보 가져오기</a>
    <br/>
    <br/>

    <form action="search-servlet">
        LAT: <input type="text" id="LAT" name="LAT" value="${test}"  size="20">
        LNT: <input type="text" id="LNT" name="LNT" value=""  size="20">
        <button type="button" id="btn1" onclick="getLocation();">내 위치 가져오기</button>
        <input type="submit" value="근처 WIPI 정보 보기" />
    </form>

    <br/>

    <table>
        <tr>
            <th>거리(Km)</th>
            <th>관리번호</th>
            <th>자치구</th>
            <th>와이파이명</th>
            <th>도로명주소</th>
            <th>상세주소</th>
            <th>설치위치(층)</th>
            <th>설치유형</th>
            <th>설치기관</th>
            <th>서비스구분</th>
            <th>망종류</th>
            <th>설치년도</th>
            <th>실내외구분</th>
            <th>WIFI접속환경</th>
            <th>X좌표</th>
            <th>Y좌표</th>
            <th>작업일자</th>
        </tr>
        <% List<Wifi> _wifis = (List<Wifi>) request.getAttribute("wifis");
        if(_wifis != null) {
            for(Wifi wifi : _wifis) {
        %>
            <tr>
                <td><%= wifi.getDIST() %></td>
                <td><%= wifi.getMGR_NO() %></td>
                <td><%= wifi.getWRDOFC() %></td>
                <td><%= wifi.getMAIN_NM() %></td>
                <td><%= wifi.getADRES1() %></td>
                <td><%= wifi.getADRES2() %></td>
                <td><%= wifi.getINSTL_FLOOR() %></td>
                <td><%= wifi.getINSTL_TY() %></td>
                <td><%= wifi.getINSTL_MBY() %></td>
                <td><%= wifi.getSVC_SE() %></td>
                <td><%= wifi.getCMCWR() %></td>
                <td><%= wifi.getCNSTC_YEAR() %></td>
                <td><%= wifi.getINOUT_DOOR() %></td>
                <td><%= wifi.getREMARS3() %></td>
                <td><%= wifi.getLAT() %></td>
                <td><%= wifi.getLON() %></td>
                <td><%= wifi.getWORK_DTTM() %></td>
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