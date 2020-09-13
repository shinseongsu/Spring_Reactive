package com.spring.example.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PasswordDTO {
    private String raw;
    private String secured;

    public PasswordDTO(@JsonProperty("raw") String raw,
                       @JsonProperty("secured") String secured) {
        this.raw = raw;
        this.secured = secured;
    }

    public String getRaw() {
        return raw;
    }

    public String getSecured() {
        return secured;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public void setSecured(String secured) {
        this.secured = secured;
    }

}
