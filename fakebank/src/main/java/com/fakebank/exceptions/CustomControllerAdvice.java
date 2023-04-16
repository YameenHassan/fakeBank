package com.fakebank.exceptions;

import com.fakebank.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
class CustomControllerAdvice {
    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<ResponseDto> handleCustomNotFoundException(Exception e) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(ResponseDto.error(new ErrorResponse(status, e.getMessage())), status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleExceptions(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

        return new ResponseEntity<>(ResponseDto.error(new ErrorResponse(status, e.getMessage(), stackTrace)), status);
    }
}
