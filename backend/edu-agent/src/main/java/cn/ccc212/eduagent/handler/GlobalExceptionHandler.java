package cn.ccc212.eduagent.handler;

import cn.ccc212.eduagent.enums.StatusCodeEnum;
import cn.ccc212.eduagent.exception.BizException;
import cn.ccc212.eduagent.pojo.entity.Result;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Hidden
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 业务异常
    @ExceptionHandler
    public Result<?> bizExceptionHandler(BizException ex) {
        log.error("业务异常信息：{}", ex.getMessage());
        return ex.getStatusCodeEnum() != null ?
                Result.error(ex.getStatusCodeEnum(), ex.getMessage()) :
                Result.error(ex.getMessage());
    }

    // 处理格式不符合 @RequestBody 上使用 @Valid 的 DTO 中的规定
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return Result.error(StatusCodeEnum.PARAM_ERROR, errorMessage);
    }

    // 处理 @RequestParam 缺参数
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<?> handleMissingParams(MissingServletRequestParameterException ex) {
        String missingParamName = ex.getParameterName();
        String errorMessage = "参数验证失败: " + missingParamName + " 不能为空";
        return Result.error(StatusCodeEnum.PARAM_ERROR, errorMessage);
    }

    // 处理格式 @RequestParam 上 不符合 validation 的规定
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> handleConstraintViolationException(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations().stream()
                .map(v -> v.getMessage())
                .collect(Collectors.joining("; "));
        return Result.error(StatusCodeEnum.PARAM_ERROR, errorMessage);
    }

    // 处理 Get 请求中 使用 @Valid 验证路径中请求实体校验失败后抛出的异常
    @ExceptionHandler(BindException.class)
    public Result<?> handleBindException(BindException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return Result.error(StatusCodeEnum.PARAM_ERROR, errorMessage);
    }

    // 未捕获的异常
    @ExceptionHandler(Exception.class)
    public Result<?> handleGenericException(Exception ex) {
        log.error("未捕获的异常：", ex);
        return Result.error(StatusCodeEnum.SERVER_ERROR);
    }
}