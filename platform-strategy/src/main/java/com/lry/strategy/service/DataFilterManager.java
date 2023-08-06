package com.lry.strategy.service;


   


import com.lry.platform.common.model.Standard_Submit;

/**
 * Created by lry on  2022/7/14 11:07
 *
 * @author lry
 *   
 */

public interface DataFilterManager {
    /**
     * 数据过滤器(策略)的处理管理者,处理短信,由实现类具体操作当前的短信
     * @param standard_submit
     */
    void dealSms(Standard_Submit standard_submit);
}
