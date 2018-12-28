package com.rms.common.util.baidu;

import lombok.Data;

import java.util.List;

@Data
public class MobileAreaDetail {

    private List<MobileAreaCityInfo> area;

    private String province;
    private String type;
    private String operator;
}
