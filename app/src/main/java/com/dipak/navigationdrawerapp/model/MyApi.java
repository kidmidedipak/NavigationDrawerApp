package com.dipak.navigationdrawerapp.model;

public class MyApi {
    String apiType;
    String api;


    public MyApi(String apiType, String api) {
        this.apiType = apiType;
        this.api = api;
    }

    public String getApiType() {
        return apiType;
    }

    public String getApi() {
        return api;
    }
}
