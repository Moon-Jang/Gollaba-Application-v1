package kr.mj.gollaba.common.aspect;

import kr.mj.gollaba.common.service.HashIdService;
import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class HashIdAspect {

    private final HashIdService hashIdService;

    @Around("@annotation(parseHashId)")
    public Object parseHashId(ProceedingJoinPoint pjp, ParseHashId parseHashId) throws Throwable {
        Object[] args = pjp.getArgs();

        args[0] =  hashIdService.decode(args[0].toString());

        return pjp.proceed(args);
    }

}
