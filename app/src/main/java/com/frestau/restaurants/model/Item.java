package com.frestau.restaurants.model;


public class Item {
    private String itemid;
    private String itemnumber;

    public Item(String itemid, String itemnumber, String itemname, String itemquality) {
        this.itemid = itemid;
        this.itemnumber = itemnumber;
        this.itemname = itemname;
        this.itemquality = itemquality;
    }

    private String itemname;

    private String itemquality;

    public Item() {

    }
    public String getitemid() {
        return itemid;
    }

    public void setitemid(String itemid) {
        this.itemid = itemid;
    }

    public String getitemnumber() {
        return itemnumber;
    }

    public void setitemnumber(String itemnumber) {
        this.itemnumber = itemnumber;
    }

    public String getitemname() {
        return itemname;
    }

    public void setitemname(String itemname) {
        this.itemname = itemname;
    }

    public String getitemquality() {
        return itemquality;
    }

    public void setitemquality(String itemquality) {
        this.itemquality = itemquality;
    }



}