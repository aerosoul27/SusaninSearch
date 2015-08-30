package me.alexeyterekhov.susaninsearch.Data;

import com.google.gson.annotations.SerializedName;

public class Firm {
    private transient String id = null;
    private transient String hash = null;
    @SerializedName("name")
    private String name = null;
    @SerializedName("address")
    private String address = null;
    @SerializedName("rating")
    private Float flampRating = null;

    public Firm(String id, String hash) {
        this.id = id;
        this.hash = hash;
    }

    public String getId() {
        return id;
    }

    public String getHash() {
        return hash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getFlampRating() {
        return flampRating;
    }

    public void setFlampRating(Float flampRating) {
        this.flampRating = flampRating;
    }
}
