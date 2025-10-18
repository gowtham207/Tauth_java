package com.gowtham.project01.utils;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.gowtham.project01.models.UserActivityModel;
import com.gowtham.project01.repo.UserActivityRepo;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class UserActivityLogUtils {

    @Autowired
    private static UserActivityRepo userActivityRepo;

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

        return userActivityRepo.save(log);
    }
}
