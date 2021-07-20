package io.boncray.web.global;

import io.boncray.bean.exception.BizException;
import io.boncray.bean.mode.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/20 14:42
 */
@Slf4j
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result<Object> webExceptionHandler(Exception e, HttpServletRequest request) {
        if (request != null) {
            log.error("异常URL:{}", request.getRequestURI());
        }
        log.error("系统异常", e);
        return Result.failure(500, e.getMessage());
    }

    @ExceptionHandler(value = BizException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result<Object> omsExceptionHandler(BizException e) {
        return Result.failure(e.getCode(), e.getMessage());
    }

}
