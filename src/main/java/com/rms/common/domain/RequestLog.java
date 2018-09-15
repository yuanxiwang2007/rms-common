package com.rms.common.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

import java.io.Serializable;

@JSONType(
        orders = {"addr", "url", "parameters", "requestBody", "sessionId", "referer", "accept", "contentType", "agent"}
)
public class RequestLog implements Serializable {
    private static final long serialVersionUID = -7794798982246434472L;
    @JSONField(
            name = "url"
    )
    private String url;
    @JSONField(
            name = "referer"
    )
    private String referer;
    @JSONField(
            name = "addr"
    )
    private String addr;
    @JSONField(
            name = "contentType"
    )
    private String contentType;
    @JSONField(
            name = "accept"
    )
    private String accept;
    @JSONField(
            name = "agent"
    )
    private String agent;
    @JSONField(
            name = "sessionId"
    )
    private String sessionId;
    @JSONField(
            name = "parameters"
    )
    private Object parameters;
    @JSONField(
            name = "requestBody"
    )
    private Object requestBody;

    public RequestLog() {
    }

    public String getUrl() {
        return this.url;
    }

    public RequestLog setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getReferer() {
        return this.referer;
    }

    public RequestLog setReferer(String referer) {
        this.referer = referer;
        return this;
    }

    public String getAddr() {
        return this.addr;
    }

    public RequestLog setAddr(String addr) {
        this.addr = addr;
        return this;
    }

    public String getContentType() {
        return this.contentType;
    }

    public RequestLog setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public String getAccept() {
        return this.accept;
    }

    public RequestLog setAccept(String accept) {
        this.accept = accept;
        return this;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public RequestLog setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public Object getParameters() {
        return this.parameters;
    }

    public RequestLog setParameters(Object parameters) {
        this.parameters = parameters;
        return this;
    }

    public String getAgent() {
        return this.agent;
    }

    public RequestLog setAgent(String agent) {
        this.agent = agent;
        return this;
    }

    public Object getRequestBody() {
        return this.requestBody;
    }

    public RequestLog setRequestBody(Object requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public String toString() {
        return "RequestLog [url=" + this.url + ", referer=" + this.referer + ", addr=" + this.addr + ", contentType=" + this.contentType + ", accept=" + this.accept + ", agent=" + this.agent + ", sessionId=" + this.sessionId + ", parameters=" + this.parameters + ", requestBody=" + this.requestBody + "]";
    }
}