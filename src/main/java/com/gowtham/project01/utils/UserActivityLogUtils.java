package com.gowtham.project01.utils;

import java.util.UUID;
import org.springframework.stereotype.Component;
import com.gowtham.project01.models.UserActivityModel;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class UserActivityLogUtils {

    public static UserActivityModel createUserActivityLog(HttpServletRequest req, UUID userId, String activity) {
        UserActivityModel log = new UserActivityModel();

        String ipAddress = req.getRemoteAddr();
        String userAgent = req.getHeader("User-Agent");

        log.setUserId(userId);
        log.setActivity(activity);
        log.setIpAddress(ipAddress);
        log.setUserAgent(userAgent);
        log.setLogDate(java.time.LocalDate.now());
        log.setLogTime(java.time.LocalTime.now());

        return log;
    }
}
