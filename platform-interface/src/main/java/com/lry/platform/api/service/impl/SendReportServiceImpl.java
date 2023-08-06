package com.lry.platform.api.service.impl;





import com.lry.platform.api.feign.CacheService;
import com.lry.platform.api.service.SendReportService;
import com.lry.platform.common.constants.CacheConstants;
import com.lry.platform.common.model.Standard_Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created by lry on  2022/7/19 10:49
 *
 * @author lry
 *
 */
@Service
public class SendReportServiceImpl  implements SendReportService {

    private RestTemplate restTemplate;

    private CacheService cacheService;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public void sendReport(Standard_Report report) {
        //通知客户
        //通知客户需要知道客户的url
        // 首先获取客户的信息,根据客户的id, 在report中有一个客户id
        Map clientInfoMap = cacheService.hmget(CacheConstants.CACHE_PREFIX_CLIENT + report.getClientID());

        if (clientInfoMap != null && clientInfoMap.size() > 0) {
            //客户用于接收请求的url,我们需要请求这个地址,把数据传过去
            String receivestatusurl = (String) clientInfoMap.get("receivestatusurl");
            //我们约定我们会通过post请求的方式将参数传递过去,并且要求对方返回一个 ok字符串,返回其他任何内容我们都认为是失败
            String result = restTemplate.postForObject(receivestatusurl, report, String.class);
            System.err.println("这是客户返回的结果:===>"+result);
            if (!"ok".equals(result)) {
                report.setSendCount(2);
                //TODO 将当前的状态报告保存起来, 后面通过定时任务进行发送
            }
        }

    }
}
