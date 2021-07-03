package com.openpayd.currencyconverter.utils;

import java.util.Map;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class ClientRequestInfo {

    private String clientIpAdress;
    private String clientUrl;
    private String sessionActivityId;

    public ClientRequestInfo() {
    }

    public String getClientIpAdress() {
        return clientIpAdress;
    }

    public void setClientIpAdress(String clientIpAdress) {
        this.clientIpAdress = clientIpAdress;
    }

    public String getClientUrl() {
        return clientUrl;
    }

    public void setClientUrl(String clientUrl) {
        this.clientUrl = clientUrl;
    }

    public String getSessionActivityId() {
        return sessionActivityId;
    }

    public void setSessionActivityId(String sessionActivityId) {
        this.sessionActivityId = sessionActivityId;
    }
}