package com.rms.common.session;

import java.io.Serializable;
import java.util.Date;

public class UserSession implements Serializable {
    private static final long serialVersionUID = 1L;
    private String openID;
    private String openIDName;
    private String macID;
    private String token;
    //private WxMpUser wxMpUser;
    private Date loginDate;
    private String macPath;
    private UserPassport userPassport;
    private String loginType;

    public UserSession() {
    }

    public String getOpenID() {
        return this.openID;
    }

    public void setOpenID(String openID) {
        this.openID = openID;
    }

    public String getMacID() {
        return this.macID;
    }

    public void setMacID(String macID) {
        this.macID = macID;
    }

    public String getOpenIDName() {
        return this.openIDName;
    }

    public void setOpenIDName(String openIDName) {
        this.openIDName = openIDName;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLoginDate() {
        return this.loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getMacPath() {
        return this.macPath;
    }

    public void setMacPath(String macPath) {
        this.macPath = macPath;
    }

    public UserPassport getUserPassport() {
        return this.userPassport;
    }

    public void setUserPassport(UserPassport userPassport) {
        this.userPassport = userPassport;
    }



    public String getLoginType() {
        return this.loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}

