package com.lry.platform.api.exceptions;





import com.lry.platform.common.constants.ResutlDataEnum;

/**
 * Created by lry on  2022/7/13 16:47
 *
 * @author lry
 *
 */

public class MyBaseException  extends RuntimeException {

    private String code;

    public MyBaseException(String message, String code) {
        super(message);
        this.code = code;
    }

    public MyBaseException(ResutlDataEnum dataEnum) {
        super(dataEnum.getMsg());
        this.code = dataEnum.getCode();
    }

    public String getCode() {
        return code;
    }
}
