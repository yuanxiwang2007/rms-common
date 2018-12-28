package com.rms.common.util.baidu;

import lombok.Data;

import java.util.Date;

@Data
public class MobileAreaResponseHeader {

    private Integer status;
    private Date time;
    private String version;
}
