package kr.mj.gollaba.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@Slf4j
@RequiredArgsConstructor
public class SchedulerErrorHandler implements ErrorHandler {

    @Override
    public void handleError(Throwable e) {
        var scheduleName = getScheduleName(e);
        log.info("{} {} - 실패 {}", LocalDateTime.now(), scheduleName, e);
    }

    private String getScheduleName(Throwable e) {
        var stackTraceElement= Arrays.stream(e.getStackTrace())
            .filter(el -> el.getClassName().contains("com.gguge.scheduler.schedule"))
            .findFirst()
            .orElseThrow();
        var className = stackTraceElement.getClassName().split("\\.");
        var simpleClassName = className[className.length-1];

        return simpleClassName + "." + stackTraceElement.getMethodName();
    }
}
