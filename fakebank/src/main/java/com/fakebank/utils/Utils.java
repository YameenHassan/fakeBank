package com.fakebank.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;

@Slf4j
public class Utils {
    public static boolean isNullOrEmpty(@Nullable Object str) {
        return (str == null || "".equals(str));
    }

    public static boolean isNotNullOrEmpty(@Nullable Object obj) {
        return !(obj == null || "".equals(obj));
    }

    public static String dateToString(Date date, String format) {
        if (!isNullOrEmpty(date)) {
            DateFormat df = new SimpleDateFormat(format);
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            return df.format(date);
        }
        return "";
    }
    public static Date convertStringToDate(String stringDate) {
        Date date= null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    public static String getCurrentDateTimeString() {
        LocalDateTime dt = LocalDateTime.now();
        return dateToString(Date.from(dt.toInstant(ZoneOffset.UTC)), Constants.DATE_FORMAT);
    }

    public static Date getCurrentDateTime(){
        LocalDateTime dt=  LocalDateTime.now();
        return Date.from(dt.toInstant(ZoneOffset.UTC));
    }

    public static String generateUniqueCorrelationId(String env, String applicationName) {
        return env + "|" + applicationName + "|" + UUID.randomUUID();
    }

    public static String generateAccountNumber(Integer branchCode, Integer accountType){
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return branchCode + "-" + number + "-" + accountType;
    }

    public static Integer generateReferenceId(){
        Random rnd = new Random();
        return rnd.nextInt(999999999);
    }
}
