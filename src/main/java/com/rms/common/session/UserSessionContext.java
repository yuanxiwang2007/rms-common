package com.rms.common.session;

import com.rms.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserSessionContext {
    protected static final Logger logger = LoggerFactory.getLogger(UserSessionContext.class);
    public static final String USER_IN_SESSION = "user_in_session";

    public UserSessionContext() {
    }

    public static UserSession getUserSession() throws BusinessException {
        HttpSession session = httpServletRequest().getSession();
        UserSession userSession = (UserSession)session.getAttribute("user_in_session");
        if (userSession == null) {
            logger.info("[UserSessionContext] user session is null");
            throw new BusinessException(ErrorCodeEnum.UNLOGIN);
        } else {
            return userSession;
        }
    }

    public static void setUser(UserSession userSession) {
        HttpSession session = httpServletRequest().getSession();
        session.setAttribute("user_in_session", userSession);
    }

    public static void removeUserSession() {
        HttpSession session = httpServletRequest().getSession();
        session.invalidate();
    }

    private static HttpServletRequest httpServletRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }
}
