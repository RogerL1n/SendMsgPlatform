package com.lry.platform.common.constants;

public class RabbitMqConsants {
    //接口模块将可以发送的短信号码和内容推送到这个队列,交给策略模块处理
    public final static String TOPIC_PRE_SEND = "pre_send_sms_topic";
    //下发日志TOPIC
    public final static String TOPIC_SMS_SEND_LOG = "sms_send_log_topic";
    //推送状态报告TOPIC
    public final static String TOPIC_PUSH_SMS_REPORT = "push_sms_report_topic";
    //状态报告更新TOPIC
    public final static String TOPIC_UPDATE_SMS_REPORT = "report_update_topic";
    //待发送短信网关队列 + 网关ID号
    public final static String TOPIC_SMS_GATEWAY = "sms_send_gateway_";
    //更新黑名单的交换机
    public final static String TOPIC_BLACKNUM_UPDATE = "sms_blacknums_update_exchange";
    public final static String TOPIC_DIRTYWORDS_UPDATE = "sms_dirtywords_update_exchange";
    public final static String TOPIC_EXECFILTERS_UPDATE = "sms_execfilters_update_exchange";
    public final static String TOPIC_LIMIT_UPDATE = "sms_limit_update_exchange";
    public final static String TOPIC_SEARCHPARAMS_UPDATE = "sms_searchparams_update_exchange";

}
