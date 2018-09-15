package com.rms.common.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

import java.io.Serializable;

@JSONType(
        orders = {"addr", "contentType", "setCookie", "cost", "response"}
)
public class ResponseLog implements Serializable {
    private static final long serialVersionUID = -7748984325960374079L;
    @JSONField(
            name = "contentType"
    )
    private String contentType;
    @JSONField(
            name = "addr"
    )
    private String addr;
    @JSONField(
            name = "response"
    )
    private Object response;
    @JSONField(
            name = "setCookie"
    )
    private String setCookie;
    @JSONField(
            name = "cost"
    )
    private String cost;

    public ResponseLog() {
    }

    public String getContentType() {
        return this.contentType;
    }

    public ResponseLog setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public String getAddr() {
        return this.addr;
    }

    public ResponseLog setAddr(String addr) {
        this.addr = addr;
        return this;
    }

    public Object getResponse() {
        return this.response;
    }

    public ResponseLog setResponse(Object response) {
        this.response = response;
        return this;
    }

    public String getSetCookie() {
        return this.setCookie;
    }

    public ResponseLog setSetCookie(String setCookie) {
        this.setCookie = setCookie;
        return this;
    }

    public String getCost() {
        return this.cost;
    }

    public ResponseLog setCost(String cost) {
        this.cost = cost;
        return this;
    }

    public String toString() {
        return "ResponseLog [contentType=" + this.contentType + ", addr=" + this.addr + ", response=" + this.response + ", setCookie=" + this.setCookie + ", cost=" + this.cost + "]";
    }
}
