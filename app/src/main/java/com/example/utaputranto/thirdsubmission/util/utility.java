package com.example.utaputranto.thirdsubmission.util;

import com.example.utaputranto.thirdsubmission.service.ApiService;
import com.example.utaputranto.thirdsubmission.service.RetrofitClient;

public class utility {
    public static final String IMAGE_URL = "https://image.tmdb.org/t/p/original";
    public static final ApiService SERVICE = RetrofitClient.retrofit().create(ApiService.class);
}
