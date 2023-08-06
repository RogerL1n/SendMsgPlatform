package com.lry.platform.common.constants;

/**
 * Created by Administrator on 2019/5/17.
 */
public class InterfaceExceptionDict {

    //成功
    public static final String RETURN_STATUS_SUCCESS = "0";

    //认证错：clientId错误
    public static final String RETURN_STATUS_CLIENTID_ERROR = "101";

    //密码错误
    public static final String RETURN_STATUS_PWD_ERROR = "102";

    //IP校验错误
    public static final String RETURN_STATUS_IP_ERROR = "103";

    //消息长度错，为空或超长（目前定为500个字）
    public static final String RETURN_STATUS_MESSAGE_ERROR = "104";

    //手机号错误
    public static final String RETURN_STATUS_MOBILE_ERROR = "105";

    //下行编号（srcID）错误
    public static final String RETURN_STATUS_SRCID_ERROR = "106";
    //手机号在黑名单中
    public static final String RETURN_STATUS_BLACK_ERROR = "107";

    //短信中包含敏感词
    public static final String RETURN_STATUS_DIRTY_ERROR = "108";
    //针对某个号码发送短信过于频繁的错误
    public static final String RETURN_STATUS_LIMIT_ERROR = "109";
    //用户余额不足
    public static final String RETURN_STATUS_FEE_ERROR = "110";
    //手机号段的归属地未查到
    public static final String RETURN_STATUS_PHASE_ERROR = "111";
}
