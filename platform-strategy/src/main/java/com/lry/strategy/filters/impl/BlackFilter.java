package com.lry.strategy.filters.impl;


   


import com.lry.platform.common.constants.CacheConstants;
import com.lry.platform.common.model.Standard_Submit;
import com.lry.strategy.feign.CacheService;
import com.lry.strategy.filters.FilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * Created by lry on  2022/7/15 10:31
 * 当前的过滤器主要负责黑名单处理,主要通过 BLACK:手机号作为key的方式查询
 * @author lry
 *   
 */
//@Component
public class BlackFilter implements FilterChain {


    private CacheService cacheService;

    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public boolean dealSms(Standard_Submit standard_submit) {
        String mobile = standard_submit.getDestMobile();//获取到要发送短信的手机号
        //判断是不是在黑名单中,通过判断这个手机号在redis中有没有对应的数据即可
        String result = cacheService.get(CacheConstants.CACHE_PREFIX_BLACK + mobile);
        if (StringUtils.isEmpty(result)) {
            //不在黑名单
            System.err.println(mobile + "不在黑名单中");
            return true;
        }else{
            //后续要通知客户 当前手机号在黑名单中
            System.err.println(mobile + "----->在黑名单中");
            return false;
        }

    }
}
