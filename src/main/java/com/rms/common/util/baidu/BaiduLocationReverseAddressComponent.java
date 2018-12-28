package com.rms.common.util.baidu;

import lombok.Data;

@Data
public class BaiduLocationReverseAddressComponent {
    /**
     * 国家
     */
    private String country;
    private String country_code;
    private String country_code_iso;
    private String country_code_iso2;
    private String province;
    /**
     * 城市
     */
    private String city;
    private Integer city_level;
    private String district;
    private String town;
    private String adcode;
    /**
     * 街道地址
     */
    private String street;
    private String street_number;
    private String direction;
    private String distance;
}
