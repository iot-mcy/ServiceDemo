package com.svc.org.exception;

import com.svc.org.bean.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ValidationException;

import static com.svc.org.utils.Constants.ERROR_CODE;
import static com.svc.org.utils.Constants.ERROR_STR;

@ControllerAdvice
@ResponseBody
public class ExceptionAspect {
    private static final Logger log = LoggerFactory.getLogger(ExceptionAspect.class.getSimpleName());

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handlerHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("could not read json...", e);
        return new ResponseEntity(ERROR_CODE, "could not read json", ERROR_STR);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity handleValidationException(MethodArgumentNotValidException e) {
        log.error("parameter_validation_exception...", e);
        return new ResponseEntity(ERROR_CODE, "parameter_validation_exception", ERROR_STR);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ValidationException.class})
    public ResponseEntity handleValidationException(ValidationException e) {
        log.error("parameter_validation_exception...", e);
        return new ResponseEntity(ERROR_CODE, "parameter_validation_exception", ERROR_STR);
    }

    /**
     * 405 - Method Not Allowed。HttpRequestMethodNotSupportedException
     * 是ServletException的子类,需要Servlet API支持
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        log.error("request_method_not_supported...", e);
        return new ResponseEntity(ERROR_CODE, "request_method_not_supported", ERROR_STR);
    }

    /**
     * 415 - Unsupported Media Type。HttpMediaTypeNotSupportedException
     * 是ServletException的子类,需要Servlet API支持
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResponseEntity handleHttpMediaTypeNotSupportedException(Exception e) {
        log.error("content_type_not_supported...", e);
        return new ResponseEntity(ERROR_CODE, "content_type_not_supported", ERROR_STR);
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TokenException.class)
    public ResponseEntity handleTokenException(Exception e) {
        log.error("Token is invaild...", e);
        return new ResponseEntity(ERROR_CODE, "Token is invaild", ERROR_STR);
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        log.error("Internal Server Error...", e);
        return new ResponseEntity(ERROR_CODE, e.getMessage(), ERROR_STR);
    }
}
