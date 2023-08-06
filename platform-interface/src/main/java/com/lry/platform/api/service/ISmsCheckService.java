package com.lry.platform.api.service;



import java.util.List;

/**
 * Created by lry on  2022/7/13 16:04
 *
 * @author lry
 *
 */

public interface ISmsCheckService {
    /**
     *
     * @param clientId
     * @param pwd
     * @param srcId
     * @param ip
     * @param content
     * @param mobile
     * @return 不符合规则的手机号
     */
    List<String> checkSms(String clientId, String pwd, String srcId, String ip, String content, String mobile);

}
