package APIservice;

import DTO.Wifi;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApiService {

    public int getSize() throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
        urlBuilder.append("/" + URLEncoder.encode("415a59527473756b37337363547578","UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
        urlBuilder.append("/" + URLEncoder.encode("json","UTF-8") ); /*요청파일타입 (xml,xmlf,xls,json) */
        urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
        urlBuilder.append("/" + URLEncoder.encode("1","UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
        urlBuilder.append("/" + URLEncoder.encode("1","UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
        // 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.
        // 서비스별 추가 요청 인자이며 자세한 내용은 각 서비스별 '요청인자'부분에 자세히 나와 있습니다.
        urlBuilder.append("/" + URLEncoder.encode("20220301","UTF-8")); /* 서비스별 추가 요청인자들*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/xml");
        System.out.println("Response code: " + conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
        BufferedReader rd;
        // 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        JsonElement element = JsonParser.parseString(sb.toString());
        JsonObject object = element.getAsJsonObject();
        object = object.getAsJsonObject("TbPublicWifiInfo");
        String count = object.get("list_total_count").getAsString();

        return Integer.valueOf(count);
    }


    public List<Wifi> getWifi(int start, int end) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
        urlBuilder.append("/" + URLEncoder.encode("415a59527473756b37337363547578","UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
        urlBuilder.append("/" + URLEncoder.encode("json","UTF-8") ); /*요청파일타입 (xml,xmlf,xls,json) */
        urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
        urlBuilder.append("/" + URLEncoder.encode(String.valueOf(start),"UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
        urlBuilder.append("/" + URLEncoder.encode(String.valueOf(end),"UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
        // 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.
        // 서비스별 추가 요청 인자이며 자세한 내용은 각 서비스별 '요청인자'부분에 자세히 나와 있습니다.
        urlBuilder.append("/" + URLEncoder.encode("20220301","UTF-8")); /* 서비스별 추가 요청인자들*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/xml");
        System.out.println("Response code: " + conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
        BufferedReader rd;
        // 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        System.out.println(sb.toString()); // 전달받은 json값 확인


        JsonElement element = JsonParser.parseString(sb.toString());
        JsonObject object = element.getAsJsonObject();
        object = object.getAsJsonObject("TbPublicWifiInfo");
        JsonArray jsonArray = object.getAsJsonArray("row");


        List<Wifi> wifis = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jo = (JsonObject) jsonArray.get(i);
            String X_SWIFI_MGR_NO = jo.get("X_SWIFI_MGR_NO").getAsString();
            String X_SWIFI_WRDOFC = jo.get("X_SWIFI_WRDOFC").getAsString();
            String X_SWIFI_MAIN_NM = jo.get("X_SWIFI_MAIN_NM").getAsString();
            String X_SWIFI_ADRES1 = jo.get("X_SWIFI_ADRES1").getAsString();
            String X_SWIFI_ADRES2 = jo.get("X_SWIFI_ADRES2").getAsString();
            String X_SWIFI_INSTL_FLOOR = jo.get("X_SWIFI_INSTL_FLOOR").getAsString();
            String X_SWIFI_INSTL_TY = jo.get("X_SWIFI_INSTL_TY").getAsString();
            String X_SWIFI_INSTL_MBY = jo.get("X_SWIFI_INSTL_MBY").getAsString();
            String X_SWIFI_SVC_SE = jo.get("X_SWIFI_SVC_SE").getAsString();
            String X_SWIFI_CMCWR = jo.get("X_SWIFI_CMCWR").getAsString();
            String X_SWIFI_CNSTC_YEAR = jo.get("X_SWIFI_CNSTC_YEAR").getAsString();
            String X_SWIFI_INOUT_DOOR = jo.get("X_SWIFI_INOUT_DOOR").getAsString();
            String X_SWIFI_REMARS3 = jo.get("X_SWIFI_REMARS3").getAsString();
            String LAT = jo.get("LAT").getAsString();
            String LNT = jo.get("LNT").getAsString();
            String WORK_DTTM = jo.get("WORK_DTTM").getAsString();

            Wifi wifi = new Wifi();
            wifi.setMGR_NO(X_SWIFI_MGR_NO);
            wifi.setWRDOFC(X_SWIFI_WRDOFC);
            wifi.setMAIN_NM(X_SWIFI_MAIN_NM);
            wifi.setADRES1(X_SWIFI_ADRES1);
            wifi.setADRES2(X_SWIFI_ADRES2);
            wifi.setINSTL_FLOOR(X_SWIFI_INSTL_FLOOR);
            wifi.setINSTL_TY(X_SWIFI_INSTL_TY);
            wifi.setINSTL_MBY(X_SWIFI_INSTL_MBY);
            wifi.setSVC_SE(X_SWIFI_SVC_SE);
            wifi.setCMCWR(X_SWIFI_CMCWR);
            wifi.setCNSTC_YEAR(X_SWIFI_CNSTC_YEAR);
            wifi.setINOUT_DOOR(X_SWIFI_INOUT_DOOR);
            wifi.setREMARS3(X_SWIFI_REMARS3);
            wifi.setLAT(LNT);
            wifi.setLON(LAT);
            wifi.setWORK_DTTM(WORK_DTTM);

            wifis.add(wifi);
        }



        return wifis;

    }
}