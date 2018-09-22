package com.rms.common.controller;

import com.rms.common.exception.BusinessException;
import com.rms.common.exception.ErrorCode;
import com.rms.common.result.HttpResult;
import com.rms.common.session.ErrorCodeEnum;
import com.rms.common.session.UserSession;
import com.rms.common.session.UserSessionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public BaseController() {

    }

    public long getUserId() throws BusinessException {
        UserSession userSession = UserSessionContext.getUserSession();
        if (userSession != null && userSession.getUserPassport() != null) {
            long passportId = userSession.getUserPassport().getId();
            if (passportId > 0L) {
                return passportId;
            }

            this.logger.info("[BaseController]Invalid PassportId", passportId);
        } else {
            this.logger.info("[BaseController]UserSession Not Exists");
        }

        throw new BusinessException(ErrorCodeEnum.UNLOGIN);
    }

    protected HttpResult success() {
        return new HttpResult(Integer.valueOf(0), "success", (Object)null);
    }

    protected HttpResult success(Object content) {
        return new HttpResult(Integer.valueOf(0), "success", content);
    }

    protected HttpResult success(Integer errorCode, Object content) {
        return new HttpResult(errorCode, "success", content);
    }

    protected HttpResult error(String message) {
        return new HttpResult(Integer.valueOf(1), message, (Object)null);
    }

    protected HttpResult error(Integer errorCode, String message) {
        return new HttpResult(errorCode, message, (Object)null);
    }

    protected HttpResult error(ErrorCode errorCode) {
        return new HttpResult(errorCode.getCode(), errorCode.getMessage(), (Object)null);
    }
}
