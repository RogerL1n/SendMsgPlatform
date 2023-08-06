package com.lry.platform.common.constants;
   


/**
 * Created by lry on  2022/7/13 15:42
 * 封装了一些不需要携带额外数据的状态数据
 *
 * @author lry
 *   
 */

public enum ResutlDataEnum {
    SUCCESS(InterfaceExceptionDict.RETURN_STATUS_SUCCESS, "成功"),
    CLIENTIDERROR(InterfaceExceptionDict.RETURN_STATUS_CLIENTID_ERROR, "clientId错误"),
    PWDERROR(InterfaceExceptionDict.RETURN_STATUS_PWD_ERROR, "账号密码错误"),
    IPERROR(InterfaceExceptionDict.RETURN_STATUS_IP_ERROR, "访问IP不在白名单"),
    MESSAGECONTENTERROR(InterfaceExceptionDict.RETURN_STATUS_MESSAGE_ERROR, "消息长度不符合要求"),
    MOBILENUMERROR(InterfaceExceptionDict.RETURN_STATUS_MOBILE_ERROR, "手机号错误"),
    SRCIDERROR(InterfaceExceptionDict.RETURN_STATUS_CLIENTID_ERROR, "下行编号错误");

    String code;
    String msg;

    ResutlDataEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
