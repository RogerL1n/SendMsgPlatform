package com.lry.platform.api.dto;





import com.fasterxml.jackson.annotation.JsonInclude;
import com.lry.platform.common.constants.ResutlDataEnum;

/**
 * Created by lry on  2022/7/13 15:40
 *
 * @author lry
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class R {

    private String code;
    private String msg;
    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public static R getR(ResutlDataEnum dataEnum) {
//        R r = new R();
//        r.setCode(dataEnum.getCode());
//        r.setMsg(dataEnum.getMsg());
        return getR(dataEnum, null);
    }

    public static R getR(ResutlDataEnum dataEnum,Object result) {
        R r = new R();
        r.setCode(dataEnum.getCode());
        r.setMsg(dataEnum.getMsg());
        r.setResult(result);
        return r;
    }

    public static R getR(String code,String msg,Object result) {
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        r.setResult(result);
        return r;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "R{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
