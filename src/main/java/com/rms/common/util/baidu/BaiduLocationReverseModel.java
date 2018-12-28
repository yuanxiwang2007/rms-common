package com.rms.common.util.baidu;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaiduLocationReverseModel implements Serializable {
    private Integer status;
    private BaiduLocationReverseResult result;
}
