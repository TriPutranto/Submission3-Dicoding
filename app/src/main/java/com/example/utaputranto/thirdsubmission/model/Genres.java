package com.example.utaputranto.thirdsubmission.model;

import com.google.gson.annotations.SerializedName;

public class Genres {

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
