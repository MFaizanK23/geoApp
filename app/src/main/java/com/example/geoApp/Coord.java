package com.example.geoApp;

public class Coord {
    private int ID;
    private String ADDR, LONGITUDE, LATITUDE;

    Coord(){}
    Coord(String ADDR, String LONGITUDE, String LATITUDE){
        this.ADDR = ADDR;
        this.LONGITUDE = LONGITUDE;
        this.LATITUDE = LATITUDE;
    }
    Coord(int ID, String ADDR, String LONGITUDE, String LATITUDE){
        this.ID = ID;
        this.ADDR = ADDR;
        this.LONGITUDE = LONGITUDE;
        this.LATITUDE = LATITUDE;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getADDR() {
        return ADDR;
    }

    public void setADDR(String ADDR) {
        this.ADDR = ADDR;
    }

    public String getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(String LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }
}
