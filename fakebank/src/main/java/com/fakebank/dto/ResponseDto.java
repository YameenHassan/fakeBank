package com.fakebank.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
    private Boolean success;
    private String message;
    private Object data;
    private Object error;

    public static ResponseDto success(Object data, String message){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(true);
        responseDto.setData(data);
        responseDto.setMessage(message);
        return responseDto;
    }

    public static ResponseDto error(Object error){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(false);
        responseDto.setError(error);
        return responseDto;
    }

}
