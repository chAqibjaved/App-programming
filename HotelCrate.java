package com.aqib.mymedreminder.model;

import java.util.List;

public class HotelCrate {

    private String name,hotelid,hoteldiscount,mainimage_url,mincharges,Location;

    public HotelCrate() {
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHotelid() {
        return hotelid;
    }

    public void setHotelid(String hotelid) {
        this.hotelid = hotelid;
    }

    public String getHoteldiscount() {
        return hoteldiscount;
    }

    public void setHoteldiscount(String hoteldiscount) {
        this.hoteldiscount = hoteldiscount;
    }

    public String getMainimage_url() {
        return mainimage_url;
    }

    public void setMainimage_url(String mainimage_url) {
        this.mainimage_url = mainimage_url;
    }

    public String getMincharges() {
        return mincharges;
    }

    public void setMincharges(String mincharges) {
        this.mincharges = mincharges;
    }




}
