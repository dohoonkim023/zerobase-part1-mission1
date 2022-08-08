package DTO;

public class History {
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getSearch_dt() {
        return search_dt;
    }

    public void setSearch_dt(String search_dt) {
        this.search_dt = search_dt;
    }

    String lat;
    String lon;
    String search_dt;
}
