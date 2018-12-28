package com.rms.common.util.baidu;

import lombok.Data;

@Data
public class BaiduLocationReverseResult {
    private String formatted_address;
    private String business;
    private String sematic_description;
    private String cityCode;
    private BaiduLocationReverseAddressComponent addressComponent;
}
