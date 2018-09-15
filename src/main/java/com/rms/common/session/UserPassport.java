package com.rms.common.session;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class UserPassport implements Serializable {
    private static final long serialVersionUID = 1990443910639719288L;
    @JSONField(
            name = "id"
    )
    private long id;
    @JSONField(
            name = "cellphone"
    )
    private String cellphone;
    @JSONField(
            name = "username"
    )
    private String username;
    @JSONField(
            name = "realname"
    )
    private String realname;
    @JSONField(
            name = "avatar"
    )
    private String avatar;
    @JSONField(
            name = "gender"
    )
    private int gender;
    @JSONField(
            name = "has_init"
    )
    private int hasInit;
    @JSONField(
            name = "enterprise_id"
    )
    private int enterpriseId;
    private String openId;
    private String appId;

    public UserPassport() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCellphone() {
        return this.cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return this.realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getHasInit() {
        return this.hasInit;
    }

    public void setHasInit(int hasInit) {
        this.hasInit = hasInit;
    }

    public int getEnterpriseId() {
        return this.enterpriseId;
    }

    public void setEnterpriseId(int enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getOpenId() {
        return this.openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String toString() {
        return "UserPassport [id=" + this.id + ", cellphone=" + this.cellphone + ", username=" + this.username + ", realname=" + this.realname + ", avatar=" + this.avatar + ", gender=" + this.gender + ", hasInit=" + this.hasInit + ", enterpriseId=" + this.enterpriseId + ", openId=" + this.openId + ", appId=" + this.appId + "]";
    }
}
