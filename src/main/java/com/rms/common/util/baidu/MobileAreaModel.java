package com.rms.common.util.baidu;

import lombok.Data;

import java.util.Map;

@Data
public class MobileAreaModel {

    private Map<String, MobileAreaInfo> response;

    private MobileAreaResponseHeader responseHeader;
}
