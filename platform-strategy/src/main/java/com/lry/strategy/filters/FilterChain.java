package com.lry.strategy.filters;
   


import com.lry.platform.common.model.Standard_Submit;

/**
 * Created by lry on  2022/7/14 11:10
 *
 * @author lry
 *   
 */
//相当于我们的web中的filter接口,
public interface FilterChain {
    /**
     * 处理短信,相当于以前web中的filter中的 doFilter方法
     * @param standard_submit
     * @return 如果返回fasle代表拦截后续的操作,返回true继续
     */
    boolean dealSms(Standard_Submit standard_submit);
}
