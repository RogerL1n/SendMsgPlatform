package com.lry.platform.webmaster.exceptions;


   


/**
 * Created by lry on 2021/8/7 17:02
 *
 * @author lry
 * @version 1.0
 * @since 1.0
 */

public class DeleteDataErrorException extends MyBaseException{

    public DeleteDataErrorException(String message, int code) {
        super(message, code);
    }
}
