package DBservice;

import APIservice.ApiService;
import DTO.History;
import DTO.Wifi;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DbService {

    public void deleteHistoryById(int id) {
        String url = "jdbc:mariadb://localhost:3306/test";
        String dbUserId = "root";
        String dbPassword = "zerobase";

        try {
            Class.forName("org.mariadb.jdbc.Driver"); // 1.드라이버 로드함.

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }

        Connection con = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;



        try {
            con = DriverManager.getConnection(url, dbUserId, dbPassword); //2. 커넥션 객체 생성

            //다음 id 값 구하기
            String sql = "DELETE from HISTORY WHERE ID = ?";

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            int affected = preparedStatement.executeUpdate();

            if (affected > 0) {
                System.out.println(" 삭제 성공 ");
            } else {
                System.out.println(" 삭제 실패 ");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<History> getHistories() {
        List<History> histories = new ArrayList<>();


        String url = "jdbc:mariadb://localhost:3306/test";
        String dbUserId = "root";
        String dbPassword = "zerobase";

        try {
            Class.forName("org.mariadb.jdbc.Driver"); // 1.드라이버 로드함.

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }

        Connection con = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;

        try {
            con = DriverManager.getConnection(url, dbUserId, dbPassword); //2. 커넥션 객체 생성


            String sql = "SELECT * from HISTORY order by ID DESC";
            preparedStatement = con.prepareStatement(sql); //스테이트먼트 객체 생성
            rs = preparedStatement.executeQuery();


            while (rs.next()) {
                History history = new History();

                history.setId(rs.getInt("ID"));
                history.setLat(rs.getString("LAT"));
                history.setLon(rs.getString("LON"));
                history.setSearch_dt(rs.getString("SEARCH_DT"));

                histories.add(history);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return histories;
        }

    }

    public void addToHistory(String lat, String lon) {
        String url = "jdbc:mariadb://localhost:3306/test";
        String dbUserId = "root";
        String dbPassword = "zerobase";

        try {
            Class.forName("org.mariadb.jdbc.Driver"); // 1.드라이버 로드함.

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }

        Connection con = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;



        try {
            con = DriverManager.getConnection(url, dbUserId, dbPassword); //2. 커넥션 객체 생성

            //다음 id 값 구하기
            String _sql = "SELECT max(ID) as MAX_ID from HISTORY";
            preparedStatement = con.prepareStatement(_sql);
            rs = preparedStatement.executeQuery();

            int id = 0;

            rs.next();
            id = rs.getInt("MAX_ID");
            if(id != 0) {
                id = id + 1;
            } else {
                id = 1;
            }
            

            //조회 시간 구하기
            LocalDateTime now = LocalDateTime.now();
            String search_dt = now.toString().substring(0,16);

            String sql = "INSERT  into HISTORY (ID, LAT, LON, SEARCH_DT) values(?,?,?,?)";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, lat);
            preparedStatement.setString(3, lon);
            preparedStatement.setString(4, search_dt);
            int affected = preparedStatement.executeUpdate();

            if (affected > 0) {
                System.out.println(" 저장 성공 ");
            } else {
                System.out.println(" 저장 실패 ");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int saveAll() throws IOException {
        ApiService api = new ApiService();
        int size = api.getSize();
        int count = (size + 1000 -1)/1000;
        int start = 1;
        int end = 1000;

        List<Wifi> wifis = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            api.getWifi(start, end);
            wifis.addAll(api.getWifi(start, end));
            start += 1000;
            end += 1000;
        }



        String url = "jdbc:mariadb://localhost:3306/test";
        String dbUserId = "root";
        String dbPassword = "zerobase";

        try {
            Class.forName("org.mariadb.jdbc.Driver"); // 1.드라이버 로드함.

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }

        Connection con = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;



        try {
            con = DriverManager.getConnection(url, dbUserId, dbPassword); //2. 커넥션 객체 생성

            //기존 테이블 내 데이터를 모두 삭제하고 새로 업로드하여 최신상태로 유지한다.
            String _sql = "DELETE from OPEN_WIFI";
            preparedStatement = con.prepareStatement(_sql);
            preparedStatement.executeUpdate();


            String sql = "INSERT into OPEN_WIFI (MGR_NO, WRDOFC, MAIN_NM, ADRES1, ADRES2, INSTL_FLOOR, INSTL_TY, INSTL_MBY, SVC_SE, CMCWR, CNSTC_YEAR, INOUT_DOOR, REMARS3\n" +
                    ", LAT, LON, WORK_DTTM) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            int affected = 0;
            for (int i = 0; i < wifis.size(); i++) {
                preparedStatement = con.prepareStatement(sql); //스테이트먼트 객체 생성

                preparedStatement.setString(1, wifis.get(i).getMGR_NO());
                preparedStatement.setString(2, wifis.get(i).getWRDOFC());
                preparedStatement.setString(3, wifis.get(i).getMAIN_NM());
                preparedStatement.setString(4, wifis.get(i).getADRES1());
                preparedStatement.setString(5, wifis.get(i).getADRES2());
                preparedStatement.setString(6, wifis.get(i).getINSTL_FLOOR());
                preparedStatement.setString(7, wifis.get(i).getINSTL_TY());
                preparedStatement.setString(8, wifis.get(i).getINSTL_MBY());
                preparedStatement.setString(9, wifis.get(i).getSVC_SE());
                preparedStatement.setString(10, wifis.get(i).getCMCWR());
                preparedStatement.setString(11, wifis.get(i).getCNSTC_YEAR());
                preparedStatement.setString(12, wifis.get(i).getINOUT_DOOR());
                preparedStatement.setString(13, wifis.get(i).getREMARS3());
                preparedStatement.setString(14, wifis.get(i).getLAT());
                preparedStatement.setString(15, wifis.get(i).getLON());
                preparedStatement.setString(16, wifis.get(i).getWORK_DTTM());

                affected += preparedStatement.executeUpdate();


            }
            if (affected == wifis.size()) {
                System.out.println(" 저장 성공 ");
            } else {
                System.out.println(" 저장 실패 ");
            }




        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return wifis.size();
        }

    }


    public List<Wifi> getList(String lat, String lon) {
        List<Wifi> wifis = new ArrayList<>();

        String url = "jdbc:mariadb://localhost:3306/test";
        String dbUserId = "root";
        String dbPassword = "zerobase";

        try {
            Class.forName("org.mariadb.jdbc.Driver"); // 1.드라이버 로드함.

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }

        Connection con = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;

        try {
            con = DriverManager.getConnection(url, dbUserId, dbPassword); //2. 커넥션 객체 생성


            String sql = "SELECT *, (6371*acos(cos(radians(?))*cos(radians(LAT))*cos(radians(LON) -radians(?))+sin(radians(?))*sin(radians(LAT)))) as DIST from OPEN_WIFI ORDER BY  (6371*acos(cos(radians(?))*cos(radians(LAT))*cos(radians(LON) -radians(?))+sin(radians(?))*sin(radians(LAT)))) LIMIT 20;\n";
            preparedStatement = con.prepareStatement(sql); //스테이트먼트 객체 생성
            preparedStatement.setString(1, lat);
            preparedStatement.setString(2, lon);
            preparedStatement.setString(3, lat);
            preparedStatement.setString(4, lat);
            preparedStatement.setString(5, lon);
            preparedStatement.setString(6, lat);

            rs = preparedStatement.executeQuery();


            while (rs.next()) {
                Wifi wifi = new Wifi();
                String dist = rs.getString("DIST").substring(0, 6);
                wifi.setDIST(dist);
                wifi.setMGR_NO(rs.getString("MGR_NO"));
                wifi.setWRDOFC(rs.getString("WRDOFC"));
                wifi.setMAIN_NM(rs.getString("MAIN_NM"));
                wifi.setADRES1(rs.getString("ADRES1"));
                wifi.setADRES2(rs.getString("ADRES2"));
                wifi.setINSTL_FLOOR(rs.getString("INSTL_FLOOR"));
                wifi.setINSTL_TY(rs.getString("INSTL_TY"));
                wifi.setINSTL_MBY(rs.getString("INSTL_MBY"));
                wifi.setSVC_SE(rs.getString("SVC_SE"));
                wifi.setCMCWR(rs.getString("CMCWR"));
                wifi.setCNSTC_YEAR(rs.getString("CNSTC_YEAR"));
                wifi.setINOUT_DOOR(rs.getString("INOUT_DOOR"));
                wifi.setREMARS3(rs.getString("REMARS3"));
                wifi.setLAT(rs.getString("LAT"));
                wifi.setLON(rs.getString("LON"));
                wifi.setWORK_DTTM(rs.getString("WORK_DTTM"));

                wifis.add(wifi);
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return wifis;
        }

    }
}
