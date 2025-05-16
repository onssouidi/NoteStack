package com.notes.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="database")
public class DBConfig {
    private String url;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String username;
    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }



}