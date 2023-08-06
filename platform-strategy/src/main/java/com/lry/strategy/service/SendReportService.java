package com.lry.strategy.service;



import com.lry.platform.common.model.Standard_Report;
import com.lry.platform.common.model.Standard_Submit;

/**
 * Created by lry on  2022/7/19 09:54
 *
 * @author lry
 *
 */

public interface SendReportService {

    /**
     * 发送状态报告给客户
     *
     * @param report
     */
    void sendSmsReport(Standard_Report report);

    /**
     * 将数据保存到日志中
     * @param standard_submit
     */
    void sendSmsToLog(Standard_Submit standard_submit);

}
