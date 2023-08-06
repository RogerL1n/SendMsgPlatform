package com.lry.strategy.filters.impl;


   


import com.lry.platform.common.constants.InterfaceExceptionDict;
import com.lry.platform.common.model.Standard_Submit;
import com.lry.strategy.dfa.SensitivewordFilter;
import com.lry.strategy.filters.FilterChain;
import com.lry.strategy.service.SendReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by lry on  2022/7/16 10:48
 *
 * @author lry
 *   
 */
@Component
public class DirtyWordsFilter  implements FilterChain {

    private SendReportService sendReportService;
    @Autowired
    public void setSendReportService(SendReportService sendReportService) {
        this.sendReportService = sendReportService;
    }

    private SensitivewordFilter sensitivewordFilter;

    @Autowired
    public void setSensitivewordFilter(SensitivewordFilter sensitivewordFilter) {
        this.sensitivewordFilter = sensitivewordFilter;
    }

    @Override
    public boolean dealSms(Standard_Submit standard_submit) {
        Set<String> sensitiveWord = sensitivewordFilter.getSensitiveWord(standard_submit.getMessageContent());//获内容中敏感词的集合
        System.err.println(sensitiveWord);
        if (sensitiveWord != null && sensitiveWord.size() > 0) {
            //存在敏感词,应该通知客户,短信中包含敏感词,甚至可以把敏感词的内容返回
//            Standard_Report standard_report = new Standard_Report();
//            standard_report.setClientID(standard_submit.getClientID());
//            standard_report.setSrcID(standard_submit.getSrcSequenceId());
//            standard_report.setState(2);
//            standard_report.setErrorCode(InterfaceExceptionDict.RETURN_STATUS_DIRTY_ERROR);
//            standard_report.setDescription(sensitiveWord.toString());
//            standard_report.setMobile(standard_submit.getDestMobile());
//            standard_report.setSendCount(1);//第几次推送, 是这样的,我们告诉客户结果,中间可能因为网络等原因没推送到,我们需要重试,但是呢又不是无限制重试,我们可以设置一个阈值
//            sendReportService.sendSmsReport(standard_report);
            //将本次短信的记录保存到我们的日志中,我们的日志用es来保存
            standard_submit.setReportState(2);
            standard_submit.setErrorCode(InterfaceExceptionDict.RETURN_STATUS_DIRTY_ERROR);
            standard_submit.setDescription(sensitiveWord.toString());
            return false;
        }
        return true;
    }
}
