package com.example.tuananh.mytravels.entity;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "travel")
public class Travel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String title;
    private long startDt,endDt;
    private String placeId,placeName,placeAddr;
    private double placeLat,placeLng;

    public Travel(@NonNull String title) {
        this.title = title;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public long getStartDt() {
        return startDt;
    }

    public void setStartDt(long startDt) {
        this.startDt = startDt;
    }

    public long getEndDt() {
        return endDt;
    }

    public void setEndDt(long endDt) {
        this.endDt = endDt;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceAddr() {
        return placeAddr;
    }

    public void setPlaceAddr(String placeAddr) {
        this.placeAddr = placeAddr;
    }

    public double getPlaceLat() {
        return placeLat;
    }

    public void setPlaceLat(double placeLat) {
        this.placeLat = placeLat;
    }

    public double getPlaceLng() {
        return placeLng;
    }

    public void setPlaceLng(double placeLng) {
        this.placeLng = placeLng;
    }

    @NonNull
    @Override
    public String toString() {
        return "Travel{" +
                "id ="+id+"" +
                ",title='"+title+"'" +
                ",startDt="+startDt+"" +
                ",endDt="+endDt+"" +
                ",placeId="+placeId+"" +
                ",placeName='"+placeName+"',placeAddr='"+placeAddr+"'" +
                ",placeLat="+placeLat+"" +
                ",placeLng="+placeLng+
                "}";
    }
}
