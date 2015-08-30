package me.alexeyterekhov.susaninsearch.Server.API;

public class WebMethodResponse {
    private int code;
    private String response;

    public WebMethodResponse(int code, String response) {
        this.code = code;
        this.response = response;
    }

    public int getCode() {
        return code;
    }

    public String getResponse() {
        return response;
    }
}
