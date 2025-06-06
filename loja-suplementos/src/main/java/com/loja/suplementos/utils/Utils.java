package com.loja.suplementos.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class Utils {

    private static final String format = "dd/MM/yyyy";

    public static Date convertStringToDate(String dateString) {
        try {
            return new SimpleDateFormat(format).parse(dateString);
        } catch (ParseException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
