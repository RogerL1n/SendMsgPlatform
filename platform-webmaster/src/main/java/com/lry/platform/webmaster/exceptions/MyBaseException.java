package com.lry.platform.webmaster.exceptions;


   


/**
 * Created by lry on 2021/8/7 16:57
 *
 * @author lry
 * @version 1.0
 * @since 1.0
 */

public class MyBaseException extends RuntimeException {

    private int code;

    public MyBaseException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
